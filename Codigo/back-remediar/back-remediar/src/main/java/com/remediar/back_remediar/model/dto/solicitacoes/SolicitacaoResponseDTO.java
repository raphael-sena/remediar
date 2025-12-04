package com.remediar.back_remediar.model.dto.solicitacoes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.remediar.back_remediar.model.dto.usuarios.UsuarioComumResponseDTO;
import com.remediar.back_remediar.model.enums.Status;
import com.remediar.back_remediar.model.enums.TipoSolicitacao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SolicitacaoResponseDTO(
        UUID id,
        UsuarioComumResponseDTO usuario,
        Long funcionarioResponsavelAtual,
        Status statusAtual,
        TipoSolicitacao tipoSolicitacao,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        @JsonProperty("dataHoraCriacao")
        LocalDateTime dataHoraCriacao,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        @JsonProperty("dataHoraUltimaAtualizacao")
        LocalDateTime dataHoraUltimaAtualizacao,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        @JsonProperty("dataHoraFinalizacao")
        LocalDateTime dataHoraFinalizacao,

        List<HistoricoResponseDTO> historico
) {
}
