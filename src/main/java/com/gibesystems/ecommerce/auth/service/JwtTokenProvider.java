package com.gibesystems.ecommerce.auth.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.gibesystems.ecommerce.auth.entity.User;

@Component
public class JwtTokenProvider {

    public String generateToken(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateToken'");
    }

    public String generateRefreshToken(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateRefreshToken'");
    }

    public LocalDateTime getExpirationDateFromToken(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getExpirationDateFromToken'");
    }

    public boolean validateToken(String refreshToken) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateToken'");
    }

    public String getUserIdFromToken(String refreshToken) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserIdFromToken'");
    }

}
