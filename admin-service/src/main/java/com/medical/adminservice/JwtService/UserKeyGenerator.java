package com.medical.adminservice.JwtService;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;

@Service
public class UserKeyGenerator
{
    private SecretKey secretKey;

    public UserKeyGenerator(@Value("${secret.key.user}") String base64Key)
    {
        byte[] decodedKey= Base64.getDecoder().decode(base64Key);
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }
}
