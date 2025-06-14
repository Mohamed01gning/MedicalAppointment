package com.medical.secretaireservice.Component;

import com.medical.secretaireservice.JwtService.ServiceTokenParser;
import com.medical.secretaireservice.Service.AuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class ServiceJwtFilter extends OncePerRequestFilter
{
    private final ServiceTokenParser tokenParser;

    public ServiceJwtFilter(ServiceTokenParser tokenParser)
    {
        this.tokenParser = tokenParser;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
    throws ServletException, IOException
    {
        System.out.println("Dans ServiceJwtFilter => secretaire-service");
        String reqHeader=request.getHeader("Authorization-Service");
        if (reqHeader!=null && reqHeader.startsWith("Bearer"))
        {
            String token=reqHeader.substring(7);
            if (token != null && tokenParser.isTokenValidated(token))
            {
                String serviceName=tokenParser.getServiceName(token);
                List<GrantedAuthority> authorities=List.of(new SimpleGrantedAuthority("ROLE_SERVICE"));
                AuthenticationToken auth=new AuthenticationToken(serviceName,authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
                System.out.println(serviceName+" communique avec secretaire-service");
            }
        }
        filterChain.doFilter(request,response);
    }
}
