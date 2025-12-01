package com.remediar.back_remediar.model.mapper;

import com.remediar.back_remediar.model.ItemSolicitacaoPedido;
import com.remediar.back_remediar.model.SolicitacaoPedido;
import com.remediar.back_remediar.model.dto.solicitacoes.ItemSolicitacaoDTO;
import com.remediar.back_remediar.model.dto.solicitacoes.PedidoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {SolicitacaoMapper.class})
public interface SolicitacaoPedidoMapper {

    @Mapping(target = "solicitacao", source = ".", qualifiedByName = "toSolicitacaoResponseDTO")
    @Mapping(target = "item", source = "itemSolicitacaoPedido", qualifiedByName = "toItemSolicitacaoDTO")
    @Mapping(target = "prescricaoMedica", source = "prescricaoMedica", qualifiedByName = "toPrescricaoMedicaDTO")
    PedidoResponseDTO toPedidoResponseDTO(SolicitacaoPedido solicitacaoPedido);

    @Named("toItemSolicitacaoDTO")
    default ItemSolicitacaoDTO toItemSolicitacaoDTO(ItemSolicitacaoPedido item) {
        return new ItemSolicitacaoDTO(
                item.getProduto().getNomeComercial(),
                item.getQuantidade()
        );
    }
}
