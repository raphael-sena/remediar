package com.remediar.back_remediar.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.remediar.back_remediar.model.dto.ForgotPasswordDTO;
import com.remediar.back_remediar.model.dto.ResetPasswordDTO;
import com.remediar.back_remediar.service.PasswordResetService;

@RestController
@RequestMapping("/password")
@Tag(name = "Autenticação", description = "Operações relacionadas a autenticação")
public class ResetPasswordController {

    private final PasswordResetService passwordResetService;

    public ResetPasswordController( PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordDTO dto) {
        passwordResetService.enviarLinkResetSenha(dto.login());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordDTO dto) {
        passwordResetService.atualizarSenha(dto.token(), dto.novaSenha());
        return ResponseEntity.ok().build();
    }
}
