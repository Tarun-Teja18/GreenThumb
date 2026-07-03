package dev.tarun.greenthumb.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.tarun.greenthumb.domain.Role;
import dev.tarun.greenthumb.domain.User;
import dev.tarun.greenthumb.dto.RegisterRequestDTO;
import dev.tarun.greenthumb.dto.UserDTO;
import dev.tarun.greenthumb.exception.NotFoundException;
import dev.tarun.greenthumb.repository.RoleRepository;
import dev.tarun.greenthumb.repository.UserRepository;
import dev.tarun.greenthumb.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;   // the BCrypt bean from SecurityConfiguration

    public AuthServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO register(RegisterRequestDTO request) {
        // 1. reject duplicate email
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalStateException("Email already in use: " + request.email());
        }

        // 2. build the user, HASHING the password (never store it raw)
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));   // ← BCrypt hash
        user.setActive(true);                                            // active immediately (no email verification yet)

        // 3. give them the default role
        Role userRole = roleRepository.findById("ROLE_USER")
                .orElseThrow(() -> new NotFoundException("Role not found: ROLE_USER"));
        user.setRoles(Set.of(userRole));

        // 4. save and return a safe DTO
        User saved = userRepository.save(user);
        return toDto(saved);
    }

    private UserDTO toDto(User u) {
        Set<String> roleNames = u.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        return new UserDTO(u.getId(), u.getName(), u.getEmail(), roleNames);
    }
}