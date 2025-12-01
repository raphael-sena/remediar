package com.remediar.back_remediar.model.dto.usuarios;

import com.remediar.back_remediar.model.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
    
}
