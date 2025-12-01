package com.remediar.back_remediar.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.remediar.back_remediar.model.Estoque;
import com.remediar.back_remediar.model.dto.EstoqueDTO;
import com.remediar.back_remediar.model.dto.EstoqueUpdateDTO;

@Mapper(componentModel = "spring", uses = {ItemEstoqueMapper.class})
public interface EstoqueMapper {

    EstoqueMapper INSTANCE = Mappers.getMapper(EstoqueMapper.class);

    EstoqueDTO toDTO(Estoque entity);

    Estoque toEntity(EstoqueDTO dto);
}