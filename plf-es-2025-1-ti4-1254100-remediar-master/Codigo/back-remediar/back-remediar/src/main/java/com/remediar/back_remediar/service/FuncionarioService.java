package com.remediar.back_remediar.service;

import com.remediar.back_remediar.model.Funcionario;
import com.remediar.back_remediar.model.UserRole;
import com.remediar.back_remediar.model.dto.usuarios.FuncionarioResponseDTO;
import com.remediar.back_remediar.model.dto.usuarios.FuncionarioUpdateDTO;
import com.remediar.back_remediar.model.mapper.FuncionarioMapper;
import com.remediar.back_remediar.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final AuthorizationService authorizationService;
    private final FuncionarioMapper funcionarioMapper;

    public FuncionarioService(FuncionarioRepository funcionarioRepository,
                              AuthorizationService authorizationService,
                              FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.authorizationService = authorizationService;
        this.funcionarioMapper = funcionarioMapper;
    }

    public FuncionarioResponseDTO create(Funcionario funcionario) {
        if (authorizationService.checkLoginExits(funcionario.getUser().getLogin())) {
            throw new IllegalArgumentException("Já existe um cadastro com esse e-mail.");
        }
        if (funcionarioRepository.existsByCpf(funcionario.getCpf())) {
            throw new IllegalArgumentException("Já existe um cadastro com esse cpf.");
        }
        funcionario.getUser().setPassword(authorizationService.encryptPassword(funcionario.getUser().getPassword()));
        funcionario.getUser().setRole(UserRole.FUNCIONARIO);
        Funcionario saved = funcionarioRepository.save(funcionario);
        return funcionarioMapper.toResponseDto(saved);
    }

    public Funcionario getById(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
    }

    public List<FuncionarioResponseDTO> getAll() {
        return funcionarioRepository.findAll()
                .stream()
                .map(funcionarioMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public FuncionarioResponseDTO update(Long id, FuncionarioUpdateDTO dto) {
        Funcionario funcionarioExistente = getById(id);
        funcionarioMapper.updateEntityFromDto(dto, funcionarioExistente);
        Funcionario updated = funcionarioRepository.save(funcionarioExistente);
        return funcionarioMapper.toResponseDto(updated);
    }

    public void delete(Long id) {
        getById(id);
        funcionarioRepository.deleteById(id);
    }

    public boolean existsByCpf(String number) {
        return funcionarioRepository.existsByCpf(number);
    }
}
