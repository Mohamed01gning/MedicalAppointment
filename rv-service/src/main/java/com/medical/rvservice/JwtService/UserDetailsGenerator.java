package com.medical.rvservice.JwtService;

import com.medical.rvservice.Exception.InvalidTokenException;
import com.medical.rvservice.Model.UserToken;
import com.medical.rvservice.Service.CallService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsGenerator implements UserDetailsService
{
    private final CallService callService;

    public UserDetailsGenerator(CallService callService) {
        this.callService = callService;
    }

    @Override
    public UserDetails loadUserByUsername(String matricule) throws UsernameNotFoundException {
        UserToken admin= callService.getAdminByMat(matricule);
        UserToken sec= callService.getSecretaireByMat(matricule);
        UserToken med=callService.getMedecinByMat(matricule);

        if (admin != null)
        {
            return User.builder()
                    .username(admin.getMatricule())
                    .password(admin.getPwd())
                    .roles(admin.getRole())
                    .build();
        }
        else if (sec!=null)
        {
            return User.builder()
                    .username(sec.getMatricule())
                    .password(sec.getPwd())
                    .roles(sec.getRole())
                    .build();
        }
        else if (med!=null)
        {
            return User.builder()
                    .username(med.getMatricule())
                    .password(med.getPwd())
                    .roles(med.getRole())
                    .build();
        }
        else {
            throw new InvalidTokenException("Authentication Failed");
        }
    }
}
