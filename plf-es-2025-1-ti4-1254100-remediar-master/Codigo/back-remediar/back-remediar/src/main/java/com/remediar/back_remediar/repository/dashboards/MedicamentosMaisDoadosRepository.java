package com.remediar.back_remediar.repository.dashboards;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.remediar.back_remediar.model.Medicamento;
import com.remediar.back_remediar.model.dto.dashboards.MedicamentosMaisDoadosDTO;

public interface MedicamentosMaisDoadosRepository extends JpaRepository<Medicamento, Long> {

    @Query(value = """
                  SELECT
            COALESCE(med.apresentacao, med_ctrl.apresentacao) AS apresentacao,
              	COALESCE(med.id, med_ctrl.id) AS id,
              	SUM(ITE.quantidade) AS quantidade_total
            FROM SOLICITACAO_PEDIDO PED
            INNER JOIN ITEM_SOLICITACAO_PEDIDO ITE ON ITE.ID = PED.ITEM_SOLICITACAO_PEDIDO_ID
            LEFT JOIN medicamento med ON med.id = ITE.item_estoque_id
            LEFT JOIN medicamento_controlado med_ctrl ON med_ctrl.id = ITE.item_estoque_id
            WHERE PED.STATUS_ATUAL = 9
            AND PED.data_hora_ultima_atualizacao BETWEEN :dataInicio AND :dataFim
            GROUP BY
              	COALESCE(med.id, med_ctrl.id),
              	COALESCE(med.apresentacao, med_ctrl.apresentacao)
            ORDER BY
              		quantidade_total DESC
            LIMIT 5;
                  """, nativeQuery = true)
    List<MedicamentosMaisDoadosDTO> findTop5MaisDoados(
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim);
}
