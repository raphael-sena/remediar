package com.remediar.back_remediar.repository.dashboards;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.remediar.back_remediar.model.ItemEstoque;
import com.remediar.back_remediar.model.dto.dashboards.FaixaEtariaSolicitacoesDTO;

public interface FaixaEtariaSolicitacoesRepository extends JpaRepository<ItemEstoque, Long> {

    @Query(value = """
                WITH faixa_etaria_agg AS (
                SELECT
                    CASE
                        WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, USU.DATA_NASCIMENTO)) BETWEEN 0 AND 5 THEN '(0-5)'
                        WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, USU.DATA_NASCIMENTO)) BETWEEN 6 AND 18 THEN '(6-18)'
                        WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, USU.DATA_NASCIMENTO)) BETWEEN 19 AND 23 THEN '(19-23)'
                        WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, USU.DATA_NASCIMENTO)) BETWEEN 24 AND 33 THEN '(24-33)'
                        WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, USU.DATA_NASCIMENTO)) BETWEEN 34 AND 38 THEN '(34-38)'
                        WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, USU.DATA_NASCIMENTO)) BETWEEN 39 AND 43 THEN '(39-43)'
                        WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, USU.DATA_NASCIMENTO)) BETWEEN 44 AND 48 THEN '(44-48)'
                        WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, USU.DATA_NASCIMENTO)) BETWEEN 49 AND 53 THEN '(49-53)'
                        WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, USU.DATA_NASCIMENTO)) BETWEEN 54 AND 58 THEN '(54-58)'
                        ELSE '(59 ou mais)'
                    END AS faixa_etaria,
                    SUM(ITE.QUANTIDADE) AS quantidade_doacoes
                FROM SOLICITACAO_PEDIDO PED
                INNER JOIN USUARIO_COMUM_PF USU ON USU.ID = PED.USUARIO_ID
                INNER JOIN ITEM_SOLICITACAO_PEDIDO ITE ON PED.ITEM_SOLICITACAO_PEDIDO_ID = ITE.ID
                WHERE PED.DATA_HORA_ULTIMA_ATUALIZACAO BETWEEN :dataInicio AND :dataFim
                GROUP BY
                    CASE
                        WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, USU.DATA_NASCIMENTO)) BETWEEN 0 AND 5 THEN '(0-5)'
                        WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, USU.DATA_NASCIMENTO)) BETWEEN 6 AND 18 THEN '(6-18)'
                        WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, USU.DATA_NASCIMENTO)) BETWEEN 19 AND 23 THEN '(19-23)'
                        WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, USU.DATA_NASCIMENTO)) BETWEEN 24 AND 33 THEN '(24-33)'
                        WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, USU.DATA_NASCIMENTO)) BETWEEN 34 AND 38 THEN '(34-38)'
                        WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, USU.DATA_NASCIMENTO)) BETWEEN 39 AND 43 THEN '(39-43)'
                        WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, USU.DATA_NASCIMENTO)) BETWEEN 44 AND 48 THEN '(44-48)'
                        WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, USU.DATA_NASCIMENTO)) BETWEEN 49 AND 53 THEN '(49-53)'
                        WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, USU.DATA_NASCIMENTO)) BETWEEN 54 AND 58 THEN '(54-58)'
                        ELSE '(59 ou mais)'
                    END
            )

            SELECT
                f.faixa_etaria,
                COALESCE(a.quantidade_doacoes, 0) AS quantidade_doacoes
            FROM (
                VALUES
                    ('(0-5)'),
                    ('(6-18)'),
                    ('(19-23)'),
                    ('(24-33)'),
                    ('(34-38)'),
                    ('(39-43)'),
                    ('(44-48)'),
                    ('(49-53)'),
                    ('(54-58)'),
                    ('(59 ou mais)')
            ) AS f(faixa_etaria)
            LEFT JOIN faixa_etaria_agg a ON f.faixa_etaria = a.faixa_etaria
            ORDER BY CASE f.faixa_etaria
                WHEN '(0-5)' THEN 1
                WHEN '(6-18)' THEN 2
                WHEN '(19-23)' THEN 3
                WHEN '(24-33)' THEN 4
                WHEN '(34-38)' THEN 5
                WHEN '(39-43)' THEN 6
                WHEN '(44-48)' THEN 7
                WHEN '(49-53)' THEN 8
                WHEN '(54-58)' THEN 9
                WHEN '(59 ou mais)' THEN 10
                ELSE 11
            END;
                        """, nativeQuery = true)
    List<FaixaEtariaSolicitacoesDTO> findFaixaEtariaSolicitacoes(
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim);
}
