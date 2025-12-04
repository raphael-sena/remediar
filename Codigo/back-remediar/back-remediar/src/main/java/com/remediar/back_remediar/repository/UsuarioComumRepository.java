package com.remediar.back_remediar.repository;

import com.remediar.back_remediar.model.UsuarioComum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioComumRepository extends JpaRepository<UsuarioComum, Long> {
    Optional<UsuarioComum> findByUser_Login(String email);

    Optional<UsuarioComum> findByDocumento(String documento);

    Boolean existsByUser_Login(String email);

    Boolean existsByDocumento(String document);
}
