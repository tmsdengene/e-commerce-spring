package com.gibesystems.ecommerce.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gibesystems.ecommerce.auth.entity.User;
import com.gibesystems.ecommerce.auth.entity.UserRole;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByRole(UserRole candidate);

    Page<User> findByRole(UserRole candidate, Pageable pageable);

    Page<User> findByRoleAndFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            UserRole candidate, String query, String query2, String query3, Pageable pageable);

    long countByRole(UserRole candidate);
    
    
    //List<User> findByRoleAndGroupIsNull(UserRole role);

}
