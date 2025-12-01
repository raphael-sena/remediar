package com.remediar.back_remediar.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.remediar.back_remediar.model.dto.dashboards.MedicamentosMaisDoadosDTO;
import com.remediar.back_remediar.model.dto.dashboards.TotalMedicamentosDoadosDTO;
import com.remediar.back_remediar.model.dto.dashboards.RemediosVencidosDTO;
import com.remediar.back_remediar.model.dto.dashboards.SolicitacoesNaoAtendidasDTO;
import com.remediar.back_remediar.model.dto.dashboards.FaixaEtariaSolicitacoesDTO;

import com.remediar.back_remediar.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService){
        this.dashboardService = dashboardService;
    }

    @GetMapping("/medicamentos-mais-doados")
    public ResponseEntity<List<MedicamentosMaisDoadosDTO>> getMaisDoacoes(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {

        List<MedicamentosMaisDoadosDTO> resultado = dashboardService.getTop5MedicamentosMaisDoados(inicio, fim);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/total-medicamentos-doados")
    public ResponseEntity<TotalMedicamentosDoadosDTO> getTotalMedicamentosDoados(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {

        TotalMedicamentosDoadosDTO resultado = dashboardService.getTotalMedicamentosDoados(inicio, fim);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/total-medicamentos-vencidos")
    public ResponseEntity<RemediosVencidosDTO> getTotalMedicamentosVencidos(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {

        RemediosVencidosDTO resultado = dashboardService.getQuantidadeRemediosVencidos(inicio, fim);
        return ResponseEntity.ok(resultado);
    }
    
    @GetMapping("/total-solicitacoes-nao-atendidas")
    public ResponseEntity<SolicitacoesNaoAtendidasDTO> getTotalSolicitacoesNaoAtendidas(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {

        SolicitacoesNaoAtendidasDTO resultado = dashboardService.getSolicitacoesNaoAtendidas(inicio, fim);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/solicitacoes-por-faixa-etaria")
    public ResponseEntity<List<FaixaEtariaSolicitacoesDTO>> getFaixaEtariaSolicitacoes(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {

        List<FaixaEtariaSolicitacoesDTO> resultado = dashboardService.getFaixaEtariaSolicitacoes(inicio, fim);
        return ResponseEntity.ok(resultado);
    }

    
}
