package com.example.bank.mapper;

import com.example.bank.domain.User;
import com.example.bank.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO toDTO(User entity);

    List<UserDTO> toDTOList(List<User> dtoList);

    User toEntity(UserDTO dto);
}
