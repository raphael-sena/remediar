package com.remediar.back_remediar.model.enums;

import lombok.Getter;

@Getter
public enum StatusProduto {

    SEM_STATUS(1, "Sem status"),
    BIOLOGICO(2, "Biológico"),
    ESPECIFICO(3, "Específico"),
    FITOTERAPICO(4, "Fitoterápico"),
    GENERICO(5, "Genérico"),
    NOVO(6, "Novo"),
    PRODUTO_TERAPIA_AVANCADA(7, "Produto de Terapia Avançada"),
    RADIOFARMACO(8, "Radiofármaco"),
    SIMILAR(9, "Similar");

    private final int id;
    private final String descricao;

    StatusProduto(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static StatusProduto fromId(int id) {
        for (StatusProduto status : StatusProduto.values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("StatusProduto com id " + id + " não encontrado.");
    }

    public static StatusProduto fromDescricao(String descricao) {
        for (StatusProduto status : StatusProduto.values()) {
            if (status.getDescricao().equalsIgnoreCase(descricao)) {
                return status;
            }
        }
        throw new IllegalArgumentException("StatusProduto com descrição " + descricao + " não encontrado.");
    }
}
