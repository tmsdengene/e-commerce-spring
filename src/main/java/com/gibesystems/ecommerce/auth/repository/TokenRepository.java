package com.gibesystems.ecommerce.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gibesystems.ecommerce.auth.entity.Token;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);
}
