package com.remediar.back_remediar.model.dto.solicitacoes;

import com.remediar.back_remediar.model.enums.ModoEntrega;
import jakarta.validation.Valid;

public record PedidoPatchRequestDTO(
        @Valid
        ItemSolicitacaoPatchRequestDTO item,

        @Valid
        ModoEntrega modoEntrega,

        @Valid
        PrescricaoMedicaPatchRequestDTO prescricaoMedica
) {
}
