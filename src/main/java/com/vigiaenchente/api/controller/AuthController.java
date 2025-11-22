package com.vigiaenchente.api.controller;

import com.vigiaenchente.api.dto.LoginRequest;
import com.vigiaenchente.api.dto.RegisterRequest;
import com.vigiaenchente.api.dto.ApiResponse;
import com.vigiaenchente.api.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpSession session) {

        var userDto = authService.authenticate(request.getEmail(), request.getSenha());

        session.setAttribute("userId", userDto.getId());
        session.setAttribute("userName", userDto.getNome());
        session.setAttribute("userEmail", userDto.getEmail());

        return ResponseEntity.ok(
                ApiResponse.success("Login bem-sucedido", userDto)
        );
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Usuário cadastrado com sucesso!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(
                ApiResponse.success("Logout bem-sucedido!")
        );
    }
}