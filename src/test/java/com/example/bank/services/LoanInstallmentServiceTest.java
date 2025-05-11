package com.example.bank.services;

import com.example.bank.domain.LoanInstallment;
import com.example.bank.dto.LoanInstallmentDTO;
import com.example.bank.exception.LoanException;
import com.example.bank.repository.LoanInstallmentRepository;
import com.example.bank.service.LoanInstallmentService;
import com.example.bank.services.helper.LoanInstallmentHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoanInstallmentServiceTest {

    LoanInstallmentRepository loanInstallmentRepository;
    LoanInstallmentService loanInstallmentService;
    LoanInstallmentHelper helper = new LoanInstallmentHelper();

    @BeforeEach
    void setUpCommon(){
        loanInstallmentRepository = mock(LoanInstallmentRepository.class);
        loanInstallmentService = new LoanInstallmentService(loanInstallmentRepository);
    }

    @Test
    void getLoanInstalmentTest(){
        List<LoanInstallment> loanInstallmentsList = helper.getLoanInstallment();
        when(loanInstallmentRepository.findAllByLoanId(1L)).thenReturn(loanInstallmentsList);
        List<LoanInstallmentDTO> data = loanInstallmentService.getLoanInstallmentList(1L);
        assertEquals(loanInstallmentsList.size(), data.size());
        assertEquals(loanInstallmentsList.getFirst().getId(), data.getFirst().id());
        assertEquals(loanInstallmentsList.getLast().getId(), data.getLast().id());
    }

    @Test
    void getLoanInstalmentNotFoundLoanTest(){
        List<LoanInstallment> loanInstallmentsList = helper.getLoanInstallment();
        when(loanInstallmentRepository.findAllByLoanId(1L)).thenReturn(loanInstallmentsList);
        LoanException exception = assertThrows(LoanException.class, () ->loanInstallmentService.getLoanInstallmentList(2L));
        assertEquals("Loan not found!!", exception.getMessage());
    }

}
