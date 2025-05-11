package com.example.bank.controller.response;

public record PayLoanResponse(int numberOfPaidInstallment, Double totalSpentAmount, boolean isPaid) {
}
