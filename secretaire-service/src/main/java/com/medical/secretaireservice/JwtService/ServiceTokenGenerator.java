package com.medical.secretaireservice.JwtService;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServiceTokenGenerator
{
    private final ServiceKeyGenerator serviceKeyGenerator;
    @Value("${spring.application.name}") private String serviceName;

    public ServiceTokenGenerator(ServiceKeyGenerator serviceKeyGenerator) {
        this.serviceKeyGenerator = serviceKeyGenerator;
    }

    public String generatorServiceToken()
    {
        return Jwts.builder()
                .setSubject(serviceName)
                .signWith(serviceKeyGenerator.getSecretKey())
                .compact();
    }
}
