package com.remediar.back_remediar.model.mapper;

import com.remediar.back_remediar.model.Medicamento;
import com.remediar.back_remediar.model.dto.MedicamentoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicamentoMapper {

    MedicamentoDTO toDTO(Medicamento entity);

    Medicamento toEntity(MedicamentoDTO dto);
}
