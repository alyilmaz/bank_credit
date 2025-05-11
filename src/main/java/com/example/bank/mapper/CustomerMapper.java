package com.example.bank.mapper;

import com.example.bank.domain.Customer;
import com.example.bank.domain.User;
import com.example.bank.dto.CustomerDTO;
import com.example.bank.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    CustomerDTO toDTO(Customer entity);

    List<CustomerDTO> toDTOList(List<Customer> dtoList);

    Customer toEntity(CustomerDTO dto);
}
