package com.remediar.pix_microservice.dto;

public class PaymentTerms {
    public String due_date; // Formato: yyyy-MM-dd
    public Fine fine;
    public Discount discount;
    public Interest interest;
}
