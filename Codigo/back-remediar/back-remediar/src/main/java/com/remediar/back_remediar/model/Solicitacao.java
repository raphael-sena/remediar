package com.remediar.back_remediar.model;

import com.remediar.back_remediar.model.enums.Status;
import com.remediar.back_remediar.model.enums.TipoSolicitacao;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioComum usuarioComum;

    @ManyToOne
    @JoinColumn(name = "funcionario_atual_id")
    @Nullable
    private Funcionario funcionarioResponsavelAtual;

    @Enumerated
    private Status statusAtual;

    @Enumerated
    private TipoSolicitacao tipoSolicitacao;

    private LocalDateTime dataHoraCriacao;

    private LocalDateTime dataHoraUltimaAtualizacao;

    private LocalDateTime dataHoraFinalizacao;

    @OneToMany(mappedBy = "solicitacao", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Historico> historicos = new ArrayList<>();

//    private List<SolicitacaoObserver> solicitacaoObservers = new ArrayList<>();
}
