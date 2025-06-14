package com.medical.adminservice.Service;

import com.medical.adminservice.Exception.InvalidTokenException;
import com.medical.adminservice.Model.Admin;
import com.medical.adminservice.Repository.AdminRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsGenerator implements UserDetailsService
{
    private final AdminRepository adminRepository;

    public UserDetailsGenerator(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String matricule)
            throws UsernameNotFoundException
    {
        Admin admin= adminRepository.findById(matricule).orElse(null);
        if (admin == null)
        {
            throw new InvalidTokenException("L'utilisateur n'existe pas");
        }

        UserDetails userDetails=User.builder()
                .username(admin.getMatricule())
                .password(admin.getPwd())
                .roles(admin.getRole())
                .build();

        return userDetails;
    }
}
