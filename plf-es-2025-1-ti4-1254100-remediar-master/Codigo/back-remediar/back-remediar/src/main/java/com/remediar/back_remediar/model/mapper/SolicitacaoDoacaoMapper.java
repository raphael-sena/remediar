package com.remediar.back_remediar.model.mapper;

import com.remediar.back_remediar.model.ItemSolicitacaoDoacao;
import com.remediar.back_remediar.model.SolicitacaoDoacao;
import com.remediar.back_remediar.model.dto.solicitacoes.DoacaoResponseDTO;
import com.remediar.back_remediar.model.dto.solicitacoes.ItemDoacaoDTO;
import com.remediar.back_remediar.model.dto.solicitacoes.ItemSolicitacaoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {SolicitacaoMapper.class})
public interface SolicitacaoDoacaoMapper {

    @Mapping(target = "solicitacao", source = ".", qualifiedByName = "toSolicitacaoResponseDTO")
    @Mapping(target = "itensDoacao", source = "itensDoacao", qualifiedByName = "toItemDoacaoDTOList")
    DoacaoResponseDTO toDoacaoResponseDTO(SolicitacaoDoacao solicitacaoDoacao);

    @Named("toItemDoacaoDTO")
    default ItemDoacaoDTO toItemDoacaoDTO(ItemSolicitacaoDoacao item) {
        return new ItemDoacaoDTO(
                new ItemSolicitacaoDTO(
                        item.getProduto().getNomeComercial(),
                        item.getQuantidade()
                ),
                item.getDataValidade(),
                item.getImagem()
        );
    }

    @Named("toItemDoacaoDTOList")
    default List<ItemDoacaoDTO> toItemDoacaoDTOList(List<ItemSolicitacaoDoacao> itens) {
        if (itens == null) return List.of();
        return itens.stream()
                .map(this::toItemDoacaoDTO)
                .collect(Collectors.toList());
    }
}





