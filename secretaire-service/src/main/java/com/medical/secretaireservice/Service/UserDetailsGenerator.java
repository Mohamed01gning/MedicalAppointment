package com.medical.secretaireservice.Service;

import com.medical.secretaireservice.Exception.InvalidTokenException;
import com.medical.secretaireservice.Model.AdminT;
import com.medical.secretaireservice.Model.Secretaire;
import com.medical.secretaireservice.Repository.SecRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsGenerator implements UserDetailsService
{
    private final SecRepository secRepository;
    private final CallService callService;

    public UserDetailsGenerator(SecRepository secRepository, CallService callService) {
        this.secRepository = secRepository;
        this.callService = callService;
    }

    @Override
    public UserDetails loadUserByUsername(String matricule)
            throws UsernameNotFoundException
    {
        AdminT admin= callService.getAdminByMat(matricule);
        Secretaire sec=secRepository.findById(matricule).orElse(null);
        if (sec != null)
        {
            return User.builder()
                    .username(sec.getMatricule())
                    .password(sec.getPwd())
                    .roles(sec.getRole())
                    .build();
        }
        else if (admin != null)
        {
            return User.builder()
                    .username(sec.getMatricule())
                    .password(sec.getPwd())
                    .roles(sec.getRole())
                    .build();
        }
        else
        {
            throw new InvalidTokenException("L'utilisateur n'existe pas");
        }
    }

}
