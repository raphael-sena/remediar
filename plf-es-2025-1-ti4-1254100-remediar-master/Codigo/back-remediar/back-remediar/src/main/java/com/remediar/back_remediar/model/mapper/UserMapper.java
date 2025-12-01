package com.remediar.back_remediar.model.mapper;

import com.remediar.back_remediar.model.User;
import com.remediar.back_remediar.model.dto.usuarios.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO dto);
    UserDTO toDto(User entity);
}
