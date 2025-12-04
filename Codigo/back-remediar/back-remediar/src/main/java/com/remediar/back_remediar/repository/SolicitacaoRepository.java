package com.remediar.back_remediar.repository;

import com.remediar.back_remediar.model.Solicitacao;
import com.remediar.back_remediar.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, UUID> {
    List<Solicitacao> findAllByFuncionarioResponsavelAtual_Id(Long id);

    List<Solicitacao> findAllByFuncionarioResponsavelAtual_IdAndStatusAtual(Long id, Status statusEnum);
}
