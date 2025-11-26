package com.vigiaenchente.api.service;

import com.vigiaenchente.api.dto.UserProfileResponse;
import com.vigiaenchente.api.repository.UserRepository;
import com.vigiaenchente.core.domain.entity.User;
import com.vigiaenchente.core.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        return UserProfileResponse.fromEntity(user);
    }
}
