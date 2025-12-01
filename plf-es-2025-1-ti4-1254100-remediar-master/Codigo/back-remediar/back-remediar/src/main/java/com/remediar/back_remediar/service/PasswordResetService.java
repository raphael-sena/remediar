package com.remediar.back_remediar.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.remediar.back_remediar.model.PasswordResetToken;
import com.remediar.back_remediar.model.User;
import com.remediar.back_remediar.repository.PasswordResetTokenRepository;
import com.remediar.back_remediar.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthorizationService authService;

    @Value("${app.link.reset.password}")
	private String linkResetPassword;

    public void enviarLinkResetSenha(String email) {
        if(!authService.checkLoginExits(email)) {
            throw new IllegalArgumentException("E-mail não encontrado. Verifique se o e-mail está correto.");
        }

        User user = (User) userRepository.findByLogin(email);

        String token = UUID.randomUUID().toString();

        PasswordResetToken existingToken = tokenRepository.findByUser(user);

        if (existingToken != null) {
            tokenRepository.delete(existingToken);
        }

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));

        tokenRepository.save(resetToken);

        String link = linkResetPassword + "/esqueceu-senha/nova-senha?token=" + token;
        emailService.sendEmail(user.getLogin(), "Recuperação de senha", "Olá, " + getNameUser(user) + "\n\nClique aqui para redefinir sua senha: " + link);
    }

    public void redefinirSenha(String token, String novaSenha) {
        PasswordResetToken tokenEntity = tokenRepository.findByToken(token)
            .orElseThrow(() -> new RuntimeException("Token inválido ou expirado"));

        User user = tokenEntity.getUser();
        user.setPassword(new BCryptPasswordEncoder().encode(novaSenha));
        userRepository.save(user);

        tokenRepository.delete(tokenEntity); 
    }

    private PasswordResetToken validarToken(String token) {
        return tokenRepository.findByToken(token)
            .filter(t -> t.getExpiryDate().isAfter(LocalDateTime.now()))
            .orElseThrow(() -> new RuntimeException("Token inválido ou expirado"));
    }

    @Transactional
    public void atualizarSenha(String token, String novaSenha) {
        PasswordResetToken tokenEntity = validarToken(token);
        User user = tokenEntity.getUser();

        String novaSenhaCriptografada = "";

        if(user.getPassword().equals(novaSenha)){
            throw new IllegalArgumentException("As senhas não podem ser iguais.");
        }

        novaSenhaCriptografada = authService.encryptPassword(novaSenha);

        user.setPassword(novaSenhaCriptografada);
        userRepository.save(user);
        tokenRepository.delete(tokenEntity);
    }

    private String getNameUser(User user){
        if(user.getUsuarioComum()!= null){
            return user.getUsuarioComum().getNome();
        }

        return user.getFuncionario().getNome();
    }
}
