package com.medical.adminservice.JwtService;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;

@Service
public class ServiceKeyGenerator
{

    private SecretKey secretKey;

    public ServiceKeyGenerator(@Value("${secret.key.service}") String base64Key)
    {
        byte[] decodedKey= Base64.getDecoder().decode(base64Key);
        this.secretKey= Keys.hmacShaKeyFor(decodedKey);
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }
}
