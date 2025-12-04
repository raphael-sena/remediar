package com.remediar.back_remediar.repository;

import com.remediar.back_remediar.model.ItemSolicitacaoDoacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemSolicitacaoDoacaoRepository extends JpaRepository<ItemSolicitacaoDoacao, Long> {

    void deleteAllBySolicitacaoDoacaoId(UUID id);
}
