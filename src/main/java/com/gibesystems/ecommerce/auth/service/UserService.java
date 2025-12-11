package com.gibesystems.ecommerce.auth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gibesystems.ecommerce.auth.dto.CreateUserDTO;
import com.gibesystems.ecommerce.auth.dto.UpdateUserDTO;
import com.gibesystems.ecommerce.auth.dto.UserDTO;
import com.gibesystems.ecommerce.auth.entity.User;
import com.gibesystems.ecommerce.auth.entity.UserRole;
import com.gibesystems.ecommerce.auth.repository.UserRepository;
import com.gibesystems.ecommerce.exception.ConflictException;
import com.gibesystems.ecommerce.exception.InvalidUserRoleException;
import com.gibesystems.ecommerce.exception.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
  
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Transactional(readOnly = true)
    public List<UserDTO> getAllCandidates() {
        List<User> candidates = userRepository.findByRole(UserRole.CUSTOMER);
        return candidates.stream()
            .map(userMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public UserDTO getCandidateById(String id) {
        User user = findUserById(id);
        if (user.getRole() != UserRole.CUSTOMER) {
            throw new InvalidUserRoleException("User is not a candidate");
        }
        return userMapper.toDTO(user);
    }
    
    public UserDTO createCandidate(CreateUserDTO request) {
        log.info("Creating candidate: {}", request.getEmail());
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already exists");
        }
        
        User candidate = User.builder()
                .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(UserRole.CUSTOMER)
            .build();
        
       
        User savedCandidate = userRepository.save(candidate);
        log.info("Candidate {} created successfully", savedCandidate.getEmail());
        
        return userMapper.toDTO(savedCandidate);
    }
    
    public UserDTO updateCandidate(String id, UpdateUserDTO request) {
        log.info("Updating candidate: {}", id);
        
        User candidate = findUserById(id);
        if (candidate.getRole() != UserRole.CUSTOMER) {
            throw new InvalidUserRoleException("User is not a candidate");
        }
        
        if (request.getFirstName() != null) {
            candidate.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            candidate.setLastName(request.getLastName());
        }
        if (request.getEmail() != null) {
            if (!candidate.getEmail().equals(request.getEmail()) && 
                userRepository.existsByEmail(request.getEmail())) {
                throw new ConflictException("Email already exists");
            }
            candidate.setEmail(request.getEmail());
        }
    
        
        User updatedCandidate = userRepository.save(candidate);
        log.info("Candidate {} updated successfully", updatedCandidate.getId());
        
        return userMapper.toDTO(updatedCandidate);
    }
    
    public void deleteCandidate(String id) {
        log.info("Deleting candidate: {}", id);
        
        User candidate = findUserById(id);
        if (candidate.getRole() != UserRole.CUSTOMER) {
            throw new InvalidUserRoleException("User is not a candidate");
        }
        
        userRepository.delete(candidate);
        log.info("Candidate {} deleted successfully", id);
    }
    
    @Transactional(readOnly = true)
    public UserDTO getCurrentUserProfile(String userId) {
        User user = findUserById(userId);
        return userMapper.toDTO(user);
    }
    
    public UserDTO updateProfile(String userId, UpdateUserDTO request) {
        log.info("Updating profile for user: {}", userId);
        
        User user = findUserById(userId);

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        
        if (request.getEmail() != null) {
            if (!user.getEmail().equals(request.getEmail()) && 
                userRepository.existsByEmail(request.getEmail())) {
                throw new ConflictException("Email already exists");
            }
            user.setEmail(request.getEmail());
        }
        
        User updatedUser = userRepository.save(user);
        log.info("Profile updated successfully for user: {}", userId);
        
        return userMapper.toDTO(updatedUser);
    }

    
    private User findUserById(String id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
    
}
