package com.medical.cnxservice.Service;


import com.medical.cnxservice.Model.LoginData;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CnxService
{

    private final PasswordEncoder passwordEncoder;

    public CnxService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isAuthenticated(LoginData loginData, UserDetails userDetails)
    {
        return loginData.getMatricule()!=null && passwordEncoder.matches(loginData.getPwd(), userDetails.getPassword());
    }
}
