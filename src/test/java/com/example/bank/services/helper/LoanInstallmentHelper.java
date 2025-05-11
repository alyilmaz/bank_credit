package com.example.bank.services.helper;

import com.example.bank.domain.Loan;
import com.example.bank.domain.LoanInstallment;

import java.util.Arrays;
import java.util.List;

public class LoanInstallmentHelper {

    public List<LoanInstallment> getLoanInstallment(){
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setLoanAmount(100.0);
        loan.setIsPaid(false);

        LoanInstallment inst1 = new LoanInstallment();
        inst1.setId(1L);
        inst1.setIsPaid(false);
        inst1.setLoan(loan);
        LoanInstallment inst2 = new LoanInstallment();
        inst2.setId(2L);
        inst2.setIsPaid(false);
        inst2.setLoan(loan);
        List<LoanInstallment> insts = Arrays.asList(inst1, inst2);
        return insts;
    }
}
