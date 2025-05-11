package com.example.bank.services;

import com.example.bank.controller.response.PayLoanResponse;
import com.example.bank.domain.Customer;
import com.example.bank.domain.Loan;
import com.example.bank.dto.LoanDTO;
import com.example.bank.exception.LoanException;
import com.example.bank.repository.CustomerRepository;
import com.example.bank.repository.LoanRepository;
import com.example.bank.service.LoanService;
import com.example.bank.services.helper.LoanHelper;
import com.example.bank.validation.CreateLoanRequestBody;
import com.example.bank.validation.PayLoanRequestBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoanServiceTest {

    CustomerRepository customerRepository;
    LoanRepository loanRepository;
    LoanService loanService;
    LoanHelper loanHelper = new LoanHelper();

    @BeforeEach
    void setUpCommon(){
        customerRepository = mock(CustomerRepository.class);
        loanRepository = mock(LoanRepository.class);
        loanService = new LoanService(loanRepository, customerRepository);
    }

    @Test
    void getListTest(){
        List<Loan> loanList = loanHelper.getLoanList();
        when(loanRepository.findAllByCustomerId(1L)).thenReturn(loanList);
        List<LoanDTO> data = loanService.getList(1L);
        assertEquals(loanList.size(), data.size());
        assertEquals(loanList.getFirst().getId(), data.getFirst().id());
        assertEquals(loanList.getLast().getId(), data.getLast().id());
    }

    @Test
    void getLoanListButCustomerDoesNotExist(){
        List<Loan> loanList = loanHelper.getLoanList();
        when(loanRepository.findAllByCustomerId(1L)).thenReturn(loanList);
        LoanException exception = assertThrows(LoanException.class, () -> loanService.getList(2L));
        assertEquals("Customer or Customer's loan NOT found", exception.getMessage());
    }

    @Test
    void createLoanButCustomerDoesNotExist(){
        CreateLoanRequestBody req = new CreateLoanRequestBody(2L, 10.0, 0.2, 6L);
        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(loanHelper.getCustomer(1L)));
        LoanException exception = assertThrows(LoanException.class, () -> loanService.createLoan(req));
        assertEquals("Customer not found", exception.getMessage());
    }

    @Test
    void createLoanButLimitIsInSufficient(){
        CreateLoanRequestBody req = new CreateLoanRequestBody(1L, 90.0, 0.2, 6L);
        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(loanHelper.getCustomer(1L)));
        LoanException exception = assertThrows(LoanException.class, () ->loanService.createLoan(req) );
        assertEquals("Your Limit is insufficient", exception.getMessage());
    }

    @Test
    void createLoanCheckAmountOfInstallment(){
        CreateLoanRequestBody req = new CreateLoanRequestBody(1L, 50.0, 0.5, 6L);
        Loan loan = loanHelper.getLoanOne(req);
        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(loanHelper.getCustomer(1L)));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        LoanDTO data = loanService.createLoan(req);
        double totalAmount = req.amount() * (1 + req.interestRate());
        double eachAmount = totalAmount / req.numberOfInstallments();
        assertEquals(eachAmount, data.installments().getFirst().amount());
    }

    @Test
    void createLoanCheckCountOfInstallment(){
        CreateLoanRequestBody req = new CreateLoanRequestBody(1L, 50.0, 0.5, 6L);
        Loan loan = loanHelper.getLoanOne(req);
        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(loanHelper.getCustomer(1L)));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        LoanDTO data = loanService.createLoan(req);
        assertEquals(req.numberOfInstallments(), data.installments().size());
    }

    @Test
    void payLoanButLoanDoesNotExist(){
        PayLoanRequestBody req = new PayLoanRequestBody(2L, 30.0);
        Loan loan = loanHelper.getLoanById(1L, 50.0);
        when(loanRepository.findById(1L)).thenReturn(Optional.ofNullable(loan));
        LoanException exp = assertThrows(LoanException.class, () -> loanService.payLoan(req));
        assertEquals("Loan not found", exp.getMessage());
    }

    @Test
    void payLoanButInSufficientAmountForAInstallment(){
        PayLoanRequestBody req = new PayLoanRequestBody(1L, 5.0);
        Loan loan = loanHelper.getLoanById(1L, 50.0);
        when(loanRepository.findById(1L)).thenReturn(Optional.ofNullable(loan));
        PayLoanResponse data = loanService.payLoan(req);
        assertEquals(0.0, data.totalSpentAmount());
        assertEquals(0, data.numberOfPaidInstallment());
        assertFalse(data.isPaid());
    }

    @Test
    void payLoanOnlyOne(){
        PayLoanRequestBody req = new PayLoanRequestBody(1L, 20.0);
        Loan loan = loanHelper.getLoanById(1L, 50.0);
        when(loanRepository.findById(1L)).thenReturn(Optional.ofNullable(loan));
        PayLoanResponse data = loanService.payLoan(req);
        assertEquals(1, data.numberOfPaidInstallment());
        assertFalse(data.isPaid());
    }

    @Test
    void payLoanTwoInstallments(){
        PayLoanRequestBody req = new PayLoanRequestBody(1L, 39.0);
        Loan loan = loanHelper.getLoanById(1L, 50.0);
        when(loanRepository.findById(1L)).thenReturn(Optional.ofNullable(loan));
        PayLoanResponse data = loanService.payLoan(req);
        assertEquals(2, data.numberOfPaidInstallment());
        assertFalse(data.isPaid());
    }

    @Test
    void payLoanThreeInstallments(){
        PayLoanRequestBody req = new PayLoanRequestBody(1L, 59.0);
        Loan loan = loanHelper.getLoanById(1L, 50.0);
        when(loanRepository.findById(1L)).thenReturn(Optional.ofNullable(loan));
        PayLoanResponse data = loanService.payLoan(req);
        assertEquals(3, data.numberOfPaidInstallment());
        assertFalse(data.isPaid());
    }

    @Test
    void payLoanFourInstallmentsButThereCalendarMonthAllow(){
        PayLoanRequestBody req = new PayLoanRequestBody(1L, 80.0);
        Loan loan = loanHelper.getLoanById(1L, 50.0);
        when(loanRepository.findById(1L)).thenReturn(Optional.ofNullable(loan));
        PayLoanResponse data = loanService.payLoan(req);
        assertNotEquals(4, data.numberOfPaidInstallment());
        assertFalse(data.isPaid());
    }

    @Test
    void payLoanTheLastThirdInstallment(){
        PayLoanRequestBody req = new PayLoanRequestBody(1L, 60.0);
        Loan loan = loanHelper.getLoanById(1L, 50.0);
        // The first 2 installment are paid
        loan.getInstallments().get(0).setIsPaid(true);
        loan.getInstallments().get(1).setIsPaid(true);

        when(loanRepository.findById(1L)).thenReturn(Optional.ofNullable(loan));
        PayLoanResponse data = loanService.payLoan(req);

        assertEquals(1, data.numberOfPaidInstallment());
        assertFalse(data.isPaid());
    }

}
