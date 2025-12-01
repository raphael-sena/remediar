package com.remediar.pix_microservice.repository;

import com.remediar.pix_microservice.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    
    List<Donation> findByUserId(String userId);
    
    List<Donation> findByCampaignId(String campaignId);
    
    List<Donation> findByStatus(String status);
    
    List<Donation> findByDonationDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT SUM(d.amount) FROM Donation d WHERE d.status = 'COMPLETED'")
    BigDecimal getTotalDonations();
    
    @Query("SELECT d.userId, d.userName, SUM(d.amount) as total " +
           "FROM Donation d " +
           "WHERE d.status = 'COMPLETED' " +
           "GROUP BY d.userId, d.userName " +
           "ORDER BY total DESC")
    List<Object[]> getTopDonors();
    
    @Query("SELECT d.campaignId, d.campaignName, SUM(d.amount) as total " +
           "FROM Donation d " +
           "WHERE d.status = 'COMPLETED' " +
           "GROUP BY d.campaignId, d.campaignName " +
           "ORDER BY total DESC")
    List<Object[]> getCampaignTotals();
} 