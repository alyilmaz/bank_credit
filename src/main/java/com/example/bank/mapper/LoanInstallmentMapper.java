package com.example.bank.mapper;

import com.example.bank.domain.LoanInstallment;
import com.example.bank.dto.LoanInstallmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LoanInstallmentMapper {

    LoanInstallmentMapper INSTANCE = Mappers.getMapper(LoanInstallmentMapper.class);
    LoanInstallmentDTO toDTO(LoanInstallment entity);

    List<LoanInstallmentDTO> toDTOList(List<LoanInstallment> dtoList);

    LoanInstallment toEntity(LoanInstallmentDTO dto);
}
