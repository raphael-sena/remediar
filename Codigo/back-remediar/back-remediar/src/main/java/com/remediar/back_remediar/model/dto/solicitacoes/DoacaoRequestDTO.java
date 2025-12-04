package com.remediar.back_remediar.model.dto.solicitacoes;

import jakarta.validation.Valid;

import java.util.List;

public record DoacaoRequestDTO(

        @Valid
        List<ItemDoacaoRequestDTO> itens,

        @Valid
        Long usuarioId
) {
}
