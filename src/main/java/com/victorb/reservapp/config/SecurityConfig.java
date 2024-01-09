package com.victorb.reservapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.victorb.reservapp.db.UsuarioRepository;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Component
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SecurityConfig {
	
	private @Nonnull UsuarioRepository usuarioRepository;
	
	private @Nonnull JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
    static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    UserDetailsService userDetailsService() {
        return username -> usuarioRepository.findByUsername(username).orElseThrow(() -> {
        	log.info("AUTH INFO: Username: '" + username + "' not found");
        	throw new UsernameNotFoundException("Username: " + username + " not found");
        } );
    }
	
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception {
        http
        	.csrf(AbstractHttpConfigurer::disable)
        	.sessionManagement(sessionMangConfig -> sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        	.authenticationProvider(authenticationProvider())
        	.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        	.authorizeHttpRequests(authConfig -> {
        		authConfig.requestMatchers("/api/**").authenticated();
        		authConfig.requestMatchers("/auth/login").permitAll();
        		authConfig.requestMatchers("/public/**").permitAll();
        		authConfig.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
        		authConfig.requestMatchers("/configuration/ui", "/swagger-resources/**", "/configuration/security", "/webjars/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();
        	});
        return http.build();
    }
}
