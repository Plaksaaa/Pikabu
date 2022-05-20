package com.plaxa.pikabu.service;

import com.plaxa.pikabu.entity.User;
import com.plaxa.pikabu.repository.UserRepository;
import com.plaxa.pikabu.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public CustomUserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userOptional.get());
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
