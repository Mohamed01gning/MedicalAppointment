package com.medical.adminservice.Service;

import com.medical.adminservice.Exception.AdminNotFoundEx;
import com.medical.adminservice.Exception.PwdException;
import com.medical.adminservice.Model.*;
import com.medical.adminservice.Repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService
{
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final ConvertionService convertionService;

    public AdminService(PasswordEncoder passwordEncoder, AdminRepository adminRepository, ConvertionService convertionService) {
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
        this.convertionService = convertionService;
    }

    public void addAdmin(AdminDTO adminDTO)
    {
        Admin admin= convertionService.convertToAdmin(adminDTO);
        adminRepository.save(admin);
    }

    public void setPassword(AdminPwd adminPwd)
    {
        AdminDTO adminDTO=getAdminByMatricule(adminPwd.getMatricule());
        if (!adminPwd.getPwd().equals(adminPwd.getPwdConf()))
        {
            throw new PwdException("Vos Deux Mot De pass Fournis ne sont pas identiques");
        }
        else
        {
            String pwdEncoded=passwordEncoder.encode(adminPwd.getPwdConf());
            adminDTO.setPwd(pwdEncoded);
            addAdmin(adminDTO);
        }
    }


    public AdminDTO getAdminByMatricule(String mat)
    {
        Admin admin=adminRepository.findById(mat).orElse(null);
        if (admin == null)
        {
            throw new AdminNotFoundEx("Administrateur inexistante ");
        }
        AdminDTO adminDTO= convertionService.convertToAdminDTO(admin);
        return adminDTO;
    }

    public AdminToken getAdminByMatForFilterToken(String mat)
    {
        Admin admin=adminRepository.findById(mat).orElse(null);
        AdminToken adminToken= convertionService.convetToAdminToken(admin);
        return adminToken;
    }

    public AdminCnx getAdminByMatForCnx(String mat)
    {
        Admin admin=adminRepository.findById(mat).orElse(null);
        AdminCnx adminCnx= convertionService.convetToAdminCnx(admin);
        return adminCnx;
    }

    public String generateMatricule()
    {
        String matricule="";
        boolean isMatExist=false;
        do
        {
            String num1=String.valueOf((int)(Math.random()*10000));
            String num2=String.valueOf((int)(Math.random()*1000));
            String mat="ADMIN-"+num1+"-"+num2;
            isMatExist=getAllAdmin().stream()
                    .anyMatch(adminDTO -> adminDTO.getMatricule().equals(mat)
                    );
            if (!isMatExist)
            {
                matricule=mat;
            }

        }while (isMatExist || matricule.length()!=14);

        return matricule;
    }

    public List<AdminDTO> getAllAdmin()
    {
        List<AdminDTO> adminDTOs=new ArrayList<>();
        List<Admin> admins=adminRepository.findAll();
        admins.forEach(admin ->{
            AdminDTO adminDTO= convertionService.convertToAdminDTO(admin);
            adminDTOs.add(adminDTO);
        });
        return adminDTOs;
    }

    public void deleteAdmin(String mat)
    {
        getAdminByMatricule(mat);
        adminRepository.deleteById(mat);
    }


}
