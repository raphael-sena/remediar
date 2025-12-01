package com.remediar.back_remediar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class UsuarioComum {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String documento;

    @Column(nullable = false)
    private String telefone;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(
            name = "endereco_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_funcionario_endereco")
    )
    private Endereco endereco;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "usuarioComum", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Solicitacao> solicitacoes = new ArrayList<>();
}
