package com.remediar.back_remediar.model;

import com.remediar.back_remediar.model.enums.ModoEntrega;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "solicitacao_pedido")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SolicitacaoPedido extends Solicitacao {

    @Enumerated(EnumType.STRING)
    private ModoEntrega modoEntrega;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prescricao_medica_id", nullable = false)
    private PrescricaoMedica prescricaoMedica;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "item_solicitacao_pedido_id", nullable = false)
    private ItemSolicitacaoPedido itemSolicitacaoPedido;
}

