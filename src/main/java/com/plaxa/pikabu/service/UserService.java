package com.plaxa.pikabu.service;

import com.plaxa.pikabu.dto.RegisterRequestDto;
import com.plaxa.pikabu.dto.UserReadDto;
import com.plaxa.pikabu.entity.User;
import com.plaxa.pikabu.mapper.UserMapper;
import com.plaxa.pikabu.repository.UserRepository;
import com.plaxa.pikabu.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Optional<UserReadDto> findByPasswordAndUsername(String password, String username) {
        return userRepository.findByPasswordAndUsername(password, username)
                .map(userMapper::mapUserToUserReadDto);
    }

    public User getCurrentUser() {
        return userRepository.findByUsername(SecurityUtils.getCurrentUser())
                .orElseThrow();
    }

    public Optional<UserReadDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::mapUserToUserReadDto);
    }

    @Transactional
    public UserReadDto save(RegisterRequestDto registerRequestDto) {
        return Optional.of(registerRequestDto)
                .map(userMapper::mapRegisterDtoToUser)
                .map(userRepository::save)
                .map(userMapper::mapUserToUserReadDto)
                .orElseThrow();
    }
}
