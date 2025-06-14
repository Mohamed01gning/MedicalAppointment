package com.medical.rvservice.JwtService;

import com.medical.rvservice.Exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class UserTokenParser
{
    private final UserKeyGenerator keyGenerator;

    public UserTokenParser(UserKeyGenerator keyGenerator)
    {
        this.keyGenerator = keyGenerator;
    }

    public void checkToken(String token)
    {
        try
        {
            Jwts.parserBuilder()
                    .setSigningKey(keyGenerator.getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch(SignatureException ex) { throw new InvalidTokenException("Invalid Jwt Signature"); }
        catch(MalformedJwtException ex) { throw new InvalidTokenException("Invalid Jwt Token"); }
        catch(IllegalArgumentException ex) { throw new InvalidTokenException("Jwt Claimms String is Empty"); }
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(keyGenerator.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getMatricule(String token)
    {
        return getAllClaims(token)
                .getSubject();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractMatricule(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails)
    {
        String mailExtracted = extractMatricule(token);
        return (mailExtracted.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token)
    {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}


