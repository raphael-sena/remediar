package com.remediar.back_remediar.infra.config;

import com.remediar.back_remediar.model.dto.EnderecoDTO;
import com.remediar.back_remediar.model.dto.EstoqueCreateDTO;
import com.remediar.back_remediar.repository.EstoqueRepository;
import com.remediar.back_remediar.service.EstoqueService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class EstoqueInitializer {

    private final EstoqueService estoqueService;

    private final EstoqueRepository estoqueRepository;

    public EstoqueInitializer(EstoqueService estoqueService, EstoqueRepository estoqueRepository) {
        this.estoqueService = estoqueService;
        this.estoqueRepository = estoqueRepository;
    }

    @PostConstruct
    public void init() {
        String[][] estoques = {
                {"Lourdes", "Avenida Bias Fortes", "431", "Edifício", "Lourdes", "Belo Horizonte", "MG", "30170-011"},
                {"União", "Rua Edson", "312", "Edifício", "União", "Belo Horizonte", "MG", "31170-620"}
        };

        for (String[] dadosEstoque : estoques) {
            String nomeEstoque = dadosEstoque[0];

            if (!estoqueRepository.existsByNomeIgnoreCase(nomeEstoque)) {
                EstoqueCreateDTO dto = getEstoqueCreateDTO(dadosEstoque, nomeEstoque);

                estoqueService.criar(dto);
                System.out.println("[INFO] Estoque '" + nomeEstoque + "' criado com sucesso.");
            } else {
                System.out.println("[INFO] Estoque '" + nomeEstoque + "' já existe. Nenhuma ação foi realizada.");
            }
        }
    }

    private EstoqueCreateDTO getEstoqueCreateDTO(String[] dadosEstoque, String nomeEstoque) {
        EnderecoDTO endereco = new EnderecoDTO(
                dadosEstoque[1], // rua
                dadosEstoque[2], // numero
                dadosEstoque[3], // complemento
                dadosEstoque[4], // bairro
                dadosEstoque[5], // cidade
                dadosEstoque[6], // estado
                dadosEstoque[7]  // cep
        );

        return new EstoqueCreateDTO(nomeEstoque, endereco);
    }


}
