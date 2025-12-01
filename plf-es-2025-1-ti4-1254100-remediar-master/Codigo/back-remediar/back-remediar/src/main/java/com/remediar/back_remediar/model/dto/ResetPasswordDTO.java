package com.remediar.back_remediar.model.dto;

public record ResetPasswordDTO(
    String novaSenha,
    String token
) {
}
