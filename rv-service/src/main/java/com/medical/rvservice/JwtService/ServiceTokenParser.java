package com.medical.rvservice.JwtService;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class ServiceTokenParser
{

    private final ServiceKeyGenerator keyGenerator;


    public ServiceTokenParser(ServiceKeyGenerator keyGenerator)
    {
        this.keyGenerator = keyGenerator;
    }

    public String extractServiceName(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(keyGenerator.generateServiceSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValidaded(String token)
    {
        String serviceName=extractServiceName(token);
        if (serviceName.equals("cnx-service") ||
            serviceName.equals("secretaire-service") ||
            serviceName.equals("admin-service")
        )
        {
            return true;
        }
        return false;
    }





}
