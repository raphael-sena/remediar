package com.remediar.back_remediar.model.dto.solicitacoes;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record ItemSolicitacaoDTO(

        @NotEmpty(message = "O nome do produto ou princípio ativo não pode ser nulo")
        String nomeComercialOrPrincipioAtivo,

        @Min(value = 1, message = "A quantidade deve ser maior que 0")
        Short quantidade
) {
}
