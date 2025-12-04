package com.remediar.back_remediar.repository;

import com.remediar.back_remediar.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository  extends JpaRepository<Funcionario, Long> {
    boolean existsByCpf(String cpf);
}
