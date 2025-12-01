package com.remediar.back_remediar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 5000)
    private String apresentacao;

    @Column(length = 5000)
    private String nomeComercial;

    @OneToMany(mappedBy = "produto", fetch = FetchType.LAZY)
    private List<ItemSolicitacaoPedido> itemSolicitacaoPedido = new ArrayList<>();
}
