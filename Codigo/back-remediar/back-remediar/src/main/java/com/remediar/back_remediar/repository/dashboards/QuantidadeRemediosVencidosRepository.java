package com.remediar.back_remediar.repository.dashboards;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.remediar.back_remediar.model.ItemEstoque;
import com.remediar.back_remediar.model.dto.dashboards.RemediosVencidosDTO;

public interface QuantidadeRemediosVencidosRepository extends JpaRepository<ItemEstoque, Long> {

    @Query(value = """
            SELECT 
            SUM(QUANTIDADE) AS QUANTIDADE_VENCIDA
            FROM ITEM_ESTOQUE
            WHERE DATA_VALIDADE BETWEEN :dataInicio AND :dataFim
            """, nativeQuery = true)
    RemediosVencidosDTO findRemediosVencidos(
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim);
}
