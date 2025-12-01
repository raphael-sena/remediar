package com.remediar.back_remediar.model.dto.usuarios;

import com.remediar.back_remediar.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String  login;
    private String  password;
    private UserRole role;
}
