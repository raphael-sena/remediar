package com.remediar.back_remediar.model.dto;

import com.remediar.back_remediar.model.enums.StatusProduto;

public record MedicamentoDTO(
        Long id,
        String nomeComercial,
        String principioAtivo,
        String apresentacao,
        String codigoBarras,
        String laboratorio,
        Double precoMaximo,
        StatusProduto statusProduto
) {
}
