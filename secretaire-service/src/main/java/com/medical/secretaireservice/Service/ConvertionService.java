package com.medical.secretaireservice.Service;

import com.medical.secretaireservice.Model.Sec;
import com.medical.secretaireservice.Model.SecCnx;
import com.medical.secretaireservice.Model.SecToken;
import com.medical.secretaireservice.Model.Secretaire;
import org.springframework.stereotype.Service;

@Service
public class ConvertionService
{

    public Secretaire convertToSecretaire(Sec sec)
    {
        if (sec == null)
        {
            return null;
        }
        Secretaire secretaire=new Secretaire(
                sec.getMatricule(),
                sec.getNom(),
                sec.getPrenom(),
                sec.getEmail(),
                sec.getTel(),
                sec.getRole(),
                sec.getPwd()
        );
        return secretaire;
    }

    public Sec convertToSec(Secretaire secretaire)
    {
        if (secretaire == null)
        {
            return null;
        }
        Sec sec=new Sec(
                secretaire.getMatricule(),
                secretaire.getNom(),
                secretaire.getPrenom(),
                secretaire.getEmail(),
                secretaire.getTel(),
                secretaire.getRole()
        );
        return sec;
    }

    public SecCnx convertToSecCnx(Secretaire secretaire)
    {
        if (secretaire==null)
        {
            return null;
        }
        SecCnx secCnx=new SecCnx(
                secretaire.getMatricule(),
                secretaire.getPwd()
        );
        return secCnx;
    }

    public SecToken convertToSecToken(Secretaire secretaire)
    {
        if (secretaire==null)
        {
            return null;
        }
        SecToken secToken=new SecToken(
                secretaire.getMatricule(),
                secretaire.getPwd(),
                secretaire.getRole()
        );
        return secToken;
    }
}
