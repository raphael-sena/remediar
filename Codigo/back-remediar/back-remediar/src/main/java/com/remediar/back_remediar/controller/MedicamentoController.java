package com.remediar.back_remediar.controller;

import com.remediar.back_remediar.model.Medicamento;
import com.remediar.back_remediar.model.dto.MedicamentoDTO;
import com.remediar.back_remediar.model.mapper.MedicamentoMapper;
import com.remediar.back_remediar.service.MedicamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicamentos")
@Tag(name = "Medicamentos", description = "Operações relacionadas a medicamentos")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;
    private final MedicamentoMapper medicamentoMapper;

    public MedicamentoController(MedicamentoService medicamentoService, MedicamentoMapper medicamentoMapper) {
        this.medicamentoService = medicamentoService;
        this.medicamentoMapper = medicamentoMapper;
    }

    @Operation(summary = "Buscar medicamentos por princípio ativo ou nome comercial",
            description = "Retorna uma lista paginada de medicamentos que começam com o prefixo fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de medicamentos retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MedicamentoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum medicamento encontrado com o prefixo fornecido")
    })
    @GetMapping("/pesquisar/principioAtivoOrNomeComercial/{prefixo}")
    public ResponseEntity<Page<MedicamentoDTO>> findAllByPrincipioAtivoOrNomeComercialStartingWith(
            @PathVariable String prefixo,
            Pageable pageable) {
        Page<MedicamentoDTO> medicamentos = medicamentoService.findAllByPrincipioAtivoOrNomeComercialStartingWith(prefixo, pageable);
        return ResponseEntity.ok(medicamentos);
    }

    @Operation(summary = "Buscar medicamento por ID", description = "Retorna um medicamento com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamento encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MedicamentoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Medicamento não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoDTO> findById(@PathVariable Long id) {
        Medicamento medicamento = medicamentoService.findById(id);
        return ResponseEntity.ok(medicamentoMapper.toDTO(medicamento));
    }

    @Operation(summary = "Listar todos os medicamentos", description = "Retorna uma lista paginada de todos os medicamentos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de medicamentos retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<Page<MedicamentoDTO>> findAll(Pageable pageable) {
        Page<MedicamentoDTO> medicamentos = medicamentoService.findAll(pageable);
        return ResponseEntity.ok(medicamentos);
    }

    @Operation(summary = "Listar todos os medicamentos controlados", description = "Retorna uma lista paginada de todos os medicamentos controlados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de medicamentos controlados retornada com sucesso")
    })
    @GetMapping("/controlados")
    public ResponseEntity<Page<MedicamentoDTO>> findAllControlados(Pageable pageable) {
        Page<MedicamentoDTO> medicamentos = medicamentoService.findControlados(pageable);
        return ResponseEntity.ok(medicamentos);
    }

    @Operation(summary = "Criar um novo medicamento", description = "Cadastra um novo medicamento no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medicamento criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MedicamentoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<MedicamentoDTO> criar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do medicamento a ser criado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = MedicamentoDTO.class)))
            @RequestBody MedicamentoDTO obj) {

        MedicamentoDTO medicamento = medicamentoService.criar(obj);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(medicamento.id())
                .toUri();

        return ResponseEntity.created(uri).body(medicamento);
    }

    @Operation(summary = "Atualizar um medicamento existente", description = "Atualiza os dados de um medicamento com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamento atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MedicamentoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Medicamento não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoDTO> atualizar(
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados do medicamento",
                    required = true,
                    content = @Content(schema = @Schema(implementation = MedicamentoDTO.class)))
            @RequestBody MedicamentoDTO obj) {

        MedicamentoDTO medicamento = medicamentoService.atualizar(id, obj);
        return ResponseEntity.ok(medicamento);
    }

    @Operation(summary = "Excluir um medicamento", description = "Remove um medicamento com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Medicamento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Medicamento não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        medicamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar medicamento por código de barras", description = "Retorna um medicamento com base no código de barras fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamento encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MedicamentoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Medicamento não encontrado")
    })
    @GetMapping("/codigoBarras/{codigoBarras}")
    public ResponseEntity<MedicamentoDTO> findByCodigoBarras(@PathVariable String codigoBarras) {
        Medicamento medicamento = medicamentoService.findByCodigoBarras(codigoBarras);
        return ResponseEntity.ok(medicamentoMapper.toDTO(medicamento));
    }
}
