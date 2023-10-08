package io.grocery.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.grocery.backend.dto.AuthRequest;
import io.grocery.backend.dto.AuthenticationResponse;
import io.grocery.backend.dto.UserDto;
import io.grocery.backend.entity.User;
import io.grocery.backend.service.UserService;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> addNewUser(@RequestBody UserDto user) {
        return userService.addUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody AuthRequest user) {
        return ResponseEntity.ok(userService.authUser(user));
    }

}
