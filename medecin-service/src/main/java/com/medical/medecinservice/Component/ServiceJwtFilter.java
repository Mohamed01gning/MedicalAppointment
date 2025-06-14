package com.medical.medecinservice.Component;

import com.medical.medecinservice.Exception.AuthenticationToken;
import com.medical.medecinservice.JwtService.ServiceTokenParser;
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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        String reqHeader=request.getHeader("Authorization-Service");
        if (reqHeader != null && reqHeader.startsWith("Bearer"))
        {
            String token=reqHeader.substring(7);
            if (token != null)
            {
                String serviceName=tokenParser.extractServiceName(token);
                if (tokenParser.isTokenValidaded(token))
                {
                    List<GrantedAuthority> authorities=List.of(new SimpleGrantedAuthority("ROLE_SERVICE"));
                    AuthenticationToken auth=new AuthenticationToken(serviceName,authorities);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println(serviceName+" vient de communiquer avec medecin-service");
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
