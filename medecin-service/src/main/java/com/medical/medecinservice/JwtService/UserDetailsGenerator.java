package com.medical.medecinservice.JwtService;

import com.medical.medecinservice.Exception.InvalidTokenException;
import com.medical.medecinservice.Model.AdminT;
import com.medical.medecinservice.Model.Medecin;
import com.medical.medecinservice.Repository.MedRepository;
import com.medical.medecinservice.Service.CallService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsGenerator implements UserDetailsService
{
    private final MedRepository medRepository;
    private final CallService callService;

    public UserDetailsGenerator(MedRepository medRepository, CallService callService) {
        this.medRepository = medRepository;
        this.callService = callService;
    }


    @Override
    public UserDetails loadUserByUsername(String matricule)
            throws UsernameNotFoundException
    {
        AdminT admin= callService.getAdminByMat(matricule);
        AdminT sec = callService.getSecretaireByMat(matricule);
        Medecin medecin=medRepository.findById(matricule).orElse(null);
        if (admin != null)
        {
            return User.builder()
                    .username(admin.getMatricule())
                    .password(admin.getPwd())
                    .roles(admin.getRole())
                    .build();
        }
        else if (medecin != null)
        {
            return User.builder()
                    .username(medecin.getMatricule())
                    .password(medecin.getPwd())
                    .roles(medecin.getRole())
                    .build();
        }
        else if (sec != null)
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
