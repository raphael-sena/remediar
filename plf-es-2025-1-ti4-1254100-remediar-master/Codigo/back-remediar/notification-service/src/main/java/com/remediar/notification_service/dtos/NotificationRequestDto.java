package com.remediar.notification_service.dtos;

import java.util.UUID;

public record NotificationRequestDto(
        Long userId,
        String emailFrom,
        String destino,
        String titulo,
        String mensagem,
        TipoCanal tipoCanal,
        UUID solicitacaoId,         // novo
        String tipoSolicitacao      // novo
) {}
