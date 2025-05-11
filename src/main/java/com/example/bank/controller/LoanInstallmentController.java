package com.example.bank.controller;

import com.example.bank.dto.LoanInstallmentDTO;
import com.example.bank.service.LoanInstallmentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/loaninstallment")
public class LoanInstallmentController {

    private final LoanInstallmentService loanInstallmentService;

    @GetMapping("/{loanId}")
    public List<LoanInstallmentDTO> getList(@PathVariable Long loanId){
        return loanInstallmentService.getLoanInstallmentList(loanId);
    }
}
