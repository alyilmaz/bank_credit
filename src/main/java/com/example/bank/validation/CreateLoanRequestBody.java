package com.example.bank.validation;

import jakarta.validation.constraints.*;

public record CreateLoanRequestBody(
        @NotNull(message = "Customer ID is required")
        @Positive(message = "Customer ID must be positive")
        Long customerId,
        @NotNull(message = "Amount is required")
        @DecimalMin(value = "1.0", message = "Amount must be greater than 0")
        Double amount,

        @NotNull(message = "Interest rate is required")
        @DecimalMin(value = "0.1", inclusive = true, message = "Interest rate must be at least 0.1")
        @DecimalMax(value = "0.5", inclusive = true, message = "Interest rate must be at most 0.5")
        Double interestRate,

        @NotNull(message = "Number of installments is required")
        @AllowedInstallments
        Long numberOfInstallments) {
}
