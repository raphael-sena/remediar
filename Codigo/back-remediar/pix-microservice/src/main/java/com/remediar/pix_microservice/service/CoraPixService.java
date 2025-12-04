package com.remediar.pix_microservice.service;

import com.remediar.pix_microservice.dto.InvoiceRequest;
import com.remediar.pix_microservice.model.Donation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CoraPixService {

    private static final Logger logger = LoggerFactory.getLogger(CoraPixService.class);

    @Value("${cora.api.invoices-url}")
    private String invoicesUrl;

    @Autowired
    private DonationService donationService;

    private final RestTemplate mtlsRestTemplate;
    private final CoraTokenService tokenService;

    public CoraPixService(RestTemplate mtlsRestTemplate, CoraTokenService tokenService) {
        this.mtlsRestTemplate = mtlsRestTemplate;
        this.tokenService = tokenService;
    }

    public ResponseEntity<?> gerarQrCodePix(InvoiceRequest invoiceRequest) {
        logger.info("Iniciando geração de QR Code PIX");
        
        try {
            // 1. Obtem token JWT
            logger.info("Obtendo token de autenticação Cora");
            String token = tokenService.obterToken();
            
            // 2. Verifica se o token foi obtido com sucesso
            if (token == null || token.trim().isEmpty()) {
                logger.error("Token não foi obtido ou está vazio");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("erro", "Falha na autenticação - Token não obtido"));
            }
            
            // 3. Valida o formato do token
            if (!tokenService.isTokenValid(token)) {
                logger.error("Token obtido não possui formato válido");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("erro", "Falha na autenticação - Token inválido"));
            }
            
            logger.info("Token validado com sucesso, prosseguindo com a geração do QR Code");

            // 4. Monta headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(token);
            headers.set("Idempotency-Key", UUID.randomUUID().toString());

            logger.debug("Headers configurados - Content-Type: {}, Accept: {}, Idempotency-Key: {}", 
                        headers.getContentType(), headers.getAccept(), headers.get("Idempotency-Key"));

            // 5. Monta e envia requisição
            HttpEntity<InvoiceRequest> entity = new HttpEntity<>(invoiceRequest, headers);

            logger.info("Enviando requisição para gerar QR Code PIX: {}", invoicesUrl);
            ResponseEntity<Map> response = mtlsRestTemplate.exchange(
                    invoicesUrl,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            logger.info("Resposta recebida do servidor Cora - Status: {}", response.getStatusCode());

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("QR Code PIX gerado com sucesso");
                return ResponseEntity.ok(response.getBody());
            } else {
                logger.error("Erro na geração do QR Code PIX - Status: {}, Body: {}", 
                           response.getStatusCode(), response.getBody());
                return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
            }

        } catch (Exception ex) {
            logger.error("Exceção durante geração do QR Code PIX", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Falha ao gerar QR Code Pix", "detalhes", ex.getMessage()));
        }
    }

    @Transactional
    public ResponseEntity<?> confirmarPagamento(String transactionId, String userId, String userName, Double amount, String campaignId, String campaignName) {
        try {
            // Cria e salva a doação
            Donation donation = new Donation();
            donation.setUserId(userId);
            donation.setUserName(userName);
            donation.setAmount(java.math.BigDecimal.valueOf(amount));
            donation.setPixTransactionId(transactionId);
            donation.setStatus("COMPLETED");
            donation.setPaymentMethod("PIX");
            donation.setCampaignId(campaignId);
            donation.setCampaignName(campaignName);

            donationService.saveDonation(donation);
            
            return ResponseEntity.ok(Map.of("message", "Doação registrada com sucesso"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Falha ao registrar doação", "detalhes", ex.getMessage()));
        }
    }
}
