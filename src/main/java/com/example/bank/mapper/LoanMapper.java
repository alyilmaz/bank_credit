package com.example.bank.mapper;

import com.example.bank.domain.Loan;
import com.example.bank.dto.LoanDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper()
public interface LoanMapper {

    LoanMapper INSTANCE = Mappers.getMapper(LoanMapper.class);
    LoanDTO toDTO(Loan entity);

    List<LoanDTO> toDTOList(List<Loan> dtoList);

    Loan toEntity(LoanDTO dto);
}
