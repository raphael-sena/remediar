package com.remediar.back_remediar.repository.dashboards;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.remediar.back_remediar.model.Solicitacao;
import com.remediar.back_remediar.model.dto.dashboards.SolicitacoesNaoAtendidasDTO;

public interface SolicitacoesNaoAtendidasRepository extends JpaRepository<Solicitacao, Long> {

        @Query(value = """
                                    SELECT
                        SUM(DOA.QUANTIDADE) AS QUANTIDADE_MEDICAMENTOS,
                        COUNT(*) AS QUANTIDADE_SOLICITACOES
                        FROM ITEM_SOLICITACAO_PEDIDO DOA
                        INNER JOIN solicitacao_PEDIDO sol ON sol.ITEM_SOLICITACAO_PEDIDO_ID = doa.ID
                        WHERE sol.status_atual IN (0, 11)
                        AND sol.data_hora_ultima_atualizacao BETWEEN :dataInicio AND :dataFim
                                    """, nativeQuery = true)
        SolicitacoesNaoAtendidasDTO findSolicitacoesNaoAtendidas(
                        @Param("dataInicio") LocalDateTime dataInicio,
                        @Param("dataFim") LocalDateTime dataFim);
}
