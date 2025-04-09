package com.portfolio.demo.config;

//import org.springframework.context.annotation.Bean;

//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//	@SuppressWarnings("removal")
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.csrf().disable().authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
//		return http.build();
//	}
//}

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Marks this class as a Spring configuration bean
public class SecurityConfig {

	// ✅ Main Security Configuration
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				// Disable CSRF protection (useful for APIs, but enable it for forms in
				// production)
				.csrf(csrf -> csrf.disable())

				// Define which endpoints should be accessible without authentication
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**").permitAll() // Login & Register
																								// should be public
						.anyRequest().authenticated() // All other requests must be authenticated
				)

				// Disable default Spring Security login form
				.formLogin(login -> login.disable());

		return http.build();
	}

	// ✅ Password encoder bean
	// This is used to hash passwords using BCrypt (industry standard)
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // You can adjust strength as needed
	}

	// ✅ Define how authentication is handled
	// DaoAuthenticationProvider will use your custom UserDetailsService to load
	// user from DB
	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService); // Link to your user loader
		authProvider.setPasswordEncoder(passwordEncoder()); // Use our BCrypt password encoder
		return authProvider;
	}

	// ✅ Expose AuthenticationManager for manual login (we’ll use this in the login
	// endpoint)
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
