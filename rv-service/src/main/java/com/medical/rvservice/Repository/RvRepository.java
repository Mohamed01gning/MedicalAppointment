package com.medical.rvservice.Repository;

import com.medical.rvservice.Model.Rv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RvRepository extends JpaRepository<Rv, String>
{
    public Rv findByDate(LocalDate date);
    public Rv findByIdSec(String idSec);
    public Rv findByIdMed(String idMed);
}
