package com.nox.JavaBootCampAdv.config;

import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
class SecurityConfig {
    public static final String RESOURCE_ACCESS = "resource_access";
    private static final String ROLES = "roles";
    private static final String AUTHORITY_ADMIN = "admin";
    private static final String AUTHORITY_MANAGER = "manager";
    private static final String AUTHORITY_USER = "user";
    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String keycloakClientId;

    @Bean
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/**/generate/**")).hasAuthority(AUTHORITY_ADMIN)
                        .requestMatchers(new AntPathRequestMatcher("/salaryPayments*")).hasAuthority(AUTHORITY_MANAGER)
                        .requestMatchers(new AntPathRequestMatcher("/*")).hasAuthority(AUTHORITY_USER)
                        .anyRequest().authenticated())
                //.requestMatchers(new AntPathRequestMatcher("/")).authenticated());
                .oauth2ResourceServer(oath2 -> oath2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {
        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {
            Map<String, Object> resourceAccess = jwt.getClaim(RESOURCE_ACCESS);
            Object client = resourceAccess.get(keycloakClientId);
            LinkedTreeMap<String, List<String>> clientRoleMap = (LinkedTreeMap<String, List<String>>) client;
            List<String> clientRoles = new ArrayList<>(clientRoleMap.get(ROLES));
            return clientRoles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        };
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
