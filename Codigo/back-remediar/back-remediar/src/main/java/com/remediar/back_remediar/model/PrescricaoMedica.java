package com.remediar.back_remediar.model;

import com.remediar.back_remediar.model.enums.Genero;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prescricao_medica")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PrescricaoMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "prescricaoMedica", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SolicitacaoPedido> solicitacoes = new ArrayList<>();

    private LocalDate dataEmissao;
    private boolean dispensada;

    @Column(name = "imagem_receita", columnDefinition = "TEXT")
    private String imageUrl;
    
    private boolean usoContinuo;

    private String nomeMedico;
    private String crmMedico;

    private String nomePaciente;
    private Integer idadePaciente;
    private Genero generoPaciente;
    private String cpfPaciente;
    private String contato;
}
