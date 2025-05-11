package com.example.bank.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AllowedInstallmentsValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowedInstallments {
    String message() default "Installments must be one of 6, 9, 12, 24";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
