package com.remediar.pix_microservice.dto;


import lombok.Data;

@Data
public class ServiceItem {
    public String name;  // Ex: "Doação"
    public int amount;   // Em centavos, ex: 1500 para R$ 15,00
}
