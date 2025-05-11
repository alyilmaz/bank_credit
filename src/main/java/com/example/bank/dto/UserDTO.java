package com.example.bank.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record  UserDTO(
        @NotNull(message = "Username is required")
        @NotEmpty(message = "Username is required")
        String username,
        @NotNull(message = "Password is required")
        @NotEmpty(message = "Password is required")
        String password){}
