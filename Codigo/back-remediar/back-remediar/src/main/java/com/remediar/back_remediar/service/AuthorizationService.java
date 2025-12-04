package com.remediar.back_remediar.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.remediar.back_remediar.repository.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService{

    private final UserRepository userRepository;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }

    public boolean checkLoginExits(String login){
        return userRepository.findByLogin(login) != null;
    }

    public String encryptPassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }
}
