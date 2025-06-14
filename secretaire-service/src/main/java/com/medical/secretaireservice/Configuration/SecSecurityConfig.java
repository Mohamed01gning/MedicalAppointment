package com.medical.secretaireservice.Configuration;

import com.medical.secretaireservice.Component.ServiceJwtFilter;
import com.medical.secretaireservice.Component.UserJwtFilter;
import com.medical.secretaireservice.Exception.InvalidTokenException;
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
public class SecSecurityConfig
{
    private final UserJwtFilter userJwtFilter;
    private final ServiceJwtFilter serviceJwtFilter;

    public SecSecurityConfig(UserJwtFilter userJwtFilter, ServiceJwtFilter serviceJwtFilter) {
        this.userJwtFilter = userJwtFilter;
        this.serviceJwtFilter = serviceJwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)
            throws Exception
    {
        httpSecurity
                .sessionManagement(session->session.disable())
                .csrf(csrf->csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/sec/addPwd").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(serviceJwtFilter,UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(userJwtFilter,UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(accessDeniedHandler())
                        .authenticationEntryPoint(authenticationEntryPoint())
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
    public AccessDeniedHandler accessDeniedHandler()
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
