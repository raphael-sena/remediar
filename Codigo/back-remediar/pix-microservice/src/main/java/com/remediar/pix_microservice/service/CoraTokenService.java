package com.remediar.pix_microservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CoraTokenService {

    private static final Logger logger = LoggerFactory.getLogger(CoraTokenService.class);

    @Value("${cora.mtls.client-id}")
    private String clientId;

    @Value("${cora.mtls.token-url}")
    private String authUrl;

    private final RestTemplate mtlsRestTemplate;

    public CoraTokenService(RestTemplate mtlsRestTemplate) {
        this.mtlsRestTemplate = mtlsRestTemplate;
    }

    public String obterToken() {
        logger.info("Iniciando processo de obtenção do token Cora");
        
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            String body = "grant_type=client_credentials" +
                    "&client_id=" + clientId;

            HttpEntity<String> request = new HttpEntity<>(body, headers);

            logger.debug("Enviando requisição para obter token: {}", authUrl);

            ResponseEntity<Map> response = mtlsRestTemplate.exchange(
                    authUrl,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            logger.info("Resposta recebida do servidor Cora - Status: {}", response.getStatusCode());

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                String accessToken = (String) response.getBody().get("access_token");
                
                if (accessToken != null && !accessToken.trim().isEmpty()) {
                    // Verifica se o token tem a estrutura básica de um JWT (3 partes separadas por ponto)
                    if (isValidJwtToken(accessToken)) {
                        logger.info("Token Cora gerado com sucesso - Token válido");
                        logger.debug("Token obtido: {}", maskToken(accessToken));
                        return accessToken;
                    } else {
                        logger.error("Token recebido não possui formato JWT válido");
                        throw new RuntimeException("Token recebido não possui formato JWT válido");
                    }
                } else {
                    logger.error("Token de acesso não encontrado na resposta");
                    throw new RuntimeException("Token de acesso não encontrado na resposta");
                }
            } else {
                logger.error("Erro na resposta do servidor Cora - Status: {}, Body: {}", 
                           response.getStatusCode(), response.getBody());
                throw new RuntimeException("Erro ao obter token Cora: " + response.getStatusCode() + 
                                         " - " + response.getBody());
            }
            
        } catch (Exception ex) {
            logger.error("Exceção durante obtenção do token Cora", ex);
            throw new RuntimeException("Falha na obtenção do token Cora: " + ex.getMessage(), ex);
        }
    }

    /**
     * Verifica se o token possui a estrutura básica de um JWT
     * @param token Token a ser validado
     * @return true se o token tem formato JWT válido
     */
    private boolean isValidJwtToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            return false;
        }
        
        // JWT deve ter 3 partes separadas por ponto
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            logger.warn("Token não possui formato JWT válido (3 partes esperadas, encontradas: {})", parts.length);
            return false;
        }
        
        // Verifica se cada parte não está vazia
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].trim().isEmpty()) {
                logger.warn("Parte {} do token JWT está vazia", i + 1);
                return false;
            }
        }
        
        return true;
    }

    /**
     * Mascara o token para logs de segurança
     * @param token Token original
     * @return Token mascarado
     */
    private String maskToken(String token) {
        if (token == null || token.length() < 10) {
            return "***";
        }
        return token.substring(0, 10) + "..." + token.substring(token.length() - 10);
    }

    /**
     * Verifica se o token atual ainda é válido (opcional - pode ser implementado com validação de expiração)
     * @param token Token a ser verificado
     * @return true se o token parece válido
     */
    public boolean isTokenValid(String token) {
        return isValidJwtToken(token);
    }
}
