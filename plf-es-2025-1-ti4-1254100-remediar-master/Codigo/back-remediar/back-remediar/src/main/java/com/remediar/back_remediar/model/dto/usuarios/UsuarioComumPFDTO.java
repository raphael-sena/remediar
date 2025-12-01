package com.remediar.back_remediar.model.dto.usuarios;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.remediar.back_remediar.model.enums.Escolaridade;
import com.remediar.back_remediar.model.enums.Genero;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record UsuarioComumPFDTO(

        @Valid
        UsuarioComumDTO usuario,

        @NotNull(message = "O gênero é obrigatório")
        Genero genero,

        @NotNull(message = "A data de nascimento é obrigatória")
        @JsonFormat(pattern = "dd/MM/yyyy")
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        LocalDate dataNascimento,

        @NotNull(message = "A escolaridade é obrigatória")
        Escolaridade escolaridade,

        @NotNull(message = "A quantidade de pessoas na casa é obrigatória")
        Integer qtdPessoasCasa,

        @NotNull(message = "A renda familiar é obrigatória")
        Double rendaFamiliar
) {
}
