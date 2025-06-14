package com.medical.cnxservice.Service;

import com.medical.cnxservice.Exception.NotFoundEx;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserDetailsGenerator implements UserDetailsService
{
    private final CallService callService;

    public UserDetailsGenerator(CallService callService) {
        this.callService = callService;
    }

    @Override
    public UserDetails loadUserByUsername(String matricule) throws UsernameNotFoundException
    {
        com.medical.cnxservice.Model.User med= callService.getMedecinByMat(matricule);
        com.medical.cnxservice.Model.User sec= callService.getSecretaireByMat(matricule);
        com.medical.cnxservice.Model.User admin = callService.getAdminByMat(matricule);

        if (sec!=null)
        {
            return User.builder()
                    .username(sec.getMatricule())
                    .password(sec.getPwd())
                    .build();
        }
        else if(med!= null)
        {
            return User.builder()
                    .username(med.getMatricule())
                    .password(med.getPwd())
                    .build();
        }
        else if (admin!=null)
        {
            return User.builder()
                    .username(admin.getMatricule())
                    .password(admin.getPwd())
                    .build();
        }
        else
        {
            Map<String,String> msg=new HashMap<>();
            msg.put("error","Matricule ou Password incorrect");
            throw new NotFoundEx(msg);
        }
    }
}
