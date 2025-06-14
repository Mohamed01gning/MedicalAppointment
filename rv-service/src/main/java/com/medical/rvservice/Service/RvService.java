package com.medical.rvservice.Service;

import com.medical.rvservice.Model.Rv;
import com.medical.rvservice.Model.RvDTO;
import com.medical.rvservice.Repository.RvRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RvService
{
    private final RvRepository rvRepository;
    private final ConversionService conversionService;


    public RvService(RvRepository rvRepository, ConversionService conversionService) {
        this.rvRepository = rvRepository;
        this.conversionService = conversionService;
    }

    private String generateRvNum()
    {
        Boolean isMatExist=false;
        String numRv="";
        do
        {
            String num1=String.valueOf((int)(Math.random()*10000));
            String num2=String.valueOf((int)(Math.random()*1000));
            System.out.println("num1 : "+num1+" num2 : "+num2);
            String num="RV-"+num1+"-"+num2;
            isMatExist=getAllRv().stream()
                    .anyMatch(rv -> rv.getNum().equals(num));

            if (!isMatExist)
            {
                numRv=num;
            }
            System.out.println("Num existe : "+isMatExist+" Num : "+numRv+" longeur : "+numRv.length());
        } while(isMatExist || numRv.length()!=11);

        return numRv;
    }

    public void addRv(RvDTO rvDTO)
    {
        String num=generateRvNum();
        rvDTO.setNum(num);
        rvDTO.setDone("close");
        Rv rv= conversionService.convertToRv(rvDTO);
        rvRepository.save(rv);
    }

    public void updateRv(RvDTO rvDTO)
    {
        Rv rv= conversionService.convertToRv(rvDTO);
        rvRepository.save(rv);
    }

    public List<RvDTO> getAllRv()
    {
        List<Rv> rv=rvRepository.findAll();
        List<RvDTO> rvDTOs=new ArrayList<>();
        rv.forEach(rv1 -> {
            rvDTOs.add(conversionService.convertToRvDTO(rv1));
        });
        return rvDTOs;
    }

    public List<RvDTO> getRvByIdSec(String idSec)
    {

        List<RvDTO> rvDTOs=getAllRv();
        List<RvDTO> rv=rvDTOs.stream()
                .filter(rvDTO -> rvDTO.getIdSec().equals(idSec))
                .collect(Collectors.toList());
        return rv;
    }

    public List<RvDTO> getRvByIdMed(String idMed)
    {
        List<RvDTO> rvDTOs=getAllRv();
        List<RvDTO> rv=rvDTOs.stream()
                .filter(rvDTO -> rvDTO.getIdMed().equals(idMed))
                .collect(Collectors.toList());
        return rv;
    }

    public List<RvDTO> getRvByDate(LocalDate date)
    {
        List<RvDTO> rvDTOs=getAllRv();
        List<RvDTO> rv=rvDTOs.stream()
                .filter(rvDTO -> rvDTO.getDate().equals(date))
                .collect(Collectors.toList());
        return rv;
    }

    public List<RvDTO> getTodayRv()
    {
        List<RvDTO> rvDTOs=getAllRv();
        List<RvDTO> rv=rvDTOs.stream()
                .filter(rvDTO -> rvDTO.getDate().equals(LocalDate.now()))
                .collect(Collectors.toList());
        return rv;
    }

    public List<RvDTO> getTodayRvByIdMed(String idMed)
    {
        List<RvDTO> rvDTOs=getAllRv();
        List<RvDTO> rv=rvDTOs.stream()
                .filter(rvDTO ->
                        rvDTO.getDate().equals(LocalDate.now()) && rvDTO.getIdMed().equals(idMed)
                )
                .collect(Collectors.toList());
        return rv;
    }

    public List<RvDTO> getTodayRvByIdSec(String idSec)
    {
        List<RvDTO> rvDTOs=getAllRv();
        List<RvDTO> rv=rvDTOs.stream()
                .filter(rvDTO ->
                        rvDTO.getDate().equals(LocalDate.now()) && rvDTO.getIdSec().equals(idSec)
                )
                .collect(Collectors.toList());
        return rv;
    }

}
