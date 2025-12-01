package com.remediar.back_remediar.repository;

import com.remediar.back_remediar.model.UsuarioComumPJ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioComumPJRepository extends JpaRepository<UsuarioComumPJ, Long> {
    boolean existsByCnpj(String cnpj);
}
