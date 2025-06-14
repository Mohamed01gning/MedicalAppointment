package com.medical.medecinservice.Exception;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticationToken extends AbstractAuthenticationToken
{
    private String serviceName;

    public AuthenticationToken(String serviceName ,Collection<? extends GrantedAuthority> authorities)
    {
        super(authorities);
        this.serviceName=serviceName;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
