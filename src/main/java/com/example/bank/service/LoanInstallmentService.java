package com.example.bank.service;

import com.example.bank.domain.LoanInstallment;
import com.example.bank.dto.LoanInstallmentDTO;
import com.example.bank.exception.LoanException;
import com.example.bank.mapper.LoanInstallmentMapper;
import com.example.bank.repository.LoanInstallmentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class LoanInstallmentService {

    private final LoanInstallmentRepository loanInstallmentRepository;

    public List<LoanInstallmentDTO> getLoanInstallmentList(Long loanId){
        List<LoanInstallment> loadInstallments = loanInstallmentRepository.findAllByLoanId(loanId);
        if (loadInstallments.isEmpty()) {
            throw new LoanException("Loan not found!!");
        }
        log.info("Loan is found to list the installments.");
        return LoanInstallmentMapper.INSTANCE.toDTOList(loadInstallments);
    }
}
