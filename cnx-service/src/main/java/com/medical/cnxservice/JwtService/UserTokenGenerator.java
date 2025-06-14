package com.medical.cnxservice.JwtService;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserTokenGenerator
{
    private final UserKeyGenerator userKeyGenerator;
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    public UserTokenGenerator(UserKeyGenerator userKeyGenerator) {
        this.userKeyGenerator = userKeyGenerator;
    }

    public String generateToken(UserDetails userDetails)
    {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(userKeyGenerator.generateUserSecretKey())
                .compact();
    }
}
