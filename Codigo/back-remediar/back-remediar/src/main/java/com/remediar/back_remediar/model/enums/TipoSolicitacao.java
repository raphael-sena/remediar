package com.remediar.back_remediar.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoSolicitacao {
    PEDIDO(1, "Pedido"),
    DOACAO(2, "Doação");

    private final int id;
    private final String descricao;

    public static TipoSolicitacao fromDescricao(String descricao) {
        for (TipoSolicitacao tipo : values()) {
            if (tipo.getDescricao().equalsIgnoreCase(descricao)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de solicitação não encontrado: " + descricao);
    }
}
