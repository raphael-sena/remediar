package com.remediar.back_remediar.model.dto.solicitacoes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ItemDoacaoRequestDTO(
        @NotEmpty(message = "O campo de descrição é obrigatório")
        String descricao,

        @NotNull(message = "O campo de quantidade é obrigatório")
        Short quantidade,

        @JsonFormat(pattern = "dd/MM/yyyy")
        @JsonProperty("dataValidade")
        LocalDate dataValidade,

        String imagem
) {
}
