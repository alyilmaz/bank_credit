package com.example.bank.contoller;

import com.example.bank.controller.LoanInstallmentController;
import com.example.bank.dto.LoanInstallmentDTO;
import com.example.bank.service.LoanInstallmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoanInstallmentControllerTest {

    LoanInstallmentService loanInstallmentService;
    LoanInstallmentController loanInstallmentController;


    @BeforeEach
    void setUpCommon(){
        loanInstallmentService = mock(LoanInstallmentService.class);
        loanInstallmentController = new LoanInstallmentController(loanInstallmentService);
    }

    @Test
    void getListTest(){
        LoanInstallmentDTO d1 = new LoanInstallmentDTO(1L, 50.0, 20.0, null, null, false);
        LoanInstallmentDTO d2 = new LoanInstallmentDTO(2L, 70.0, 25.0, null, null, false);
        List<LoanInstallmentDTO> dtoList = List.of(d1, d2);
        when(loanInstallmentService.getLoanInstallmentList(1L)).thenReturn(dtoList);


        List<LoanInstallmentDTO> data = loanInstallmentController.getList(1L);
        assertEquals(dtoList.size(), data.size());
    }

    @Test
    void getListTestButDoesNotExist(){
        LoanInstallmentDTO d1 = new LoanInstallmentDTO(1L, 50.0, 20.0, null, null, false);
        LoanInstallmentDTO d2 = new LoanInstallmentDTO(2L, 70.0, 25.0, null, null, false);
        List<LoanInstallmentDTO> dtoList = List.of(d1, d2);
        when(loanInstallmentService.getLoanInstallmentList(1L)).thenReturn(dtoList);
        List<LoanInstallmentDTO> data = loanInstallmentController.getList(2L);
        assertEquals(0, data.size());
    }
}
