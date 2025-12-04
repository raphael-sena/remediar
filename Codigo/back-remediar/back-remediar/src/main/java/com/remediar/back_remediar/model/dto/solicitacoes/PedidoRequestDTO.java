package com.remediar.back_remediar.model.dto.solicitacoes;

import com.remediar.back_remediar.model.enums.ModoEntrega;
import jakarta.validation.Valid;

public record PedidoRequestDTO(

        @Valid
        ItemSolicitacaoDTO item,

        @Valid
        ModoEntrega modoEntrega,

        @Valid
        Long usuarioId,

        @Valid
        PrescricaoMedicaDTO prescricaoMedica
) {
}
