package com.remediar.back_remediar.model.dto.solicitacoes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record ItemDoacaoDTO(
        ItemSolicitacaoDTO item,

        @JsonFormat(pattern = "dd/MM/yyyy")
        @JsonProperty("dataValidade")
        LocalDate dataValidade,

        String imagem
) {
}
