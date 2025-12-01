package com.remediar.back_remediar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item_solicitacao_pedido")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemSolicitacaoPedido extends ItemSolicitacao {

    @OneToOne(mappedBy = "itemSolicitacaoPedido")
    private SolicitacaoPedido solicitacaoPedido;

}
