package com.medical.adminservice.Component;

import com.medical.adminservice.Exception.InvalidTokenException;
import com.medical.adminservice.JwtService.UserTokenParser;
import com.medical.adminservice.Service.UserDetailsGenerator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserJwtFilter extends OncePerRequestFilter
{
    private final UserTokenParser tokenParser;
    private final UserDetailsGenerator userDetailsGenerator;

    public UserJwtFilter(UserTokenParser tokenParser, UserDetailsGenerator userDetailsGenerator) {
        this.tokenParser = tokenParser;
        this.userDetailsGenerator = userDetailsGenerator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {

        try
        {
            String reqHeader= request.getHeader("Authorization");
            if (reqHeader != null && reqHeader.startsWith("Bearer"))
            {
                String token=reqHeader.substring(7);
                tokenParser.checkToken(token);
                if (token != null)
                {
                    String matFromToken=tokenParser.extractMatricule(token);
                    System.out.println("TOKEN NON NULL");
                    if (matFromToken!=null && SecurityContextHolder.getContext().getAuthentication()==null)
                    {
                        System.out.println("Matricule extraite = "+matFromToken);
                        UserDetails userDetails=userDetailsGenerator.loadUserByUsername(matFromToken);
                        System.out.println("Token valid = "+tokenParser.isTokenValid(token,userDetails));
                        if (tokenParser.isTokenValid(token,userDetails))
                        {
                            UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                            SecurityContextHolder.getContext().setAuthentication(auth);
                        }
                    }
                }
            }
        }
        catch (InvalidTokenException ex)
        {
            System.out.println("InvalidTokenException LEVEE");
            request.setAttribute("ex",ex);
        }
        filterChain.doFilter(request,response);
    }
}
