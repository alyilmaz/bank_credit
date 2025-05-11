package com.example.bank.repository;

import com.example.bank.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findAllByCustomerId(Long customerId);
}
