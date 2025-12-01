package com.remediar.back_remediar.model;

import com.remediar.back_remediar.model.enums.Genero;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(
            name = "endereco_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_funcionario_endereco")
    )
    private Endereco endereco;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_funcionario_user")
    )
    private User user;

    @OneToMany(mappedBy = "funcionarioResponsavelAtual")
    private List<Solicitacao> solicitacoes = new ArrayList<>();

    @OneToMany(mappedBy = "funcionario")
    private List<Historico> historicos = new ArrayList<>();
}
