package com.example.bank.validation;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PayLoanRequestBody(
        @NotNull(message = "Loan ID is required")
        @Positive(message = "Loan ID must be positive")
        Long loanId,
        @NotNull(message = "Amount is required")
        @DecimalMin(value = "1.0", message = "Amount must be greater than 0")
        Double amount) {
}
