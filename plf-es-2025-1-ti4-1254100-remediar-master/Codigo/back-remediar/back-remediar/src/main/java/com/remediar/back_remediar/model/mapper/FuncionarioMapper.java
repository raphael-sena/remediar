package com.remediar.back_remediar.model.mapper;

import com.remediar.back_remediar.model.Funcionario;
import com.remediar.back_remediar.model.dto.usuarios.FuncionarioRequestDTO;
import com.remediar.back_remediar.model.dto.usuarios.FuncionarioResponseDTO;
import com.remediar.back_remediar.model.dto.usuarios.FuncionarioUpdateDTO;
import com.remediar.back_remediar.model.enums.Genero;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * Mapper responsável pela conversão entre entidades Funcionario e seus DTOs.
 * Utiliza MapStruct para automatizar a transformação de objetos.
 *
 * O componente é configurado como um Bean do Spring e utiliza outros mappers
 * (EnderecoMapper e UserMapper) para mapear relações complexas.
 */
@Mapper(componentModel = "spring", uses = {EnderecoMapper.class, UserMapper.class})
public interface FuncionarioMapper extends GenericMapper<FuncionarioRequestDTO, Funcionario> {

    /**
     * Converte um DTO de requisição em uma entidade Funcionario.
     * Usado principalmente em operações de criação (POST).
     *
     * @param dto O DTO contendo os dados para criar um novo funcionário
     * @return Uma nova instância de Funcionario com os dados do DTO
     */
    @Override
    @Mapping(target = "dataNascimento", source = "dataNascimento", qualifiedByName = "stringToLocalDate")
    @Mapping(target = "genero", source = "genero", qualifiedByName = "stringToGenero")
    @Mapping(target = "user", source = "usuario") // Mapeia o campo usuario do DTO para o campo user da entidade
    Funcionario toEntity(FuncionarioRequestDTO dto);

    /**
     * Converte uma entidade Funcionario em um DTO de requisição.
     * Útil para expor dados da entidade em um formato adequado para APIs.
     *
     * @param funcionario A entidade a ser convertida
     * @return Um DTO com os dados do funcionário
     */
    @Override
    @Mapping(target = "dataNascimento", source = "dataNascimento", qualifiedByName = "localDateToString")
    @Mapping(target = "genero", source = "genero", qualifiedByName = "generoToString")
    FuncionarioRequestDTO toDto(Funcionario funcionario);

    /**
     * Converte uma entidade Funcionario em um DTO específico para respostas.
     * Este método seleciona apenas os campos necessários para a resposta da API,
     * incluindo dados derivados como o email obtido do objeto User associado.
     *
     * @param funcionario A entidade a ser convertida em DTO de resposta
     * @return DTO contendo apenas os campos relevantes para a resposta
     */
    @Mapping(target = "email", expression = "java(funcionario.getUser() != null ? funcionario.getUser().getLogin() : null)")
    FuncionarioResponseDTO toResponseDto(Funcionario funcionario);

    /**
     * Atualiza uma entidade Funcionario existente com os dados de um DTO de atualização.
     * Utilizado em operações de atualização parcial (PATCH) ou completa (PUT).
     *
     * @param dto O DTO contendo os novos valores
     * @param funcionario A entidade existente que será atualizada
     */
    @Mapping(target = "dataNascimento", source = "dataNascimento", qualifiedByName = "stringToLocalDate")
    @Mapping(target = "genero", source = "genero", qualifiedByName = "stringToGenero")
    void updateEntityFromDto(FuncionarioUpdateDTO dto, @MappingTarget Funcionario funcionario);

    /**
     * Converte uma string no formato "dd/MM/yyyy" para um objeto LocalDate.
     * Método usado para conversão de datas recebidas da API para o formato de data do Java.
     *
     * @param date A data como string no formato "dd/MM/yyyy"
     * @return Um objeto LocalDate ou null se a string for nula ou vazia
     */
    @Named("stringToLocalDate")
    default LocalDate stringToLocalDate(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }

    /**
     * Converte um objeto LocalDate para sua representação em string.
     * Método usado para enviar datas para a API em formato padronizado.
     *
     * @param date O objeto LocalDate a ser convertido
     * @return A data como string ou null se o objeto for nulo
     */
    @Named("localDateToString")
    default String localDateToString(LocalDate date) {
        return date != null ? date.toString() : null;
    }

    /**
     * Converte uma string para o enum Genero.
     * Realiza uma conversão case-insensitive para o enum correspondente.
     *
     * @param genero O gênero como string
     * @return O enum Genero correspondente ou null se a string for nula
     */
    @Named("stringToGenero")
    default Genero stringToGenero(String genero) {
        return genero != null ? Genero.valueOf(genero.toUpperCase()) : null;
    }

    /**
     * Converte um enum Genero para sua representação em string.
     *
     * @param genero O enum Genero a ser convertido
     * @return O nome do enum como string ou null se o enum for nulo
     */
    @Named("generoToString")
    default String generoToString(Genero genero) {
        return genero != null ? genero.name() : null;
    }
}