package org.example.taskmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.dto.AuthRequest;
import org.example.taskmanagementsystem.dto.AuthResponse;
import org.example.taskmanagementsystem.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest   authRequest) {

        return ResponseEntity.ok(authService.authenticate(authRequest));
    }


}