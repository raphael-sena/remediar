package com.remediar.back_remediar.repository;

import com.remediar.back_remediar.model.PrescricaoMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescricaoMedicaRepository extends JpaRepository<PrescricaoMedica, Long> {
}
