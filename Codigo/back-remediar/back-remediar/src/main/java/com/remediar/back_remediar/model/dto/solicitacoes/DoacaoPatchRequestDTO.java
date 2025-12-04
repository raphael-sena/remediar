package com.remediar.back_remediar.model.dto.solicitacoes;

import jakarta.validation.Valid;

import java.util.List;

public record DoacaoPatchRequestDTO(

        @Valid
        List<ItemDoacaoDTO> itens
) {
}
