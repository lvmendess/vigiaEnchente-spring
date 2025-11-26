package com.vigiaenchente.api.service;

import com.vigiaenchente.api.dto.AddressRequest;
import com.vigiaenchente.api.repository.AddressRepository;
import com.vigiaenchente.api.repository.UserRepository;
import com.vigiaenchente.core.domain.entity.Address;
import com.vigiaenchente.core.domain.entity.User;
import com.vigiaenchente.core.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveOrUpdateAddress(Integer userId, AddressRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Address address = addressRepository.findByUserId(userId)
                .orElse(Address.builder().user(user).build());

        address.setStreet(request.getStreet());
        address.setNumber(request.getNum());
        address.setZipCode(request.getCep());
        address.setNeighborhood(request.getNeighbor());
        address.setCity(request.getCity());

        addressRepository.save(address);
    }
}
