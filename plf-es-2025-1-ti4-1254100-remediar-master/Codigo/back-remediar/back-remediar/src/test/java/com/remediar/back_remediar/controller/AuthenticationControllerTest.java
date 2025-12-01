package com.remediar.back_remediar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.remediar.back_remediar.model.User;
import com.remediar.back_remediar.model.UserRole;
import com.remediar.back_remediar.model.dto.usuarios.AuthenticationDTO;
import com.remediar.back_remediar.model.dto.usuarios.LoginResponseDTO;
import com.remediar.back_remediar.model.dto.usuarios.RegisterDTO;
import com.remediar.back_remediar.repository.UserRepository;
import com.remediar.back_remediar.service.AuthorizationService;
import com.remediar.back_remediar.infra.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthorizationService authService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthenticationController authenticationController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void login_WithValidCredentials_ShouldReturnToken() throws Exception {
        // Arrange
        AuthenticationDTO authDTO = new AuthenticationDTO("test@email.com", "password123");
        User user = new User("test@email.com", "encryptedPassword", UserRole.USER);
        String expectedToken = "jwt.token.here";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(tokenService.generateToken(user)).thenReturn(expectedToken);

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(expectedToken));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService).generateToken(user);
    }

    @Test
    void login_WithInvalidCredentials_ShouldReturnUnauthorized() throws Exception {
        // Arrange
        AuthenticationDTO authDTO = new AuthenticationDTO("invalid@email.com", "wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authDTO)))
                .andExpect(status().isUnauthorized());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void register_WithValidData_ShouldCreateUser() throws Exception {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO("newuser@email.com", "password123", UserRole.USER);

        when(authService.checkLoginExits(anyString())).thenReturn(false);
        when(authService.encryptPassword(anyString())).thenReturn("encryptedPassword");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Act & Assert
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isOk());

        verify(authService).checkLoginExits(registerDTO.login());
        verify(authService).encryptPassword(registerDTO.password());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_WithExistingLogin_ShouldReturnBadRequest() throws Exception {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO("existing@email.com", "password123", UserRole.USER);

        when(authService.checkLoginExits(anyString())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isBadRequest());

        verify(authService).checkLoginExits(registerDTO.login());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void register_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO("", "", UserRole.USER);

        // Act & Assert
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isBadRequest());

        verify(authService, never()).checkLoginExits(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
} 