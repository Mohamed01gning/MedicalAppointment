package com.medical.rvservice.Service;

import com.medical.rvservice.Model.Rv;
import com.medical.rvservice.Model.RvDTO;
import org.springframework.stereotype.Service;

@Service
public class ConversionService
{

    public Rv convertToRv(RvDTO rvDTO)
    {
        if (rvDTO==null)
        {
            return null;
        }
        Rv rv=new Rv(
                rvDTO.getNum(),
                rvDTO.getIdSec(),
                rvDTO.getIdMed(),
                rvDTO.getNomPatient(),
                rvDTO.getPrenomPatient(),
                rvDTO.getTel(),
                rvDTO.getDate(),
                rvDTO.getTime(),
                rvDTO.getDone()
        );
        return rv;
    }

    public RvDTO convertToRvDTO(Rv rv)
    {
        if (rv==null)
        {
            return null;
        }
        RvDTO rvDTO=new RvDTO(
                rv.getNum(),
                rv.getIdSec(),
                rv.getIdMed(),
                rv.getNomPatient(),
                rv.getPrenomPatient(),
                rv.getTel(),
                rv.getDate(),
                rv.getTime(),
                rv.getDone()
        );
        return rvDTO;
    }

}
