package com.remediar.back_remediar.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoriaPortaria {

    A1(1, "A1"),
    A2(2, "A2"),
    A3(3, "A3"),
    B1(4, "B1"),
    B2(5, "B2"),
    C1(6, "C1"),
    C2(7, "C2"),
    C3(8, "C3"),
    C4(9, "C4"),
    C5(10, "C5"),
    D1(11, "D1"),
    D2(12, "D2"),
    E(13, "E"),
    F1(14, "F1"),
    F2(15, "F2"),
    F3(16, "F3"),
    F4(17, "F4");

    private final int id;
    private final String descricao;

    public static CategoriaPortaria fromDescricao(String descricao) {
        if (descricao == null) {
            throw new IllegalArgumentException("Descrição da categoria está nula.");
        }

        String descricaoLimpa = descricao
                .trim()
                .replace("\u00A0", "")
                .replace("\u200B", "")
                .replaceAll("[^\\x20-\\x7E]", "");

        for (CategoriaPortaria categoria : values()) {
            if (categoria.getDescricao().equalsIgnoreCase(descricaoLimpa)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Categoria não encontrada: " + descricao);
    }
}
