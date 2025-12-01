package com.remediar.back_remediar.repository;

import com.remediar.back_remediar.model.PasswordResetToken;
import com.remediar.back_remediar.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    PasswordResetToken findByUser(User user);
}