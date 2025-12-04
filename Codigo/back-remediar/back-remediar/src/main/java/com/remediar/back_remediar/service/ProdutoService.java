package com.remediar.back_remediar.service;

import com.remediar.back_remediar.model.Medicamento;
import com.remediar.back_remediar.model.Produto;
import com.remediar.back_remediar.repository.MedicamentoRepository;
import com.remediar.back_remediar.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final MedicamentoRepository medicamentoRepository;

    public ProdutoService(ProdutoRepository produtoRepository, MedicamentoRepository medicamentoRepository) {
        this.produtoRepository = produtoRepository;
        this.medicamentoRepository = medicamentoRepository;
    }

    @Transactional(readOnly = true)
    public Produto findById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Medicamento> findMedicamentosByDescricao(String descricao) {
        List<Medicamento> medicamentos = medicamentoRepository
                .findByNomeComercialIgnoreCaseOrPrincipioAtivoIgnoreCase(descricao, descricao);

        if (medicamentos.isEmpty()) {
            throw new RuntimeException("Nenhum medicamento encontrado com essa descrição");
        }

        return medicamentos;
    }
}



