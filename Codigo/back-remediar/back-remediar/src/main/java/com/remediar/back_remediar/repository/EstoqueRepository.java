package com.remediar.back_remediar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.remediar.back_remediar.model.Estoque;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    
    Optional<Estoque> findByNome(String nome);

@Query("SELECT e FROM Estoque e LEFT JOIN FETCH e.itens WHERE e.id = :id")
Optional<Estoque> findByIdWithItens(@Param("id") Long id);

    boolean existsByNomeIgnoreCase(String nome);

    Optional<Estoque> findByItens_Id(Long id);
}