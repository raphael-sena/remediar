package com.remediar.back_remediar.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record ItemEstoqueDTO(
        Long itemEstoqueId,
        Long produtoId,
        String nomeComercial,
        String principioAtivo,
        String apresentacao,
        int quantidade,

        @JsonFormat(pattern = "dd/MM/yyyy")
        @JsonProperty("dataValidade")
        LocalDate dataValidade
) {
}
