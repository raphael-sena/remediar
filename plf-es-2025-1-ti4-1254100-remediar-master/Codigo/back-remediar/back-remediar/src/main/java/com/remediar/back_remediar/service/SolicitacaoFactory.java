package com.remediar.back_remediar.service;

import com.remediar.back_remediar.model.*;
import com.remediar.back_remediar.model.enums.Status;
import com.remediar.back_remediar.model.enums.TipoSolicitacao;

import java.time.LocalDateTime;

public class SolicitacaoFactory {

    public static SolicitacaoDoacao criarSolicitacaoDoacao(UsuarioComum usuario) {
        SolicitacaoDoacao solicitacao = new SolicitacaoDoacao();
        preencherSolicitacaoBase(solicitacao, usuario, TipoSolicitacao.DOACAO);
        return solicitacao;
    }

    public static SolicitacaoPedido criarSolicitacaoPedido(UsuarioComum usuario) {
        SolicitacaoPedido solicitacao = new SolicitacaoPedido();
        preencherSolicitacaoBase(solicitacao, usuario, TipoSolicitacao.PEDIDO);
        return solicitacao;
    }

    private static void preencherSolicitacaoBase(Solicitacao solicitacao, UsuarioComum usuario, TipoSolicitacao tipo) {
        solicitacao.setUsuarioComum(usuario);
        solicitacao.setTipoSolicitacao(tipo);
        solicitacao.setStatusAtual(Status.PENDENTE);
        solicitacao.setDataHoraCriacao(LocalDateTime.now());
        solicitacao.setDataHoraUltimaAtualizacao(LocalDateTime.now());
    }
}

