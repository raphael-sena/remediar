package com.remediar.back_remediar.model.dto.solicitacoes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.remediar.back_remediar.model.enums.Genero;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record PrescricaoMedicaDTO(

        Long id,


        @JsonFormat(pattern = "dd/MM/yyyy")
        @JsonProperty("dataEmissao")
        LocalDate dataEmissao,

        @NotNull(message = "Campo dispensada não pode ser nulo")
        boolean dispensada,

        @NotEmpty(message = "Campo imageUrl não pode ser nulo")
        String imageUrl,

        @NotNull(message = "Campo usoContinuo não pode ser nulo")
        Boolean usoContinuo,


        @NotEmpty(message = "Campo nomeMedico não pode ser nulo")
        String nomeMedico,

        @NotEmpty(message = "Campo crmMedico não pode ser nulo")
        String crmMedico,


        @NotEmpty(message = "Campo nomePaciente não pode ser nulo")
        String nomePaciente,

        @NotNull(message = "Campo idadePaciente não pode ser nulo")
        Integer idadePaciente,

        @NotNull(message = "Campo generoPaciente não pode ser nulo")
        Genero generoPaciente,

        @NotEmpty(message = "Campo cpfPaciente não pode ser nulo")
        @CPF(message = "CPF inválido")
        String cpfPaciente,

        @NotEmpty(message = "Campo contato não pode ser nulo")
        String contato
) {
}
