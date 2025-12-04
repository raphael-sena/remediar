package com.remediar.back_remediar.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.remediar.back_remediar.model.User;
import com.remediar.back_remediar.model.UserRole;
import com.remediar.back_remediar.model.dto.usuarios.AuthenticationDTO;
import com.remediar.back_remediar.model.dto.usuarios.RegisterDTO;
import com.remediar.back_remediar.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
class AuthenticationIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        userRepository.deleteAll();
    }

    @Test
    void register_WithValidData_ShouldCreateUser() throws Exception {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO(
                "newuser@email.com",
                "password123",
                UserRole.USER
        );

        // Act & Assert
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isOk());

        // Verify user was created in database
        User savedUser = (User) userRepository.findByLogin("newuser@email.com");
        assert savedUser != null;
        assert savedUser.getLogin().equals("newuser@email.com");
        assert savedUser.getRole() == UserRole.USER;
        assert passwordEncoder.matches("password123", savedUser.getPassword());
    }

    @Test
    void register_WithExistingEmail_ShouldReturnBadRequest() throws Exception {
        // Arrange
        User existingUser = new User("existing@email.com", "encodedPassword", UserRole.USER);
        userRepository.save(existingUser);

        RegisterDTO registerDTO = new RegisterDTO(
                "existing@email.com",
                "password123",
                UserRole.USER
        );

        // Act & Assert
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void register_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO(
                "", // Invalid email
                "", // Invalid password
                UserRole.USER
        );

        // Act & Assert
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_WithValidCredentials_ShouldReturnToken() throws Exception {
        // Arrange
        String encodedPassword = passwordEncoder.encode("password123");
        User user = new User("test@email.com", encodedPassword, UserRole.USER);
        userRepository.save(user);

        AuthenticationDTO authDTO = new AuthenticationDTO("test@email.com", "password123");

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void login_WithInvalidCredentials_ShouldReturnUnauthorized() throws Exception {
        // Arrange
        String encodedPassword = passwordEncoder.encode("password123");
        User user = new User("test@email.com", encodedPassword, UserRole.USER);
        userRepository.save(user);

        AuthenticationDTO authDTO = new AuthenticationDTO("test@email.com", "wrongpassword");

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void login_WithNonExistentUser_ShouldReturnUnauthorized() throws Exception {
        // Arrange
        AuthenticationDTO authDTO = new AuthenticationDTO("nonexistent@email.com", "password123");

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void login_WithInvalidJson_ShouldReturnBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("invalid json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void register_WithInvalidJson_ShouldReturnBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("invalid json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void register_WithDifferentUserRoles_ShouldCreateUserWithCorrectRole() throws Exception {
        // Test USER role
        RegisterDTO userDTO = new RegisterDTO("user@email.com", "password123", UserRole.USER);
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());

        User savedUser = (User) userRepository.findByLogin("user@email.com");
        assert savedUser != null;
        assert savedUser.getRole() == UserRole.USER;

        // Test FUNCIONARIO role
        RegisterDTO funcionarioDTO = new RegisterDTO("funcionario@email.com", "password123", UserRole.FUNCIONARIO);
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funcionarioDTO)))
                .andExpect(status().isOk());

        User savedFuncionario = (User) userRepository.findByLogin("funcionario@email.com");
        assert savedFuncionario != null;
        assert savedFuncionario.getRole() == UserRole.FUNCIONARIO;

        // Test ADMIN role
        RegisterDTO adminDTO = new RegisterDTO("admin@email.com", "password123", UserRole.ADMIN);
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminDTO)))
                .andExpect(status().isOk());

        User savedAdmin = (User) userRepository.findByLogin("admin@email.com");
        assert savedAdmin != null;
        assert savedAdmin.getRole() == UserRole.ADMIN;
    }
} 