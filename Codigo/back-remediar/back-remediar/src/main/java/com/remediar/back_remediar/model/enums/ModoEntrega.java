package com.remediar.back_remediar.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ModoEntrega {

    RETIRADA(1, "Retirada"),
    ENVIO(2, "Envio");

    private final int id;
    private final String descricao;

    public static ModoEntrega fromDescricao(String descricao) {
        for (ModoEntrega modo : values()) {
            if (modo.getDescricao().equalsIgnoreCase(descricao)) {
                return modo;
            }
        }
        throw new IllegalArgumentException("Modo de entrega não encontrado: " + descricao);
    }
}
