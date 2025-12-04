package com.remediar.back_remediar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario_comum_pj")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioComumPJ extends UsuarioComum {
    private String cnpj;

    public String getDocumento() {
        return this.cnpj;
    }

    public void setDocumento(String documento) {
        this.cnpj = documento;
    }
}
