package com.medical.secretaireservice.Repository;

import com.medical.secretaireservice.Model.Secretaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecRepository extends JpaRepository<Secretaire,String>
{
    public Secretaire findByEmail(String email);
}
