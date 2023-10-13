package io.grocery.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import io.grocery.backend.dto.AuthRequest;
import io.grocery.backend.dto.AuthResponse;
import io.grocery.backend.dto.PatchRequest;
import io.grocery.backend.dto.UserDto;
import io.grocery.backend.entity.User;
import io.grocery.backend.entity.Enumerate.Role;
import io.grocery.backend.repository.UserRepository;
import io.grocery.backend.util.JwtUtil;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	@Autowired
	private UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	public ResponseEntity<String> addUser(UserDto user) {
		Optional<User> isUserExists = userRepository.findByEmail(user.getEmail());

		if (isUserExists.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists");
		} else {
			User n = User.builder()
					.name(user.getName())
					.email(user.getEmail())
					.password(user.getPassword())
					.country(user.getCountry())
					.contact(user.getContact())
					.role(Role.CUSTOMER)
					.build();
			userRepository.save(n);
			return ResponseEntity.status(HttpStatus.OK).body("User saved successfully");
		}
	}

	public ResponseEntity<?> authUser(AuthRequest request) {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
		}
		var existingUser = userRepository.findByEmail(request.getEmail()).orElse(null);
		if (existingUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		var token = jwtUtil.generateToken(existingUser);
		return ResponseEntity.ok(AuthResponse.builder()
				.token(token)
				.contact(existingUser.getContact())
				.country(existingUser.getCountry())
				.name(existingUser.getName())
				.uid(existingUser.getUid())
				.email(existingUser.getEmail())
				.password(existingUser.getPassword())
				.build());
	}

	public ResponseEntity<?> updateUser(PatchRequest request) {
		User existingUser = userRepository.findByEmail(request.getEmail()).orElse(null);

		if (existingUser == null) {
			return ResponseEntity.notFound().build();
		}
		existingUser.setName(request.getName());
		existingUser.setContact(request.getContact());
		userRepository.save(existingUser);
		return ResponseEntity.ok(existingUser);
	}

	public ResponseEntity<?> findByUserId(Long uid) {
		try {
			User existingUser = userRepository.findByUid(uid).orElseThrow();
			if (existingUser == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(existingUser);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Finding user");
		}
	}
}
