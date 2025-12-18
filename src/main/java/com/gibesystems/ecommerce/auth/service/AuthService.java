package com.gibesystems.ecommerce.auth.service;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gibesystems.ecommerce.auth.dto.AuthResponseDTO;
import com.gibesystems.ecommerce.auth.dto.LoginRequestDTO;
import com.gibesystems.ecommerce.auth.dto.RegisterRequestDTO;
import com.gibesystems.ecommerce.auth.dto.UserDTO;
import com.gibesystems.ecommerce.auth.entity.User;
import com.gibesystems.ecommerce.auth.entity.UserRole;
import com.gibesystems.ecommerce.auth.repository.UserRepository;
import com.gibesystems.ecommerce.exception.ConflictException;
import com.gibesystems.ecommerce.exception.InvalidTokenException;
import com.gibesystems.ecommerce.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    // private final JwtTokenProvider jwtTokenProvider;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthResponseDTO login(LoginRequestDTO request) {
        log.info("Login attempt for email: {}", request.getEmail());

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = (User) authentication.getPrincipal();
        // User user = userRepository.findByEmail(request.getEmail())
        // .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        String token = jwtService.generateToken(user, user.getFullName(), user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user);

        UserDTO userDTO = userMapper.toDTO(user);

        log.info("User {} logged in successfully", user.getEmail());

        return AuthResponseDTO.builder()
                .token(token)
                .refreshToken(refreshToken)
                .user(userDTO)
                .expiresAt(jwtService.extractExpiration(token))
                .build();
    }

    public UserDTO register(RegisterRequestDTO request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already exists");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .enabled(true)
                .accountLocked(false)
                .build();

        User savedUser = userRepository.save(user);
        log.info("User {} registered successfully", savedUser.getEmail());

        return userMapper.toDTO(savedUser);
    }

    public void logout(String token) {
        // Add token to blacklist if implementing token blacklisting
        log.info("User logged out");
    }

    public String refreshToken(String refreshToken) {
        if (!jwtService.validateToken(refreshToken)) {
            throw new InvalidTokenException("Invalid refresh token");
        }

        String userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return jwtService.generateToken(user, user.getFullName(), user.getEmail());
    }

    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }

    public String extractUserIdFromToken(String token) {
        return jwtService.getUserIdFromToken(token);
    }

}
