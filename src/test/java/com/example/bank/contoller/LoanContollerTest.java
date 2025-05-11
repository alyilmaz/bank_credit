package com.example.bank.contoller;


import com.example.bank.controller.LoanController;
import com.example.bank.controller.LoanInstallmentController;
import com.example.bank.controller.response.PayLoanResponse;
import com.example.bank.dto.LoanDTO;
import com.example.bank.mapper.LoanMapper;
import com.example.bank.service.LoanInstallmentService;
import com.example.bank.service.LoanService;
import com.example.bank.services.helper.LoanHelper;
import com.example.bank.validation.CreateLoanRequestBody;
import com.example.bank.validation.PayLoanRequestBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LoanContollerTest {

    LoanHelper helper = new LoanHelper();
    private LoanService loanService;
    private LoanController loanController;

    @BeforeEach
    void setUpCommon(){
        loanService = mock(LoanService.class);
        loanController = new LoanController(loanService);
    }



    @Test
    void getListLoanTest() {
        List<LoanDTO> loans = LoanMapper.INSTANCE.toDTOList(helper.getLoanList());
        when(loanService.getList(1L)).thenReturn(loans);
        ResponseEntity<?> data = loanController.listLoan(1L);
        verify(loanService).getList(1L);
        assertEquals(HttpStatus.OK, data.getStatusCode());
        assertEquals(loans.size(), ((List<LoanDTO>)data.getBody()).size());
    }

    @Test
    void getListLoanButCustomerDoesNotExistTest() throws Exception {
        List<LoanDTO> loans = LoanMapper.INSTANCE.toDTOList(helper.getLoanList());
        when(loanService.getList(1L)).thenReturn(loans);
        ResponseEntity<?> data = loanController.listLoan(2L);
        assertEquals(HttpStatus.OK, data.getStatusCode());
        assertEquals(0, ((LinkedList)data.getBody()).size());
    }

    @Test
    void createLoan()  {
        CreateLoanRequestBody body = new CreateLoanRequestBody(1L, 50.0, 0.5, 6L);
        LoanDTO dto = LoanMapper.INSTANCE.toDTO(helper.getLoanList().getFirst());
        when(loanService.createLoan(any(CreateLoanRequestBody.class))).thenReturn(dto);
        ResponseEntity<?> data = loanController.createLoan(body);
        verify(loanService).createLoan(any(CreateLoanRequestBody.class));
        assertEquals(HttpStatus.OK, data.getStatusCode());
        assertEquals(dto.id(), ((LoanDTO)data.getBody()).id());
    }

    @Test
    void payLoan(){
        PayLoanRequestBody body = new PayLoanRequestBody(1l, 50.0);
        PayLoanResponse res = new PayLoanResponse(6, 50.0, false);
        when(loanService.payLoan(body)).thenReturn(res);
        ResponseEntity<?> data = loanController.payLoan(body);
        verify(loanService).payLoan(any(PayLoanRequestBody.class));
        assertEquals(HttpStatus.OK, data.getStatusCode());
        assertEquals(res.numberOfPaidInstallment(), ((PayLoanResponse)data.getBody()).numberOfPaidInstallment());
    }
}
