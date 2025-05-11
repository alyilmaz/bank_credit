package com.example.bank.dto;

import java.time.LocalDate;
import java.util.Date;

public record LoanInstallmentDTO(Long id, Double amount, Double  paidAmount, LocalDate dueDate, LocalDate paymentDate, Boolean isPaid) {
}
