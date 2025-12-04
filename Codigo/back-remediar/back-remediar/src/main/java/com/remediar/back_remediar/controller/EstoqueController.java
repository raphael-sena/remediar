package com.remediar.back_remediar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.remediar.back_remediar.model.Estoque;
import com.remediar.back_remediar.model.dto.EstoqueCreateDTO;
import com.remediar.back_remediar.model.dto.EstoqueDTO;
import com.remediar.back_remediar.model.dto.EstoqueUpdateDTO;
import com.remediar.back_remediar.model.mapper.EstoqueMapper;
import com.remediar.back_remediar.service.EstoqueService;

@RestController
@RequestMapping("/estoque")
@Tag(name = "Estoque", description = "Operações relacionadas ao estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;
    private final EstoqueMapper estoqueMapper;

    public EstoqueController(EstoqueService estoqueService, EstoqueMapper estoqueMapper) {
        this.estoqueService = estoqueService;
        this.estoqueMapper = estoqueMapper;
    }

    @Operation(summary = "Buscar estoque por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estoque encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EstoqueDTO.class))),
            @ApiResponse(responseCode = "404", description = "Estoque não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EstoqueDTO> findById(@PathVariable Long id) {
        EstoqueDTO estoque = estoqueService.findByIdDTO(id);
        return ResponseEntity.ok(estoque);
    }

    @Operation(summary = "Listar todos os estoques")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<EstoqueDTO>> findAll(Pageable pageable) {
        Page<EstoqueDTO> estoques = estoqueService.findAll(pageable);
        return ResponseEntity.ok(estoques);
    }

    @Operation(summary = "Excluir estoque por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estoque removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estoque não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        estoqueService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Atualizar um estoque existente", description = "Atualiza os dados de um estoque com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EstoqueUpdateDTO.class))),
            @ApiResponse(responseCode = "404", description = "Estoque não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EstoqueDTO> atualizar(
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados do estoque",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EstoqueUpdateDTO.class)))
            @RequestBody EstoqueUpdateDTO obj) {

                EstoqueDTO estoque = estoqueService.atualizar(id, obj);
        return ResponseEntity.ok(estoque);
    }

    @Operation(summary = "Criar um novo estoque", description = "Cadastra um novo estoque no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estoque criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EstoqueCreateDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping()
    public ResponseEntity<EstoqueDTO> criar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do item de estoque a ser criado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EstoqueDTO.class)))
            @RequestBody EstoqueCreateDTO obj) {

                EstoqueDTO estoque = estoqueService.criar(obj);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(estoque.id())
                .toUri();

        return ResponseEntity.created(uri).body(estoque);
    }

    @GetMapping("/findByItemEstoque/{id}")
    public ResponseEntity<EstoqueDTO> findByItemEstoque(@PathVariable Long id) {
        EstoqueDTO estoqueDTO = estoqueService.findByItemEstoque(id);
        return ResponseEntity.ok(estoqueDTO);
    }
}
