package com.remediar.back_remediar.model.mapper;

import com.remediar.back_remediar.model.ItemSolicitacaoDoacao;
import com.remediar.back_remediar.model.SolicitacaoDoacao;
import com.remediar.back_remediar.model.UsuarioComum;
import com.remediar.back_remediar.model.dto.solicitacoes.DoacaoResponseDTO;
import com.remediar.back_remediar.model.dto.solicitacoes.ItemDoacaoDTO;
import com.remediar.back_remediar.model.dto.solicitacoes.SolicitacaoResponseDTO;
import com.remediar.back_remediar.model.dto.usuarios.UsuarioComumDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UsuarioComumMapper {

    @Mapping(target = "id", source = "usuarioComum.id")
    UsuarioComumDTO toUsuarioResponseDTO(UsuarioComum usuarioComum);
}

