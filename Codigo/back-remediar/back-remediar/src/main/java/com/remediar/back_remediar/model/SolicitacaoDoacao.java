package com.remediar.back_remediar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "solicitacao_doacao")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SolicitacaoDoacao extends Solicitacao {

    @OneToMany(mappedBy = "solicitacaoDoacao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemSolicitacaoDoacao> itensDoacao = new ArrayList<>();
}

