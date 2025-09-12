package com.pgr.restapi.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						authz -> authz.requestMatchers("/api/v1/hello/**").permitAll().requestMatchers("/h2-console/**")
								.permitAll().requestMatchers(HttpMethod.GET, "/api/v1/users/**")
								.hasAnyRole("USER", "ADMIN").requestMatchers(HttpMethod.POST, "/api/v1/users")
								.hasRole("ADMIN").requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasRole("ADMIN")
								.requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasRole("ADMIN").anyRequest()
								.authenticated())
				.httpBasic();

		// Allow H2 console frames
		http.headers(headers -> headers.frameOptions().disable());

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.builder().username("user").password(passwordEncoder().encode("password")).roles("USER")
				.build();

		UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
				.build();

		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}