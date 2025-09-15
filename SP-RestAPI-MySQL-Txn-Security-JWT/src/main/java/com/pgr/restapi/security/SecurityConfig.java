package com.pgr.restapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pgr.restapi.service.CustomUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

	private final JwtRequestFilter jwtRequestFilter;
	private final CustomUserDetailsService customUserDetailsService;

	public SecurityConfig(JwtRequestFilter jwtRequestFilter, CustomUserDetailsService customUserDetailsService) {
		this.jwtRequestFilter = jwtRequestFilter;
		this.customUserDetailsService = customUserDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		log.info("securityFilterChain(-)");

		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
				// Swagger + OpenAPI paths (ignore JWT)
				.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

				// Authentication API
				.requestMatchers("/api/v1/authenticate").permitAll()

				// Actuator (health/info public, rest protected optional)
				.requestMatchers("/actuator/**").permitAll()

				// Secure all other APIs with JWT
				.requestMatchers("/api/**").authenticated()

				// Everything else → public (optional)
				.anyRequest().permitAll())
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return customUserDetailsService;
	}
}