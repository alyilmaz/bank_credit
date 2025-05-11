package com.example.bank.dto;

import com.example.bank.domain.Customer;
import com.example.bank.domain.LoanInstallment;

import java.time.LocalDate;
import java.util.List;

public record LoanDTO(Long id, Double loanAmount, Long  numberOfInstallment, LocalDate createDate, Boolean isPaid, List<LoanInstallmentDTO> installments) { }
