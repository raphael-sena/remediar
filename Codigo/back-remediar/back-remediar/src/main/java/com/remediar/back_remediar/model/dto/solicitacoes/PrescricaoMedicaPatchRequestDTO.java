package com.remediar.back_remediar.model.dto.solicitacoes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.remediar.back_remediar.model.enums.Genero;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record PrescricaoMedicaPatchRequestDTO(

        @JsonFormat(pattern = "dd/MM/yyyy")
        @JsonProperty("dataEmissao")
        LocalDate dataEmissao,

        Boolean dispensada,

        String imageUrl,

        Boolean usoContinuo,


        String nomeMedico,

        String crmMedico,


        String nomePaciente,

        Integer idadePaciente,

        Genero generoPaciente,

        @CPF(message = "CPF inválido")
        String cpfPaciente,

        String contato
) {
}
