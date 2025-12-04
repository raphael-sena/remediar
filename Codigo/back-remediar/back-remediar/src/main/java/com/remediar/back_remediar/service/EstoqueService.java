package com.remediar.back_remediar.service;

import com.remediar.back_remediar.service.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.remediar.back_remediar.model.Estoque;
import com.remediar.back_remediar.model.ItemEstoque;
import com.remediar.back_remediar.model.dto.EnderecoDTO;
import com.remediar.back_remediar.model.dto.EstoqueCreateDTO;
import com.remediar.back_remediar.model.dto.EstoqueDTO;
import com.remediar.back_remediar.model.dto.EstoqueTransferDTO;
import com.remediar.back_remediar.model.dto.EstoqueUpdateDTO;
import com.remediar.back_remediar.model.dto.ItemEstoqueDTO;
import com.remediar.back_remediar.model.dto.ItemEstoqueUpdateDTO;
import com.remediar.back_remediar.model.mapper.EnderecoMapper;
import com.remediar.back_remediar.model.mapper.EstoqueMapper;
import com.remediar.back_remediar.repository.EnderecoRepository;
import com.remediar.back_remediar.repository.EstoqueRepository;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;

    private final EstoqueMapper estoqueMapper;

    private final EnderecoService enderecoService;

    private final EnderecoMapper enderecoMapper;

    public EstoqueService(EstoqueRepository estoqueRepository, EstoqueMapper estoqueMapper, EnderecoService enderecoService, EnderecoMapper enderecoMapper){
        this.estoqueRepository = estoqueRepository;
        this.estoqueMapper = estoqueMapper;
        this.enderecoService = enderecoService;
        this.enderecoMapper = enderecoMapper;
    }
    

    @Transactional(readOnly = true)
    public Estoque findById(Long id) {
        return estoqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado."));
    }

    @Transactional(readOnly = true)
    public Page<EstoqueDTO> findAll(Pageable pageable) {
        return estoqueRepository.findAll(pageable)
                .map(estoqueMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Estoque findByName(String nome) {
        return estoqueRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado."));
    }

    @Transactional(readOnly = true)
    public EstoqueDTO findByIdDTO(Long id) {
            Estoque estoque = estoqueRepository.findByIdWithItens(id)
              .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
        return estoqueMapper.toDTO(estoque);
    }

    @Transactional
    public EstoqueDTO criar(EstoqueCreateDTO obj) {
        if(checkEstoqueExits(obj.nome()))
            throw new IllegalArgumentException("Já existe um estoque com esse nome.");
            
        Estoque estoque =  new Estoque();

        EnderecoDTO enderecoDTO = enderecoService.save(obj.endereco());
        estoque.setEndereco(enderecoService.fromDto(enderecoDTO));
        estoque.setNome(obj.nome());

        estoqueRepository.save(estoque);
        return estoqueMapper.toDTO(estoque);
    }

    @Transactional
    public EstoqueDTO atualizar(Long id, EstoqueUpdateDTO dto) {
    Estoque estoque = estoqueRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));

    estoque.setNome(dto.nome());
    estoque.setEndereco(enderecoMapper.toEntity(dto.endereco()));

    estoqueRepository.save(estoque);
    return estoqueMapper.toDTO(estoque);
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        estoqueRepository.deleteById(id);
    }

    public boolean checkEstoqueExits(String nome){
        return estoqueRepository.findByNome(nome).isPresent();
    }

    @Transactional(readOnly = true)
    public EstoqueDTO findByItemEstoque(Long id) {
        return estoqueMapper
                .toDTO(estoqueRepository
                        .findByItens_Id(id).orElseThrow(
                                () -> new ObjectNotFoundException("Estoque não encontrado com este itemEstoqueId")));
    }
}
