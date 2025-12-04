package com.remediar.back_remediar.repository;

import com.remediar.back_remediar.model.ItemSolicitacaoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemSolicitacaoPedidoRepository extends JpaRepository<ItemSolicitacaoPedido, Long> {
}
