package com.bank.api.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static com.bank.api.gateway.util.UserAccessRole.ROLE_USER;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http.
                authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/users/**").hasAuthority(ROLE_USER)
                        .pathMatchers("/payments/**").hasAuthority(ROLE_USER)
                        .pathMatchers("/transactions/**").hasAuthority(ROLE_USER)
                        .pathMatchers("/notifications/**").hasAuthority(ROLE_USER)
                        .pathMatchers("/fraud-detections/**").hasAuthority(ROLE_USER)
                        .pathMatchers("/health/**").permitAll()
                        .pathMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                                "/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll()
                        .anyExchange().authenticated())
                        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                        .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();

    }

}
