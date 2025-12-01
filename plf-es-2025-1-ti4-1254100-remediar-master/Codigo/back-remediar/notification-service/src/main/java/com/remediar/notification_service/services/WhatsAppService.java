package com.remediar.notification_service.services;

import com.remediar.notification_service.dtos.NotificationRequestDto;
import com.remediar.notification_service.models.NotificationModel;
import com.remediar.notification_service.enuns.StatusNotification;
import com.remediar.notification_service.repositories.NotificationRepository;
import com.remediar.notification_service.utils.PhoneNumberUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WhatsAppService {

    private final NotificationRepository notificationRepository;
    private final RestTemplate restTemplate;

    public WhatsAppService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
        this.restTemplate = new RestTemplate();
    }

    public NotificationModel enviarMensagemFromDto(NotificationRequestDto dto) {
        NotificationModel notificacao = new NotificationModel();
        notificacao.setUserId(dto.userId());
        notificacao.setDestino(dto.destino());
        notificacao.setMensagem(dto.mensagem());
        notificacao.setTitulo(dto.titulo());
        notificacao.setTipoCanal(dto.tipoCanal().name());
        notificacao.setDataCriacao(LocalDateTime.now());

        try {
            String numeroFormatado = PhoneNumberUtils.formatToE164(dto.destino()); // ex: +553199999999
            System.out.println("📞 Enviando mensagem para: " + numeroFormatado);
            Map<String, String> body = new HashMap<>();
            body.put("numero", numeroFormatado.replace("+", "")); // Venom espera sem "+"
            body.put("mensagem", dto.mensagem());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity("http://host.docker.internal:3030/enviar", request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                notificacao.setStatusNotification(StatusNotification.SENT);
                System.out.println("✅ Mensagem enviada para " + numeroFormatado);
            } else {
                notificacao.setStatusNotification(StatusNotification.ERROR);
                System.err.println("❌ Falha ao enviar: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            notificacao.setStatusNotification(StatusNotification.ERROR);
        }

        return notificationRepository.save(notificacao);
    }
}
