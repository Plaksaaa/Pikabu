package com.plaxa.pikabu.security;

import com.plaxa.pikabu.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class CustomUserDetails implements UserDetails {

    private String login;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static CustomUserDetails fromUserEntityToCustomUserDetails(User user) {
        CustomUserDetails c = new CustomUserDetails();
        c.login = user.getUsername();
        c.password = user.getPassword();
        c.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
        return c;
    }

    public static String hasRole(String role) {
        Objects.requireNonNull(role, "role cannot be null");
        if (role.startsWith("ROLE_")) {
            throw new IllegalArgumentException(
                    "role should not start with 'ROLE_' since it is automatically inserted. Got '" +
                            role + "'"
            );
        }
        return "hasRole('ROLE_" + role + "')";
    }

//    @Override
//    @Transactional(readOnly = true)
//    public UserDetails loadUserByUsername(String username) {
//        Optional<User> userOptional = userRepository.findByUsername(username);
//        User user = userOptional
//                .orElseThrow(() -> new UsernameNotFoundException("No user " +
//                        "Found with username : " + username));
//
//        return new org.springframework.security
//                .core.userdetails.User(user.getUsername(), user.getPassword(),
//                user.isEnabled(), true, true,
//                true, getAuthorities("USER"));
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
