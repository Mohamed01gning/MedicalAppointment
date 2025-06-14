package com.medical.rvservice.Configuration;

import com.medical.rvservice.Component.ServiceJwtFilter;
import com.medical.rvservice.Component.UserJwtFilter;
import com.medical.rvservice.Exception.InvalidTokenException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class RvSecurityConfig
{
    private final UserJwtFilter userJwtFilter;
    private final ServiceJwtFilter serviceJwtFilter;

    public RvSecurityConfig(UserJwtFilter userJwtFilter, ServiceJwtFilter serviceJwtFilter)
    {
        this.userJwtFilter = userJwtFilter;
        this.serviceJwtFilter = serviceJwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)
            throws Exception
    {
        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session->session.disable())
                .authorizeHttpRequests(httpRequest -> httpRequest
                        .anyRequest().authenticated()
                        //.anyRequest().permitAll()
                )
                .addFilterBefore(userJwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(serviceJwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(handleAccessDeniedHandler())
                )
                ;

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint()
    {
        return (request, response, authException) -> {

            InvalidTokenException ex=(InvalidTokenException) request.getAttribute("ex");
            String msg=(ex==null) ? "Absence de Token" : ex.getMsg();

            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\""+msg+"\"}");
        };
    }

    @Bean
    public AccessDeniedHandler handleAccessDeniedHandler()
    {
        return (request, response, accessDeniedException) -> {
          response.setStatus(403);
          response.setContentType("application/json");
          response.getWriter().write("{\"error\":\"Votre Role Ne Vous Permet Pas D'effectuer Cette Operation\"}");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
