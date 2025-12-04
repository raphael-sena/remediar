package com.remediar.back_remediar.model.dto.solicitacoes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.remediar.back_remediar.model.dto.usuarios.FuncionarioResponseDTO;
import com.remediar.back_remediar.model.enums.Status;

import java.time.LocalDateTime;
import java.util.UUID;

public record HistoricoResponseDTO(
        Long id,
        UUID solicitacaoId,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        @JsonProperty("dataHora")
        LocalDateTime dataHora,

        FuncionarioResponseDTO funcionario,
        Status status,
        String observacao
) {
}
