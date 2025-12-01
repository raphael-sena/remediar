package com.remediar.back_remediar.model.dto.usuarios;

import com.remediar.back_remediar.model.dto.EnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record FuncionarioRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100, message = "O nome não pode ter mais que 100 caracteres")
        String nome,

        @NotBlank(message = "O CPF é obrigatório")
        @CPF
        String cpf,

        @NotBlank(message = "O telefone é obrigatório")
        @Size(max = 15, message = "O telefone não pode ter mais que 15 caracteres")
        String telefone,

        @NotBlank(message = "O gênero é obrigatório")
        String genero,

        @NotBlank(message = "A data de nascimento é obrigatória")
        @Pattern(regexp = "^(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/(19|20)\\d{2}$",
                message = "A data de nascimento deve estar no formato dd/MM/yyyy")
        String dataNascimento,

        @Valid
        EnderecoDTO endereco,

        @Valid
        UserDTO usuario
) {
}
