package com.remediar.back_remediar.model;

import com.remediar.back_remediar.model.enums.Escolaridade;
import com.remediar.back_remediar.model.enums.Genero;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "usuario_comum_pf")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioComumPF extends UsuarioComum {
    private String cpf;
    private Genero genero;
    private LocalDate dataNascimento;
    private Escolaridade escolaridade;
    private Integer qtdPessoasCasa;
    private Double rendaFamiliar;

    public String getDocumento() {
        return this.cpf;
    }

    public void setDocumento(String documento) {
        this.cpf = documento;
    }

}
