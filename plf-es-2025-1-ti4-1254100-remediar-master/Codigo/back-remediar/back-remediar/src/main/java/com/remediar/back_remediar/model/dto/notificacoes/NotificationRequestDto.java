package com.remediar.back_remediar.model.dto.notificacoes;

import java.io.Serializable;
import java.util.UUID;

public class NotificationRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String emailFrom;
    private String destino;
    private String titulo;
    private String mensagem;
    private TipoCanal tipoCanal;
    private UUID solicitacaoId;      // Novo - pode ser nulo
    private String tipoSolicitacao;  // Novo - pode ser nulo

    // Construtor principal
    public NotificationRequestDto(Long userId, String emailFrom, String destino, String titulo, String mensagem, TipoCanal tipoCanal, UUID solicitacaoId, String tipoSolicitacao) {
        this.userId = userId;
        this.emailFrom = emailFrom;
        this.destino = destino;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.tipoCanal = tipoCanal;
        this.solicitacaoId = solicitacaoId;
        this.tipoSolicitacao = tipoSolicitacao;
    }

    // Construtor simplificado, caso não queira passar os campos novos
    public NotificationRequestDto(Long userId, String emailFrom, String destino, String titulo, String mensagem, TipoCanal tipoCanal) {
        this(userId, emailFrom, destino, titulo, mensagem, tipoCanal, null, null);
    }

    // Getters e Setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public TipoCanal getTipoCanal() {
        return tipoCanal;
    }

    public void setTipoCanal(TipoCanal tipoCanal) {
        this.tipoCanal = tipoCanal;
    }

    public UUID getSolicitacaoId() {
        return solicitacaoId;
    }

    public void setSolicitacaoId(UUID solicitacaoId) {
        this.solicitacaoId = solicitacaoId;
    }

    public String getTipoSolicitacao() {
        return tipoSolicitacao;
    }

    public void setTipoSolicitacao(String tipoSolicitacao) {
        this.tipoSolicitacao = tipoSolicitacao;
    }
}
