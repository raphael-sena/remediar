package com.remediar.notification_service.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_solicitacao_notification")
public class SolicitacaoNotificationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID solicitacaoId; // vindo do microserviço principal

    private String tipoSolicitacao; // "DOACAO" ou "PEDIDO"

    @OneToMany(mappedBy = "solicitacaoNotification", cascade = CascadeType.ALL)
    private List<NotificationHistoryModel> historicos;
}
