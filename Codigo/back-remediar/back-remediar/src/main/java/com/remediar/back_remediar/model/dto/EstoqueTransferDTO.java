package com.remediar.back_remediar.model.dto;

public record EstoqueTransferDTO(
    List<ItemEstoqueDTO> itensEstoque,
    Long idEstoqueOrigem,
    Long idEstoqueDestino
) {
} 