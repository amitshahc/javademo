package com.portfolio.demo.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Get token from Authorization header
		final String authHeader = request.getHeader("Authorization");
		final String token;
		final String username;

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			// No token, continue filter chain
			filterChain.doFilter(request, response);
			return;
		}

		// Remove "Bearer " prefix
		token = authHeader.substring(7);
		username = jwtService.extractUsername(token);

		// If user not yet authenticated
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			// Validate the token against user
			if (jwtService.isTokenValid(token, userDetails.getUsername())) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities() // sets roles
				);

				// Set authentication
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		// Continue with the request
		filterChain.doFilter(request, response);
	}
}
