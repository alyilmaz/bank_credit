package com.example.bank.dto;

import com.example.bank.domain.Loan;
import com.example.bank.domain.User;

import java.util.List;
public record CustomerDTO(String name, String surname, Double creditLimit, Double usedCreditLimit, UserDTO user, List<Loan> loans) { }
