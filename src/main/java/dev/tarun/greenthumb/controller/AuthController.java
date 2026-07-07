package dev.tarun.greenthumb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.tarun.greenthumb.dto.AuthResponseDTO;
import dev.tarun.greenthumb.dto.LoginRequestDTO;
import dev.tarun.greenthumb.dto.RegisterRequestDTO;
import dev.tarun.greenthumb.dto.UserDTO;
import dev.tarun.greenthumb.service.AuthService;
import jakarta.validation.Valid;

@RestController                       // "this class handles HTTP and returns JSON"
@RequestMapping("/api/auth")      // base URL for every method here
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")                 // → POST /api/auth/register
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        UserDTO created = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
}