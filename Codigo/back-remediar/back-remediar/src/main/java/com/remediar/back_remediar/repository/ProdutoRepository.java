package com.remediar.back_remediar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.remediar.back_remediar.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}