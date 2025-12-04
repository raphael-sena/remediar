package com.remediar.back_remediar.model.dto.solicitacoes;

public record PedidoResponseDTO(
        SolicitacaoResponseDTO solicitacao,
        ItemSolicitacaoDTO item,
        String modoEntrega,
        PrescricaoMedicaDTO prescricaoMedica
) {
}
