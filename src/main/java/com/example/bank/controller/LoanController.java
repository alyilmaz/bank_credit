package com.example.bank.controller;

import com.example.bank.dto.LoanDTO;
import com.example.bank.service.LoanService;
import com.example.bank.validation.CreateLoanRequestBody;
import com.example.bank.validation.PayLoanRequestBody;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/loan")
public class LoanController {

    private final LoanService loanService;

    @GetMapping("/{customerId}")
    public ResponseEntity<?> listLoan(@PathVariable Long customerId){
        return ResponseEntity.ok(loanService.getList(customerId));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLoan(@Valid @RequestBody CreateLoanRequestBody loan){
        return ResponseEntity.ok(loanService.createLoan(loan));
    }

    @PostMapping("/pay")
    public ResponseEntity<?> payLoan(@RequestBody PayLoanRequestBody payLoan){
        return ResponseEntity.ok(loanService.payLoan(payLoan));
    }
}
