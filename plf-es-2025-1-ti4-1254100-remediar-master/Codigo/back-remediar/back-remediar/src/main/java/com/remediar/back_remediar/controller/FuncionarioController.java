package com.remediar.back_remediar.controller;

import com.remediar.back_remediar.model.Funcionario;
import com.remediar.back_remediar.model.dto.usuarios.FuncionarioRequestDTO;
import com.remediar.back_remediar.model.dto.usuarios.FuncionarioResponseDTO;
import com.remediar.back_remediar.model.dto.usuarios.FuncionarioUpdateDTO;
import com.remediar.back_remediar.model.mapper.FuncionarioMapper;
import com.remediar.back_remediar.service.FuncionarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/funcionarios")
@Tag(name = "Funcinários", description = "Operações relacionadas a funcionários")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;
    private final FuncionarioMapper funcionarioMapper;

    public FuncionarioController(FuncionarioService funcionarioService, FuncionarioMapper funcionarioMapper) {
        this.funcionarioService = funcionarioService;
        this.funcionarioMapper = funcionarioMapper;
    }

    @PostMapping
    public ResponseEntity<FuncionarioResponseDTO> create(@Valid @RequestBody FuncionarioRequestDTO dto) {
        // Converte o DTO para a entidade usando MapStruct
        Funcionario funcionario = funcionarioMapper.toEntity(dto);
        // Cria o funcionário usando a service que retorna o DTO de resposta
        FuncionarioResponseDTO response = funcionarioService.create(funcionario);
        // Considerando que o funcionário salvo possui o id, constrói a URI
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(funcionario.getId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> getById(@PathVariable Long id) {
        Funcionario response = funcionarioService.getById(id);
        FuncionarioResponseDTO funcionarioResponseDTO = funcionarioMapper.toResponseDto(response);
        return ResponseEntity.ok(funcionarioResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDTO>> getAll() {
        List<FuncionarioResponseDTO> responseList = funcionarioService.getAll();
        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody FuncionarioUpdateDTO dto) {
        // Utiliza o DTO de atualização para modificar a entidade existente
        FuncionarioResponseDTO response = funcionarioService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        funcionarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
