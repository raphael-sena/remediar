package com.remediar.back_remediar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "item_solicitacao_doacao")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemSolicitacaoDoacao extends ItemSolicitacao {

    @ManyToOne
    @JoinColumn(name = "solicitacao_doacao_id", nullable = false)
    private SolicitacaoDoacao solicitacaoDoacao;

    private LocalDate dataValidade;

    @Column(name = "imagem", columnDefinition = "TEXT")
    private String imagem;
}
