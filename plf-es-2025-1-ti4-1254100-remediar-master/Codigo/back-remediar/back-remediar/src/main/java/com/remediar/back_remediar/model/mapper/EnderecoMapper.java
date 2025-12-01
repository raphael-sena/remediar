package com.remediar.back_remediar.model.mapper;

import com.remediar.back_remediar.model.Endereco;
import com.remediar.back_remediar.model.dto.EnderecoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {
    Endereco toEntity(EnderecoDTO dto);
    EnderecoDTO toDto(Endereco entity);
}
