package com.remediar.back_remediar.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.remediar.back_remediar.model.dto.notificacoes.NotificationRequestDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.notification.name}")
    private String queue;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotification(NotificationRequestDto dto) {
        try {
            // Log do JSON que será enviado para o RabbitMQ
            String json = new ObjectMapper().writeValueAsString(dto);
            System.out.println("🔔 Enviando mensagem para fila (" + queue + "): " + json);
        } catch (Exception e) {
            System.out.println("Erro ao serializar o DTO para JSON: " + e.getMessage());
        }

        rabbitTemplate.convertAndSend(queue, dto);
    }
}

