package com.remediar.back_remediar.repository;

import com.remediar.back_remediar.model.Medicamento;
import com.remediar.back_remediar.model.MedicamentoControlado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    @Query("SELECT m FROM Medicamento m WHERE TYPE(m) = MedicamentoControlado")
    Page<MedicamentoControlado> findAllControlados(Pageable pageable);

    List<Medicamento> findByNomeComercialIgnoreCaseOrPrincipioAtivoIgnoreCase(String nomeComercial, String principioAtivo);

    Page<Medicamento> findAllByPrincipioAtivoStartingWithIgnoreCaseOrNomeComercialStartingWithIgnoreCase(String principioAtivo, String nomeComercial, Pageable pageable);

    Optional<Medicamento> findByCodigoBarras(String codigoBarras);
}
