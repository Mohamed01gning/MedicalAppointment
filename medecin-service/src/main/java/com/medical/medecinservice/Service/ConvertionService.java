package com.medical.medecinservice.Service;

import com.medical.medecinservice.Model.Med;
import com.medical.medecinservice.Model.MedCnx;
import com.medical.medecinservice.Model.MedToken;
import com.medical.medecinservice.Model.Medecin;
import org.springframework.stereotype.Service;

@Service
public class ConvertionService
{

    public MedCnx convertToMedCnx(Medecin medecin)
    {
        if (medecin == null)
        {
            return null;
        }
        MedCnx medCnx=new MedCnx(
                medecin.getMatricule(),
                medecin.getPwd()
        );
        return medCnx;
    }

    public MedToken convertToMedToken(Medecin medecin)
    {
        if (medecin == null)
        {
            return null;
        }
        MedToken medToken=new MedToken(
                medecin.getMatricule(),
                medecin.getPwd(),
                medecin.getRole()
        );
        return medToken;
    }

    public Medecin convertToMedecin(Med med)
    {
        if(med==null)
        {
            return null;
        }
        Medecin medecin=new Medecin(
                med.getMatricule(),
                med.getNom(),
                med.getPrenom(),
                med.getEmail(),
                med.getTel(),
                med.getRole(),
                med.getPwd()
        );
        return medecin;
    }

    public Med convertToMed(Medecin medecin)
    {
        if(medecin==null)
        {
            return null;
        }
        Med med=new Med(
                medecin.getMatricule(),
                medecin.getNom(),
                medecin.getPrenom(),
                medecin.getEmail(),
                medecin.getTel(),
                medecin.getRole()
        );
        return med;
    }

}
