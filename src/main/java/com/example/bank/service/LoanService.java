package com.example.bank.service;

import com.example.bank.controller.response.PayLoanResponse;
import com.example.bank.domain.Customer;
import com.example.bank.domain.Loan;
import com.example.bank.domain.LoanInstallment;
import com.example.bank.dto.LoanDTO;
import com.example.bank.exception.LoanException;
import com.example.bank.mapper.LoanMapper;
import com.example.bank.repository.CustomerRepository;
import com.example.bank.repository.LoanRepository;
import com.example.bank.validation.CreateLoanRequestBody;
import com.example.bank.validation.PayLoanRequestBody;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class LoanService {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;

    public List<LoanDTO> getList(Long customerId){
        List<Loan> loans = loanRepository.findAllByCustomerId(customerId);
        if (loans.isEmpty()){
            throw new LoanException("Customer or Customer's loan NOT found");
        }
        log.info("Customer is found to list loan.");
        return LoanMapper.INSTANCE.toDTOList(loans);
    }

    public LoanDTO createLoan(CreateLoanRequestBody loan){
        Customer customer = customerRepository.findById(loan.customerId())
                .orElseThrow(() -> new LoanException("Customer not found"));
        log.info("Customer is found to create a loan.");
        double totalAmount = loan.amount() * (1 + loan.interestRate());
        double availableLimit = customer.getCreditLimit() - customer.getUsedCreditLimit();

        if(totalAmount > availableLimit){
            throw new LoanException("Your Limit is insufficient");
        }
        log.info("Customer's limit is sufficient to create a loan.");

        double installmentAmount = Math.round((totalAmount / loan.numberOfInstallments()) * 100.0) / 100.0;
        List<LoanInstallment> installments = new ArrayList<>();
        LocalDate now = LocalDate.now();
        LocalDate firstDueDate = now.withDayOfMonth(1).plusMonths(1);

        Loan loanEntity = new Loan();
        loanEntity.setLoanAmount(loan.amount());
        loanEntity.setCustomer(customer);

        for (int i = 0; i < loan.numberOfInstallments(); i++) {
            LoanInstallment inst = new LoanInstallment();
            inst.setAmount(installmentAmount);
            inst.setDueDate(firstDueDate.plusMonths(i));
            inst.setLoan(loanEntity);
            inst.setIsPaid(false);
            installments.add(inst);
        }

        loanEntity.setInstallments(installments);
        loanEntity.setCreateDate(LocalDate.now());
        loanEntity.setIsPaid(false);
        loanEntity.setNumberOfInstallment(loan.numberOfInstallments());

        customer.setUsedCreditLimit(customer.getUsedCreditLimit() + totalAmount);
        customerRepository.save(customer);

        log.info("A loan was created.");

        return LoanMapper.INSTANCE.toDTO(loanRepository.save(loanEntity));
    }

    public PayLoanResponse payLoan(PayLoanRequestBody payLoan){
        Loan loan = loanRepository.findById(payLoan.loanId())
                .orElseThrow(() -> new LoanException("Loan not found"));

        log.info("Loan is found to pay a installment.");

        Double amount = payLoan.amount();
        List<LoanInstallment> unpaidInstallments = loan.getInstallments().stream()
                .filter(i -> !i.getIsPaid())
                .sorted(Comparator.comparing(LoanInstallment::getDueDate))
                .toList();
        LocalDate now = LocalDate.now();
        LocalDate maxPayableDate = now.plusMonths(3).withDayOfMonth(1);

        int paidCount = 0;
        double totalPaid = 0;

        for (LoanInstallment inst : unpaidInstallments) {
            if (inst.getDueDate().isAfter(maxPayableDate)) break;

            if (amount >= inst.getAmount()) {
                amount -= inst.getAmount();
                totalPaid += inst.getAmount();
                inst.setIsPaid(true);
                inst.setPaymentDate(LocalDate.now());
                inst.setPaidAmount(inst.getAmount());
                paidCount++;
                log.info("Installment is paid.");
            } else {
                log.info("Amount is not sufficient to pay a installment.");
                break;
            }
        }

        if (paidCount == 0) {
            return new PayLoanResponse(0, 0.0, false);
        }

        loan.getCustomer().setUsedCreditLimit(loan.getCustomer().getUsedCreditLimit() - totalPaid);

        boolean loanFullyPaid = loan.getInstallments().stream().allMatch(LoanInstallment::getIsPaid);
        loan.setIsPaid(loanFullyPaid);

        loanRepository.save(loan);
        log.info("Payment is successfully for installment");

        return new PayLoanResponse(paidCount, totalPaid, loanFullyPaid);

    }
}
