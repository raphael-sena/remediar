package com.remediar.back_remediar.repository;

import com.remediar.back_remediar.model.SolicitacaoDoacao;
import com.remediar.back_remediar.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface SolicitacaoDoacaoRepository extends JpaRepository<SolicitacaoDoacao, UUID> {
    List<SolicitacaoDoacao> findAllByUsuarioComum_Id(Long id);

    Page<SolicitacaoDoacao> findAllByStatusAtual(Status status, Pageable pageable);
    

}
