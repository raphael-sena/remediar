package com.remediar.notification_service.repositories;

import com.remediar.notification_service.models.SolicitacaoNotificationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SolicitacaoNotificationRepository extends JpaRepository<SolicitacaoNotificationModel, UUID> {
    Optional<SolicitacaoNotificationModel> findBySolicitacaoId(UUID solicitacaoId);
}
