package com.remediar.back_remediar.service;

import com.remediar.back_remediar.model.Medicamento;
import com.remediar.back_remediar.model.dto.MedicamentoDTO;
import com.remediar.back_remediar.model.mapper.MedicamentoMapper;
import com.remediar.back_remediar.repository.MedicamentoRepository;
import com.remediar.back_remediar.service.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    private final MedicamentoMapper medicamentoMapper;

    public MedicamentoService(MedicamentoRepository medicamentoRepository, MedicamentoMapper medicamentoMapper) {
        this.medicamentoRepository = medicamentoRepository;
        this.medicamentoMapper = medicamentoMapper;
    }

    @Transactional(readOnly = true)
    public Medicamento findById(Long id) {
        return medicamentoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Medicamento não encontrado."));
    }

    @Transactional(readOnly = true)
    public Page<MedicamentoDTO> findControlados(Pageable pageable) {
        return medicamentoRepository.findAllControlados(pageable)
                .map(medicamentoMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<MedicamentoDTO> findAll(Pageable pageable) {
        return medicamentoRepository.findAll(pageable)
                .map(medicamentoMapper::toDTO);
    }

    @Transactional
    public MedicamentoDTO criar(MedicamentoDTO obj) {
        Medicamento medicamento =  medicamentoMapper.toEntity(obj);
        medicamentoRepository.save(medicamento);
        return medicamentoMapper.toDTO(medicamento);
    }

    @Transactional
    public MedicamentoDTO atualizar(Long id, MedicamentoDTO obj) {
        findById(id);
        Medicamento medicamento = medicamentoMapper.toEntity(obj);
        medicamentoRepository.save(medicamento);
        return medicamentoMapper.toDTO(medicamento);
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        medicamentoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<MedicamentoDTO> findAllByPrincipioAtivoOrNomeComercialStartingWith(String prefixo, Pageable pageable) {
        return medicamentoRepository.findAllByPrincipioAtivoStartingWithIgnoreCaseOrNomeComercialStartingWithIgnoreCase(prefixo, prefixo, pageable)
                .map(medicamentoMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Medicamento findByCodigoBarras(String codigoBarras) {
        return medicamentoRepository.findByCodigoBarras(codigoBarras)
                .orElseThrow(() -> new ObjectNotFoundException("Medicamento não encontrado."));
    }
}
