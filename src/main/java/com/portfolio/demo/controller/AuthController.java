package com.portfolio.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.demo.dto.AuthResponse;
import com.portfolio.demo.entity.Role;
import com.portfolio.demo.entity.User;
import com.portfolio.demo.repository.UserRepository;
import com.portfolio.demo.security.JwtService;

import jakarta.validation.Valid;

@RestController // Marks this class as a REST controller (returns JSON)
@RequestMapping("/api/auth") // Base URL for all endpoints in this controller
public class AuthController {

	@Autowired
	private UserRepository userRepository; // Used to save & fetch users from DB

	@Autowired
	private AuthenticationManager authenticationManager; // Performs login authentication

	@Autowired
	private PasswordEncoder passwordEncoder; // Encodes passwords before saving

	@Autowired
	private JwtService jwtService;

	// ✅ User Registration Endpoint
	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody User user) {
		// Check if username already exists
		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
			return ResponseEntity.badRequest().body("Username already exists");
		}

		// Encode the password before saving to DB
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// Set a default role for new users
		user.setRole(Role.ROLE_USER);

		// Save user to database
		userRepository.save(user);

		return ResponseEntity.ok("User registered successfully");
	}

	// ✅ User Login Endpoint
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody User loginRequest) {
		try {
			// Try to authenticate using provided credentials
			Authentication auth = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), // username
							loginRequest.getPassword() // raw password
					));

			// If authentication succeeds, return OK
//			return ResponseEntity.ok("Login successful");

			// Generate JWT using username
			UserDetails user = (UserDetails) auth.getPrincipal();
			String jwt = jwtService.generateToken(user.getUsername());

			// Return token
			return ResponseEntity.ok(new AuthResponse(jwt));

		} catch (BadCredentialsException ex) {
			return null;
			// If authentication fails, return 401 Unauthorized
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}
	}
}
