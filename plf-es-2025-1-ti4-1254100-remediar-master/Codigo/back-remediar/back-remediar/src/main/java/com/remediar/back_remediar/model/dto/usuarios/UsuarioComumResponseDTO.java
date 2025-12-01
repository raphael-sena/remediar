package com.remediar.back_remediar.model.dto.usuarios;

public record UsuarioComumResponseDTO(
        Long id,
        String nome,
        String documento,
        String telefone
) {
}
