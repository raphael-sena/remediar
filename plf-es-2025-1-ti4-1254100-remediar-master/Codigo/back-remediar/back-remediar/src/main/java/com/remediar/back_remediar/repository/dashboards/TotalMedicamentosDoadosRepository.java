package com.remediar.back_remediar.repository.dashboards;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.remediar.back_remediar.model.Medicamento;
import com.remediar.back_remediar.model.dto.dashboards.TotalMedicamentosDoadosDTO;

public interface TotalMedicamentosDoadosRepository extends JpaRepository<Medicamento, Long> {

        @Query(value = """
                        SELECT
                            SUM(DOA.QUANTIDADE) AS QUANTIDADE_MEDICAMENTOS
                            FROM ITEM_SOLICITACAO_PEDIDO DOA
                            INNER JOIN solicitacao_PEDIDO sol ON sol.ITEM_SOLICITACAO_PEDIDO_ID = doa.ID
                            WHERE sol.status_atual = 9
                            AND sol.data_hora_ultima_atualizacao BETWEEN :dataInicio AND :dataFim
                        """, nativeQuery = true)
        TotalMedicamentosDoadosDTO findTotalDoado(
                        @Param("dataInicio") LocalDateTime dataInicio,
                        @Param("dataFim") LocalDateTime dataFim);
}
