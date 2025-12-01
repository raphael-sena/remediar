package com.remediar.pix_microservice.service;

import com.remediar.pix_microservice.model.Donation;
import com.remediar.pix_microservice.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Transactional
    public Donation saveDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    public List<Donation> getDonationsByUser(String userId) {
        return donationRepository.findByUserId(userId);
    }

    public List<Donation> getDonationsByCampaign(String campaignId) {
        return donationRepository.findByCampaignId(campaignId);
    }

    public BigDecimal getTotalDonations() {
        return donationRepository.getTotalDonations();
    }

    public List<Map<String, Object>> getTopDonors() {
        return donationRepository.getTopDonors().stream()
                .map(row -> Map.of(
                    "userId", row[0],
                    "userName", row[1],
                    "total", row[2]
                ))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getCampaignTotals() {
        return donationRepository.getCampaignTotals().stream()
                .map(row -> Map.of(
                    "campaignId", row[0],
                    "campaignName", row[1],
                    "total", row[2]
                ))
                .collect(Collectors.toList());
    }

    public List<Donation> getDonationsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return donationRepository.findByDonationDateBetween(startDate, endDate);
    }

    public List<Donation> getDonationsByStatus(String status) {
        return donationRepository.findByStatus(status);
    }
} 