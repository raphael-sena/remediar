package com.remediar.back_remediar.model;

import com.remediar.back_remediar.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String observacao;

    @ManyToOne
    @JoinColumn(name = "solicitacao_id")
    private Solicitacao solicitacao;
}
