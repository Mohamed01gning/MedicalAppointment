package com.medical.medecinservice.Repository;

import com.medical.medecinservice.Model.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedRepository extends JpaRepository<Medecin,String>
{
    public Medecin findByEmail(String email);
}
