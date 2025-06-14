package com.medical.adminservice.Service;

import com.medical.adminservice.Model.Admin;
import com.medical.adminservice.Model.AdminCnx;
import com.medical.adminservice.Model.AdminDTO;
import com.medical.adminservice.Model.AdminToken;
import org.springframework.stereotype.Service;

@Service
public class ConvertionService
{
    public AdminDTO convertToAdminDTO(Admin admin)
    {
        if (admin == null)
        {
            return null;
        }
        AdminDTO adminDTO=new AdminDTO(
                admin.getMatricule(),
                admin.getNom(),
                admin.getPrenom(),
                admin.getEmail(),
                admin.getTel(),
                admin.getRole()
        );
        return adminDTO;
    }

    public AdminCnx convetToAdminCnx(Admin admin)
    {
        if (admin==null)
        {
            return null;
        }
        AdminCnx adminCnx=new AdminCnx(
                admin.getMatricule(),
                admin.getPwd()
        );
        return adminCnx;
    }

    public AdminToken convetToAdminToken(Admin admin)
    {
        if (admin==null)
        {
            return null;
        }
        AdminToken adminToken=new AdminToken(
                admin.getMatricule(),
                admin.getPwd(),
                admin.getRole()
        );
        return adminToken;
    }

    public Admin convertToAdmin(AdminDTO adminDTO)
    {
        if (adminDTO == null)
        {
            return null;
        }
        Admin admin=new Admin(
                adminDTO.getMatricule(),
                adminDTO.getNom(),
                adminDTO.getPrenom(),
                adminDTO.getEmail(),
                adminDTO.getTel(),
                adminDTO.getRole(),
                adminDTO.getPwd()
                );
        return admin;
    }

}
