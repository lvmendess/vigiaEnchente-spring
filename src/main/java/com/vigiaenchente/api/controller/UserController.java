package com.vigiaenchente.api.controller;

import com.vigiaenchente.api.dto.AddressRequest;
import com.vigiaenchente.api.dto.ApiResponse;
import com.vigiaenchente.api.dto.UserProfileResponse;
import com.vigiaenchente.api.service.UserService;
import com.vigiaenchente.api.service.AddressService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowCredentials = "true")
public class UserController {

    private final UserService userService;
    private final AddressService addressService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }

        var profile = userService.getUserProfile(userId);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/address")
    public ResponseEntity<ApiResponse> saveAddress(
            @Valid @RequestBody AddressRequest request,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }

        addressService.saveOrUpdateAddress(userId, request);
        return ResponseEntity.ok(
                ApiResponse.success("Endereço salvo com sucesso!")
        );
    }
}
