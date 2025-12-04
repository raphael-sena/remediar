package com.remediar.back_remediar.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    PENDENTE(1, "Pendente"),
    ALTERADA_PELO_USUARIO(2, "Alterada Pelo Usuário"),
    EM_ANALISE(3, "Em Análise"),
    APROVADA(4, "Aprovada"),
    EM_SEPARACAO(5, "Em Separação"),
    SEPARADA(6, "Separada"),
    AGUARDANDO_RETIRADA(7, "Aguardando Retirada"),
    AGUARDANDO_ENTREGA(8, "Aguardando Entrega"),
    EM_TRANSPORTE(9, "Em Transporte"),
    CONCLUIDA(10, "Concluída"),
    CANCELADA(11, "Cancelada"),
    SEM_ESTOQUE(12, "Sem Estoque"),
    AGUARDANDO_USUARIO(13, "Aguardando Usuário");

    private final int codigo;
    private final String descricao;

    public static Status fromDescricao(String descricao) {
        for (Status status : values()) {
            if (status.getDescricao().equalsIgnoreCase(descricao)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status não encontrado: " + descricao);
    }
}
