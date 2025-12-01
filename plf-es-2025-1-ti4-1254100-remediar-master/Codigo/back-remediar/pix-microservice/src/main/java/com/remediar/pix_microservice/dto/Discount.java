package com.remediar.pix_microservice.dto;

public class Discount {
    public String type;  // "PERCENT" ou "FIXED"
    public double value; // Se PERCENT, é percentual (1.5). Se FIXED, em centavos.
}
