package com.remediar.pix_microservice.dto;

import java.util.List;

public class InvoiceRequest {
    public String code;
    public Customer customer;
    public List<ServiceItem> services;
    public PaymentTerms payment_terms;
    public Notification notification;
    public List<String> payment_forms;
}
