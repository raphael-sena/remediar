package com.remediar.notification_service.repositories;

import com.remediar.notification_service.models.NotificationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<NotificationModel, UUID> {
}
