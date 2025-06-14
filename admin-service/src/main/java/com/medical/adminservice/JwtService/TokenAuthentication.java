package com.medical.adminservice.JwtService;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class TokenAuthentication extends AbstractAuthenticationToken
{
    private String serviceName;

    public TokenAuthentication(String serviceName,Collection<? extends GrantedAuthority> authorities) {
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
        return this.serviceName;
    }
}
