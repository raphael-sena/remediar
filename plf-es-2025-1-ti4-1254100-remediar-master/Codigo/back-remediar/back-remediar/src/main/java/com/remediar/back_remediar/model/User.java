package com.remediar.back_remediar.model;

import java.util.Collection;
import java.util.List;

import com.remediar.back_remediar.model.enums.StatusConta;
import jakarta.persistence.*;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private StatusConta status;

    @OneToOne(mappedBy = "user")
    private UsuarioComum usuarioComum;

    @OneToOne(mappedBy = "user")
    private Funcionario funcionario;

    public User (String login, String password, UserRole role){
        this.login = login;
        this.password = password;
        this.role = role;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_FUNCIONARIO"));
        else if(this.role == UserRole.FUNCIONARIO) return List.of(new SimpleGrantedAuthority("ROLE_FUNCIONARIO"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));

    }
    
    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
