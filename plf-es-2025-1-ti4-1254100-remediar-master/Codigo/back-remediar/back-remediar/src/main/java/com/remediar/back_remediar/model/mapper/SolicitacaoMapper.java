package com.remediar.back_remediar.model.mapper;

import com.remediar.back_remediar.model.*;
import com.remediar.back_remediar.model.dto.solicitacoes.HistoricoResponseDTO;
import com.remediar.back_remediar.model.dto.solicitacoes.PrescricaoMedicaPatchRequestDTO;
import com.remediar.back_remediar.model.dto.solicitacoes.SolicitacaoResponseDTO;
import com.remediar.back_remediar.model.dto.usuarios.FuncionarioResponseDTO;
import com.remediar.back_remediar.model.dto.usuarios.UsuarioComumResponseDTO;
import com.remediar.back_remediar.model.dto.solicitacoes.PrescricaoMedicaDTO;
import com.remediar.back_remediar.model.enums.Status;
import com.remediar.back_remediar.model.enums.TipoSolicitacao;
import org.mapstruct.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface SolicitacaoMapper {

    @Named("toSolicitacaoResponseDTO")
    default SolicitacaoResponseDTO toSolicitacaoResponseDTO(SolicitacaoDoacao d) {
        return getSolicitacaoResponseDTO(
                d.getId(),
                d.getUsuarioComum(),
                d.getFuncionarioResponsavelAtual() != null ? d.getFuncionarioResponsavelAtual().getId() : null,
                d.getStatusAtual(),
                d.getTipoSolicitacao(),
                d.getDataHoraCriacao(),
                d.getDataHoraUltimaAtualizacao(),
                d.getDataHoraFinalizacao(),
                d.getHistoricos().stream()
                        .map(h -> new HistoricoResponseDTO(
                                h.getId(),
                                d.getId(),
                                h.getDataHora(),
                                h.getFuncionario() != null ? new FuncionarioResponseDTO(
                                        h.getFuncionario().getId(),
                                        h.getFuncionario().getNome(),
                                        h.getFuncionario().getUser().getLogin(),
                                        h.getFuncionario().getCpf(),
                                        h.getFuncionario().getTelefone()
                                ) : null,
                                h.getStatus(),
                                h.getObservacao()
                        )).toList()
        );
    }

    @Named("toSolicitacaoResponseDTO")
    default SolicitacaoResponseDTO toSolicitacaoResponseDTO(SolicitacaoPedido p) {
        return getSolicitacaoResponseDTO(
                p.getId(),
                p.getUsuarioComum(),
                p.getFuncionarioResponsavelAtual() != null ? p.getFuncionarioResponsavelAtual().getId() : null,
                p.getStatusAtual(),
                p.getTipoSolicitacao(),
                p.getDataHoraCriacao(),
                p.getDataHoraUltimaAtualizacao(),
                p.getDataHoraFinalizacao(),
                p.getHistoricos().stream()
                        .map(h -> new HistoricoResponseDTO(
                                h.getId(),
                                p.getId(),
                                h.getDataHora(),
                                h.getFuncionario() != null ? new FuncionarioResponseDTO(
                                        h.getFuncionario().getId(),
                                        h.getFuncionario().getNome(),
                                        h.getFuncionario().getUser().getLogin(),
                                        h.getFuncionario().getCpf(),
                                        h.getFuncionario().getTelefone()
                                ) : null,
                                h.getStatus(),
                                h.getObservacao()
                        )).toList()
        );
    }


    private SolicitacaoResponseDTO getSolicitacaoResponseDTO(
            UUID id,
            UsuarioComum usuarioComum,
            Long funcionarioResponsavelAtual,
            Status statusAtual,
            TipoSolicitacao tipoSolicitacao,
            LocalDateTime dataHoraCriacao,
            LocalDateTime dataHoraUltimaAtualizacao,
            LocalDateTime dataHoraFinalizacao,
            List<HistoricoResponseDTO> historico
    ) {
        return new SolicitacaoResponseDTO(
                id,
                usuarioComum != null
                        ? new UsuarioComumResponseDTO(
                        usuarioComum.getId(),
                        usuarioComum.getNome(),
                        usuarioComum.getDocumento(),
                        usuarioComum.getTelefone()
                )
                        : null,
                funcionarioResponsavelAtual,
                statusAtual,
                tipoSolicitacao,
                dataHoraCriacao,
                dataHoraUltimaAtualizacao,
                dataHoraFinalizacao,
                historico
        );
    }

    @Named("toPrescricaoMedicaDTO")
    default PrescricaoMedicaDTO toPrescricaoMedicaDTO(PrescricaoMedica p) {
        return new PrescricaoMedicaDTO(
                p.getId(),
                p.getDataEmissao(),
                p.isDispensada(),
                p.getImageUrl(),
                p.isUsoContinuo(),
                p.getNomeMedico(),
                p.getCrmMedico(),
                p.getNomePaciente(),
                p.getIdadePaciente(),
                p.getGeneroPaciente(),
                p.getCpfPaciente(),
                p.getContato()
        );
    }

    @Mapping(target = "solicitacoes", ignore = true)
    PrescricaoMedica toPrescricaoMedica(PrescricaoMedicaDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePrescricaoFromPatchDTO(PrescricaoMedicaPatchRequestDTO dto, @MappingTarget PrescricaoMedica prescricao);
}