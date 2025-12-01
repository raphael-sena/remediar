package com.remediar.back_remediar.model.dto.usuarios;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
}
