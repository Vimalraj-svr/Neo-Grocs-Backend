package io.grocery.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.grocery.backend.dto.AuthRequest;
import io.grocery.backend.dto.PatchRequest;
import io.grocery.backend.dto.UserDto;
import io.grocery.backend.entity.User;
import io.grocery.backend.repository.UserRepository;
import io.grocery.backend.service.UserService;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{email}")
    public ResponseEntity<?> findUserandCartItem(@PathVariable String email) {
        return ResponseEntity.ok(userRepository.findByEmail(email));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewUser(@RequestBody UserDto user) {
        return userService.addUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest user) {
        var authResponse = userService.authUser(user);
        if (authResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        return ResponseEntity.ok(authResponse);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody PatchRequest request) {
        var updatedResponse = userService.updateUser(request);
        if (updatedResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedResponse);
    }

    // @GetMapping("/user/{uid}")
    // public ResponseEntity<?> findByUserId(@PathVariable Long uid) {
    // var existingUser = userService.findByUserId(uid);
    // if (existingUser == null) {
    // return ResponseEntity.notFound().build();
    // }
    // return ResponseEntity.ok(existingUser);
    // }

    // @GetMapping("/user/current")
    // public ResponseEntity<User> findCurrentuser(@AuthenticationPrincipal User
    // user) {
    // return ResponseEntity.ok(user);
    // }

}
