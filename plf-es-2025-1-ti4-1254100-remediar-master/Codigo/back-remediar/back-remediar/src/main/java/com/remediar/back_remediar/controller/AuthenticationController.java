package com.remediar.back_remediar.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.remediar.back_remediar.infra.security.TokenService;
import com.remediar.back_remediar.model.dto.usuarios.AuthenticationDTO;
import com.remediar.back_remediar.model.dto.usuarios.LoginResponseDTO;
import com.remediar.back_remediar.model.dto.usuarios.RegisterDTO;
import com.remediar.back_remediar.model.User;
import com.remediar.back_remediar.repository.UserRepository;
import com.remediar.back_remediar.service.AuthorizationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Operações relacionadas a autenticação")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final TokenService tokenService;

    private final AuthorizationService authService;

    public AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService, AuthorizationService authService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        
        var token = tokenService.generateToken((User)auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
        
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data){
        if(authService.checkLoginExits(data.login())) 
            return ResponseEntity.badRequest().build();

        String passEncrypted = authService.encryptPassword(data.password());

        User newUser = new User (data.login(), passEncrypted, data.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();

    }
    
}
