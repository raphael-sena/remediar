package com.remediar.pix_microservice.dto;

import java.util.List;

public class NotificationChannel {
    public String channel; // "EMAIL" ou "SMS"
    public String contact; // ex: email ou número +55...
    public List<String> rules; // Regras ex: ["NOTIFY_WHEN_PAID", "NOTIFY_ON_DUE_DATE"]
}
