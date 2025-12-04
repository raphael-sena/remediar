package com.remediar.back_remediar.model.dto.solicitacoes;

import java.util.List;

public record DoacaoResponseDTO(
        SolicitacaoResponseDTO solicitacao,
        List<ItemDoacaoDTO> itensDoacao
) {
}
