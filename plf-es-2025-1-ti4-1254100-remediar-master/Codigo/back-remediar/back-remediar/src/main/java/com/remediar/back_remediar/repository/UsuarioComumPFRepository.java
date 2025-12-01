package com.remediar.back_remediar.repository;

import com.remediar.back_remediar.model.UsuarioComumPF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioComumPFRepository extends JpaRepository<UsuarioComumPF, Long> {
    boolean existsByCpf(String cpf);
}
