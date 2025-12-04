package com.remediar.back_remediar.model.dto.usuarios;

import jakarta.validation.constraints.NotNull;

public record FuncionarioIdRequestDTO(

        @NotNull(message = "O id do funcionário é obrigatório")
        Long funcionarioId
) {
}
