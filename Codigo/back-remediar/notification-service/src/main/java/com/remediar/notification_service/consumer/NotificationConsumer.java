package com.remediar.notification_service.consumer;

import com.remediar.notification_service.dtos.NotificationRequestDto;
import com.remediar.notification_service.dtos.TipoCanal;
import com.remediar.notification_service.models.NotificationHistoryModel;
import com.remediar.notification_service.models.NotificationModel;
import com.remediar.notification_service.models.SolicitacaoNotificationModel;
import com.remediar.notification_service.models.StatusNotification;
import com.remediar.notification_service.repositories.NotificationHistoryRepository;
import com.remediar.notification_service.repositories.SolicitacaoNotificationRepository;
import com.remediar.notification_service.services.EmailService;
import com.remediar.notification_service.services.WhatsAppService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificationConsumer {

    @Autowired
    private EmailService emailService;

    @Autowired
    private WhatsAppService whatsAppService;

    @Autowired
    private SolicitacaoNotificationRepository solicitacaoNotificationRepository;

    @Autowired
    private NotificationHistoryRepository notificationHistoryRepository;

    @RabbitListener(queues = "${broker.queue.notification.name}")
    public void listenNotificationQueue(@Payload NotificationRequestDto dto) {
        System.out.println("🔔 Recebendo mensagem da fila: " + dto);

        // Enviar notificação (email ou WhatsApp)
        boolean enviadaComSucesso = false;
        try {
            if (dto.tipoCanal() == TipoCanal.EMAIL) {
                emailService.sendEmailFromDto(dto);
            } else if (dto.tipoCanal() == TipoCanal.WHATSAPP) {
                whatsAppService.enviarMensagemFromDto(dto);
            }
            enviadaComSucesso = true;
        } catch (Exception e) {
            System.err.println("❌ Falha ao enviar notificação: " + e.getMessage());
        }

        // Se veio com solicitação associada, registrar o histórico
        if (dto.solicitacaoId() != null && dto.tipoSolicitacao() != null) {
            SolicitacaoNotificationModel solicitacao = solicitacaoNotificationRepository
                    .findBySolicitacaoId(dto.solicitacaoId())
                    .orElseGet(() -> {
                        SolicitacaoNotificationModel nova = new SolicitacaoNotificationModel();
                        nova.setSolicitacaoId(dto.solicitacaoId());
                        nova.setTipoSolicitacao(dto.tipoSolicitacao());
                        return solicitacaoNotificationRepository.save(nova);
                    });

            NotificationHistoryModel historico = new NotificationHistoryModel();
            historico.setDataHora(LocalDateTime.now());
            historico.setStatus(enviadaComSucesso ? StatusNotification.ENVIADA : StatusNotification.FALHA);
            historico.setCanal(dto.tipoCanal().name());
            historico.setDestino(dto.destino());
            historico.setMensagem(dto.mensagem());
            historico.setSolicitacaoNotification(solicitacao);

            notificationHistoryRepository.save(historico);
        }
    }
}
