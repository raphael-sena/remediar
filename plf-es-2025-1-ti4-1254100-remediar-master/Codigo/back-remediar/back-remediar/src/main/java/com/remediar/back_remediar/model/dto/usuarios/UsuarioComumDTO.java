package com.remediar.back_remediar.model.dto.usuarios;

import com.remediar.back_remediar.model.dto.EnderecoDTO;
import com.remediar.back_remediar.validation.CPFouCNPJ;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UsuarioComumDTO(

        Long id,

        @NotEmpty(message = "O nome é obrigatório")
        @Size(max = 100, message = "O nome não pode ter mais que 100 caracteres")
        String nome,

        @CPFouCNPJ
        String documento,

        @NotBlank(message = "O telefone é obrigatório")
        @Size(max = 15, message = "O telefone não pode ter mais que 15 caracteres")
        String telefone,

        @Valid
        EnderecoDTO endereco,

        @Valid
        UserDTO user
) {
}
