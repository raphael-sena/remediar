package com.remediar.pix_microservice.controller;

import com.remediar.pix_microservice.model.Donation;
import com.remediar.pix_microservice.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/donations")
public class DonationController {

    @Autowired
    private DonationService donationService;


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Donation>> getDonationsByUser(@PathVariable String userId) {
        return ResponseEntity.ok(donationService.getDonationsByUser(userId));
    }

    @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<List<Donation>> getDonationsByCampaign(@PathVariable String campaignId) {
        return ResponseEntity.ok(donationService.getDonationsByCampaign(campaignId));
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalDonations() {
        return ResponseEntity.ok(donationService.getTotalDonations());
    }

    @GetMapping("/top-donors")
    public ResponseEntity<List<Map<String, Object>>> getTopDonors() {
        return ResponseEntity.ok(donationService.getTopDonors());
    }

    @GetMapping("/campaign-totals")
    public ResponseEntity<List<Map<String, Object>>> getCampaignTotals() {
        return ResponseEntity.ok(donationService.getCampaignTotals());
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Donation>> getDonationsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(donationService.getDonationsByDateRange(startDate, endDate));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Donation>> getDonationsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(donationService.getDonationsByStatus(status));
    }
} 