package com.remediar.pix_microservice.dto;

public class Fine {
    public Integer amount; // Multa em centavos (prioridade)
    public Double rate;    // Percentual (apenas se amount == null)
}
