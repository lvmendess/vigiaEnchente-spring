package com.vigiaenchente.api.service;

import com.vigiaenchente.api.dto.RegisterRequest;
import com.vigiaenchente.api.dto.UserDTO;
import com.vigiaenchente.api.repository.UserRepository;
import com.vigiaenchente.core.domain.entity.User;
import com.vigiaenchente.core.exception.AuthenticationException;
import com.vigiaenchente.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserDTO authenticate(String email, String senha) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senha, user.getSenha())) {
            throw new AuthenticationException("Senha incorreta");
        }

        return UserDTO.fromEntity(user);
    }

    @Transactional
    public void register(RegisterRequest request) {
        validateRegistration(request);

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("E-mail já cadastrado");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new BusinessException("Telefone já cadastrado");
        }

        User user = User.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .phone(request.getPhone())
                .senha(passwordEncoder.encode(request.getSenha()))
                .build();

        userRepository.save(user);
    }

    private void validateRegistration(RegisterRequest request) {
        if (request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new BusinessException("Nome é obrigatório");
        }
        if (request.getEmail() == null || !request.getEmail().contains("@")) {
            throw new BusinessException("E-mail inválido");
        }
        if (request.getPhone() == null || request.getPhone().replaceAll("\\D", "").length() != 11) {
            throw new BusinessException("Telefone inválido");
        }
        if (request.getSenha() == null || request.getSenha().length() < 6) {
            throw new BusinessException("Senha deve ter no mínimo 6 caracteres");
        }
    }
}
