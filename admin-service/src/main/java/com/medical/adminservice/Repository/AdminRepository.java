package com.medical.adminservice.Repository;

import com.medical.adminservice.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,String>
{
    public Admin findByEmail(String email);
}
