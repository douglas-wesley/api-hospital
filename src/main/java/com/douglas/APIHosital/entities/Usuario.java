package com.douglas.APIHosital.entities;

import com.douglas.APIHosital.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "tb_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeUsuario;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore // Evita export a senha
    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    private Role role; // Define o tipo de usuário

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Define as permissões "roles" do usuário
        if (this.role == Role.MEDICO) {
            return List.of(new SimpleGrantedAuthority("ROLE_MEDICO"),
                    new SimpleGrantedAuthority("ROLE_ENFERMEIRO"),
                    new SimpleGrantedAuthority("ROLE_ATENDENTE")); // Médico tem todas as permissões
        } else if (this.role == Role.ENFERMEIRO) {
            return List.of(new SimpleGrantedAuthority("ROLE_ENFERMEIRO")); // Enfermeiro tem permissão de enfermeiro
        } else if (this.role == Role.ATENDENTE) {
            return List.of(new SimpleGrantedAuthority("ROLE_ATENDENTE")); // Atendente tem permissão de atendente
        } else if (this.role == Role.FARMACIA) {
            return List.of(new SimpleGrantedAuthority("ROLE_FARMACIA")); // Farmácia tem permissão de farmácia
        }
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.senha; // O campo que guarda a senha
    }

    @Override
    public String getUsername() {
        return this.email; // O campo que o Spring usará como "username"
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Conta nunca expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Conta nunca é bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credenciais nunca expiram
    }

    @Override
    public boolean isEnabled() {
        return true; // Conta está sempre habilitada
    }

}
