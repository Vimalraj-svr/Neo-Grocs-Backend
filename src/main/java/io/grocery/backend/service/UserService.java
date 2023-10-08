package io.grocery.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import io.grocery.backend.dto.AuthRequest;
import io.grocery.backend.dto.AuthenticationResponse;
import io.grocery.backend.dto.UserDto;
import io.grocery.backend.entity.User;
import io.grocery.backend.repository.UserRepository;
import io.grocery.backend.util.JwtUtil;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	@Autowired
	private UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	public ResponseEntity<String> addUser(UserDto user) {
		User existingUser = userRepository.findByEmail(user.getEmail()).orElseThrow();

		if (existingUser != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists");
		} else {
			User n = User.builder()
					.name(user.getName())
					.email(user.getEmail())
					.password(user.getPassword())
					.country(user.getCountry())
					.contact(user.getContact())
					.build();
			userRepository.save(n);
			return ResponseEntity.status(HttpStatus.OK).body("User saved successfully");
		}
	}

	public AuthenticationResponse authUser(AuthRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var existingUser = userRepository.findByEmail(request.getEmail()).orElseThrow();
		var token = jwtUtil.generateToken(existingUser);
		return AuthenticationResponse.builder()
		.token(token)
		.email(existingUser.getEmail())
		.build();
	}
}
