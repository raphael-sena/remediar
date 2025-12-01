package com.remediar.back_remediar.infra.config;

import com.remediar.back_remediar.model.Endereco;
import com.remediar.back_remediar.model.Funcionario;
import com.remediar.back_remediar.model.User;
import com.remediar.back_remediar.model.UserRole;
import com.remediar.back_remediar.model.enums.Genero;
import com.remediar.back_remediar.service.FuncionarioService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class FuncionarioInitializer {

    private final FuncionarioService funcionarioService;

    public FuncionarioInitializer(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostConstruct
    public void init() {
        String cpfTeste = "37613021009";

        boolean jaExiste = funcionarioService
                .existsByCpf(cpfTeste);

        if (!jaExiste) {
            Funcionario funcionario = new Funcionario();
            funcionario.setNome("Teste Remediar");
            funcionario.setCpf(cpfTeste);
            funcionario.setTelefone("31987654321");
            funcionario.setGenero(Genero.FEMININO);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            funcionario.setDataNascimento(LocalDate.parse("09/07/2002", formatter));

            Endereco endereco = new Endereco();
            endereco.setRua("Rua X");
            endereco.setNumero("123");
            endereco.setComplemento("Casa");
            endereco.setBairro("XPTO");
            endereco.setCidade("Belo Horizonte");
            endereco.setEstado("MG");
            endereco.setCep("31340-400");
            funcionario.setEndereco(endereco);

            User user = new User();
            user.setLogin("teste@remediar.com");
            user.setPassword("1234");
            user.setRole(UserRole.FUNCIONARIO);
            funcionario.setUser(user);

            funcionarioService.create(funcionario);
            System.out.println("[INFO] Funcionário de teste criado com sucesso.");
        } else {
            System.out.println("[INFO] Funcionário de teste já existe. Nenhuma ação foi realizada.");
        }
    }
}
