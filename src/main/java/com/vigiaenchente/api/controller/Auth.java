package com.vigiaenchente.api.controller;

import com.vigiaenchente.api.model.User;
import com.vigiaenchente.api.repository.UserRepository;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class Auth {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Auth(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody RegisterDTO registerDTO) {
        try {
            if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Email already registered.");
            }

            User user = new User();
            user.setName(registerDTO.getNome());
            user.setEmail(registerDTO.getEmail());
            user.setPhone(registerDTO.getPhone());
            user.setPassword(passwordEncoder.encode(registerDTO.getSenha()));

            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User registered successfully.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during registration: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Validated @RequestBody LoginDTO loginDTO) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(loginDTO.getEmail());

            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid credentials.");
            }

            User user = userOptional.get();

            // Use passwordEncoder.matches() for secure password comparison
            if (!passwordEncoder.matches(loginDTO.getSenha(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid credentials.");
            }

            return ResponseEntity.ok("Login successful.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during login: " + e.getMessage());
        }
    }

    // Alternative login method using Map (if you prefer)
    @PostMapping("/login-map")
    public ResponseEntity<?> loginUserWithMap(@RequestBody Map<String, String> loginRequest) {
        try {
            String email = loginRequest.get("email");
            String senha = loginRequest.get("senha");

            if (email == null || senha == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Email and password are required.");
            }

            Optional<User> userOptional = userRepository.findByEmail(email);

            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid credentials.");
            }

            User user = userOptional.get();

            if (!passwordEncoder.matches(senha, user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid credentials.");
            }

            return ResponseEntity.ok("Login successful.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during login: " + e.getMessage());
        }
    }
}

@Data
class LoginDTO {
    private String email;
    private String senha;
}

@Data
class RegisterDTO {
    private String nome;
    private String email;
    private String phone;
    private String senha;
}