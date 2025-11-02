package com.douglas.APIHosital.security;


import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll() // Register & Login
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        // Permitir acesso ao endpoint /error para evitar loops de redirecionamento
                        .requestMatchers("/error").permitAll()

                        // Definição de permissões por função
                        .requestMatchers(HttpMethod.POST, "/fichas").hasRole("ATENDENTE") // Postar Ficha
                        .requestMatchers(HttpMethod.PUT, "/fichas/{fichaId}/classificar").hasRole("ENFERMEIRO") // Classificar Risco
                        .requestMatchers(HttpMethod.POST, "/fichas/{fichaId}/atender").hasRole("MEDICO") // Atender Paciente
                        .requestMatchers(HttpMethod.POST, "/fichas/{fichaId}/concluir").hasRole("MEDICO") // Concluir Atendimento
                        .requestMatchers(HttpMethod.POST, "/fichas/{fichaId}/medicar").hasRole("FARMACIA") // Medicar Paciente

                        .anyRequest().authenticated() // Todas as outras requisições precisam estar autenticadas
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona o filtro de segurança personalizado
                .build();
    }

    @Bean // Configura o AuthenticationManager
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean // Configura o PasswordEncoder
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
