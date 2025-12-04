package com.remediar.back_remediar.model.dto.solicitacoes;

public record EstatisticasPedidoDTO(
        Long quantidadeTotalMedicamentos,
        Long quantidadeSolicitacoes,
        String usuarioNome
) {
} 