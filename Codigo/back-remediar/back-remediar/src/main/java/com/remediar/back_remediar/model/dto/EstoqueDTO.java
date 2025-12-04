package com.remediar.back_remediar.model.dto;

import java.util.List;

public record EstoqueDTO(
        Long id,
        List<ItemEstoqueDTO> itens,
        String nome,
        EnderecoDTO endereco
) {
}
