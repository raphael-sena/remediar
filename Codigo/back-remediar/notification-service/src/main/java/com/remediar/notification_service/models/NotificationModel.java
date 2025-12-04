package com.remediar.notification_service.models;

import com.remediar.notification_service.enuns.StatusNotification;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_notification")
@Data

public class NotificationModel {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID notificationId;
    private Long userId;
    private String emailFrom;
    private String destino;
    private String titulo;
    @Column(columnDefinition = "TEXT")
    private String mensagem;
    private String tipoCanal;
    private StatusNotification statusNotification;
    private LocalDateTime dataCriacao;
}
