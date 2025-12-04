package com.remediar.notification_service.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_notification_history")
public class NotificationHistoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private StatusNotification status;

    private String canal; // "email", "whatsapp", etc.

    private String destino;

    private String mensagem;

    @ManyToOne
    @JoinColumn(name = "solicitacao_notification_id")
    private SolicitacaoNotificationModel solicitacaoNotification;
}
