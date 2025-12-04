package com.remediar.back_remediar.model;

import com.remediar.back_remediar.model.enums.StatusProduto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicamento")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Medicamento extends Produto {

    @Column(length = 5000)
    private String principioAtivo;

    private String codigoBarras;

    private String laboratorio;

    private Double precoMaximo;

    @Enumerated
    private StatusProduto statusProduto;

    private boolean fromBase = false;
}
