package com.remediar.back_remediar.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.remediar.back_remediar.model.dto.dashboards.MedicamentosMaisDoadosDTO;
import com.remediar.back_remediar.model.dto.dashboards.TotalMedicamentosDoadosDTO;
import com.remediar.back_remediar.model.dto.dashboards.RemediosVencidosDTO;
import com.remediar.back_remediar.model.dto.dashboards.SolicitacoesNaoAtendidasDTO;
import com.remediar.back_remediar.repository.dashboards.MedicamentosMaisDoadosRepository;
import com.remediar.back_remediar.repository.dashboards.QuantidadeRemediosVencidosRepository;
import com.remediar.back_remediar.repository.dashboards.SolicitacoesNaoAtendidasRepository;
import com.remediar.back_remediar.repository.dashboards.TotalMedicamentosDoadosRepository;
import com.remediar.back_remediar.repository.dashboards.FaixaEtariaSolicitacoesRepository;
import com.remediar.back_remediar.model.dto.dashboards.FaixaEtariaSolicitacoesDTO;



@Service
public class DashboardService {

    private final MedicamentosMaisDoadosRepository medicamentosMaisDoadosRepository;
    private final QuantidadeRemediosVencidosRepository quantidadeRemediosVencidosRepository;
    private final SolicitacoesNaoAtendidasRepository solicitacoesNaoAtendidasRepository;
    private final TotalMedicamentosDoadosRepository totalMedicamentosDoadosRepository;
    private final FaixaEtariaSolicitacoesRepository faixaEtariaSolicitacoesRepository;


    public DashboardService(MedicamentosMaisDoadosRepository medicamentosMaisDoadosRepository, QuantidadeRemediosVencidosRepository quantidadeRemediosVencidosRepository, SolicitacoesNaoAtendidasRepository solicitacoesNaoAtendidasRepository, TotalMedicamentosDoadosRepository totalMedicamentosDoadosRepository, FaixaEtariaSolicitacoesRepository faixaEtariaSolicitacoesRepository) {
        this.faixaEtariaSolicitacoesRepository = faixaEtariaSolicitacoesRepository;
        this.medicamentosMaisDoadosRepository = medicamentosMaisDoadosRepository;
        this.quantidadeRemediosVencidosRepository = quantidadeRemediosVencidosRepository;
        this.solicitacoesNaoAtendidasRepository = solicitacoesNaoAtendidasRepository;
        this.totalMedicamentosDoadosRepository = totalMedicamentosDoadosRepository;
    }

    @Transactional(readOnly = true)
    public List<MedicamentosMaisDoadosDTO> getTop5MedicamentosMaisDoados(LocalDateTime dataInicio, LocalDateTime dataFim) {
        if(dataInicio == null || dataFim == null){
            dataFim = LocalDateTime.now();
            dataInicio = dataFim.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
        return medicamentosMaisDoadosRepository.findTop5MaisDoados(dataInicio, dataFim);
    }

    @Transactional(readOnly = true)
    public TotalMedicamentosDoadosDTO getTotalMedicamentosDoados(LocalDateTime dataInicio, LocalDateTime dataFim) {
        if(dataInicio == null || dataFim == null){
            dataFim = LocalDateTime.now();
            dataInicio = dataFim.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
        return totalMedicamentosDoadosRepository.findTotalDoado(dataInicio, dataFim);
    }

    @Transactional(readOnly = true)
    public SolicitacoesNaoAtendidasDTO getSolicitacoesNaoAtendidas(LocalDateTime dataInicio, LocalDateTime dataFim) {
        if(dataInicio == null || dataFim == null){
            dataFim = LocalDateTime.now();
            dataInicio = dataFim.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
        return solicitacoesNaoAtendidasRepository.findSolicitacoesNaoAtendidas(dataInicio, dataFim);
    }

    @Transactional(readOnly = true)
    public RemediosVencidosDTO getQuantidadeRemediosVencidos(LocalDateTime dataInicio, LocalDateTime dataFim) {
        if(dataInicio == null || dataFim == null){
            dataFim = LocalDateTime.now();
            dataInicio = dataFim.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
        return quantidadeRemediosVencidosRepository.findRemediosVencidos(dataInicio, dataFim);
    }
    
    
    @Transactional(readOnly = true)
    public List<FaixaEtariaSolicitacoesDTO> getFaixaEtariaSolicitacoes(LocalDateTime dataInicio, LocalDateTime dataFim) {
        if(dataInicio == null || dataFim == null){
            dataFim = LocalDateTime.now();
            dataInicio = dataFim.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
        return faixaEtariaSolicitacoesRepository.findFaixaEtariaSolicitacoes(dataInicio, dataFim);
    }

}
