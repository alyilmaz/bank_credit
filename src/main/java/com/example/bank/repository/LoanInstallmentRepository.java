package com.example.bank.repository;

import com.example.bank.domain.LoanInstallment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanInstallmentRepository extends JpaRepository<LoanInstallment, Long> {

    List<LoanInstallment> findAllByLoanId(Long loanId);
}
