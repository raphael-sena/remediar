package com.remediar.notification_service.repositories;

import com.remediar.notification_service.models.NotificationHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationHistoryRepository extends JpaRepository<NotificationHistoryModel, UUID> {
}
