package com.remediar.notification_service.services;

import com.remediar.notification_service.dtos.NotificationRequestDto;
import com.remediar.notification_service.enuns.StatusNotification;
import com.remediar.notification_service.models.NotificationModel;
import com.remediar.notification_service.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EmailService {

    final NotificationRepository emailRepository;
    final JavaMailSender emailSender;

    public EmailService(NotificationRepository emailRepository, JavaMailSender emailSender) {
        this.emailRepository = emailRepository;
        this.emailSender = emailSender;
    }

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    @Transactional
    public NotificationModel sendEmailFromDto(NotificationRequestDto dto) {
        NotificationModel emailModel = new NotificationModel();
        emailModel.setUserId(dto.userId());
        emailModel.setEmailFrom(emailFrom);
        emailModel.setDestino(dto.destino());
        emailModel.setTitulo(dto.titulo());
        emailModel.setMensagem(dto.mensagem());
        emailModel.setTipoCanal(dto.tipoCanal().name());
        emailModel.setDataCriacao(LocalDateTime.now());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(dto.destino());
            message.setSubject(dto.titulo());
            message.setText(dto.mensagem());
            emailSender.send(message);

            emailModel.setStatusNotification(StatusNotification.SENT);
        } catch (MailException e) {
            emailModel.setStatusNotification(StatusNotification.ERROR);
        }

        return emailRepository.save(emailModel);
    }

}