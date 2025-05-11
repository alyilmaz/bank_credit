package com.example.bank.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class AllowedInstallmentsValidator implements ConstraintValidator<AllowedInstallments, Long> {

    private static final Set<Long> ALLOWED_VALUES = Set.of(6L, 9L, 12L, 24L);

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return value != null && ALLOWED_VALUES.contains(value);
    }
}

