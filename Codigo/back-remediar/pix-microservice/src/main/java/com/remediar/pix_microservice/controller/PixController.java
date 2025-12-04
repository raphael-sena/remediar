package com.remediar.pix_microservice.controller;

import com.remediar.pix_microservice.dto.InvoiceRequest;
import com.remediar.pix_microservice.service.CoraPixService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pix")
public class PixController {

    private final CoraPixService coraPixService;

    public PixController(CoraPixService coraPixService) {
        this.coraPixService = coraPixService;
    }

    @PostMapping("/gerar")
    public ResponseEntity<?> gerarQr(@RequestBody InvoiceRequest request) {
        return coraPixService.gerarQrCodePix(request);
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmarPagamento(
            @RequestParam String transactionId,
            @RequestParam String userId,
            @RequestParam String userName,
            @RequestParam Double amount,
            @RequestParam(required = false) String campaignId,
            @RequestParam(required = false) String campaignName) {
        
        return coraPixService.confirmarPagamento(
            transactionId,
            userId,
            userName,
            amount,
            campaignId,
            campaignName
        );
    }
} 