package com.medical.secretaireservice.JwtService;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class ServiceTokenParser
{
    private final ServiceKeyGenerator keyGenerator;


    public ServiceTokenParser(ServiceKeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    public String getServiceName(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(keyGenerator.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValidated(String token)
    {
        String serviceName=getServiceName(token);
        return  serviceName.equals("cnx-service") ||
                serviceName.equals("admin-service") ||
                serviceName.equals("medecin-service")||
                serviceName.equals("rv-service");
    }

}
