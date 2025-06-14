package com.medical.medecinservice.Service;

import com.medical.medecinservice.Exception.MedecinNotFoundEx;
import com.medical.medecinservice.Exception.PwdException;
import com.medical.medecinservice.Model.*;
import com.medical.medecinservice.Repository.MedRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedService
{
    private String header;
    private String contentType;
    private final MedRepository medRepository;
    private final ConvertionService convertionService;
    private final PasswordEncoder paswwordEncoder;


    public MedService(MedRepository medRepository, ConvertionService convertionService, PasswordEncoder paswwordEncoder) {
        this.medRepository = medRepository;
        this.convertionService = convertionService;
        this.paswwordEncoder = paswwordEncoder;
    }

    // GETTERS AND SETTERS
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    // INSTANCE METHOD
    public void addPassword(MedPwd medPwd)
    {
        Med med=getMedByMat(medPwd.getMatricule());
        if (!medPwd.getPwd().equals(medPwd.getPwdConf()))
        {
            throw new PwdException("Vos Deux Mot De Pass Fournis Ne Sont Pas Identiques");
        }
        String pwdEncoded= paswwordEncoder.encode(medPwd.getPwdConf());
        med.setPwd(pwdEncoded);
        Medecin medecin= convertionService.convertToMedecin(med);
        medRepository.save(medecin);
    }

    public Resource getResource(Med med)
            throws IOException
    {
        String fileName=med.getMatricule()+".pdf";
        Path medPdfFile=Paths.get("medecin-service/src/main/resources/PdfProfil/"+med.getMatricule()+".pdf");
        Resource resource=new UrlResource(medPdfFile.toUri());
        if (!resource.exists() && !resource.isReadable())
        {
            throw new FileNotFoundException("Resource inexistente");
        }

        contentType= Files.probeContentType(resource.getFile().toPath());
        if (contentType==null)
        {
            contentType="application/octect-stream";
        }

        header="inline;filename=\""+fileName+"\"";

        return resource;
    }

    private String generateMatricule()
    {
        Boolean isMatExist=false;
        String matricule="";
        do
        {
            String num1=String.valueOf((int)(Math.random()*10000));
            String num2=String.valueOf((int)(Math.random()*1000));
            System.out.println("num1 : "+num1+" num2 : "+num2);
            String mat="MED-"+num1+"-"+num2;
            isMatExist=getAllMed().stream()
                    .anyMatch(med -> med.getMatricule().equals(mat));

            if (!isMatExist)
            {
                matricule=mat;
            }
            System.out.println("Matricule exite : "+isMatExist+" Matricule : "+matricule+" longeur : "+matricule.length());
        } while(isMatExist || matricule.length()!=12);

        return matricule;
    }

    public void addMedecin(Med med)
    {
        String matricule=generateMatricule();
        med.setMatricule(matricule);
        Medecin medecin= convertionService.convertToMedecin(med);
        medRepository.save(medecin);
    }

    public void addImage(MultipartFile image)
    {
        Path ImageProfil = Paths.get("med-service/src/main/resources/ImageProfil/");
    }

    public Med getMedByMat(String mat)
    {
        Medecin medecin=medRepository.findById(mat).orElse(null);
        if (medecin==null)
        {
            throw new MedecinNotFoundEx("Medecin inexistente");
        }
        Med med=convertionService.convertToMed(medecin);
        return med;
    }

    public MedToken getMedForAuthToken(String mat)
    {
        Medecin medecin=medRepository.findById(mat).orElse(null);
        MedToken medToken=convertionService.convertToMedToken(medecin);
        return medToken;
    }

    public MedCnx getMedForCnx(String mat)
    {
        Medecin medecin=medRepository.findById(mat).orElse(null);
        MedCnx medCnx=convertionService.convertToMedCnx(medecin);
        return medCnx;
    }

    public List<Med> getAllMed()
    {
        List<Med> meds=new ArrayList<>();
        medRepository.findAll().forEach(medecin -> {
            Med med= convertionService.convertToMed(medecin);
            meds.add(med);
        });
        return meds;
    }

    public void deleteMed(String mat)
    {
        getMedByMat(mat);
        medRepository.deleteById(mat);
    }



}
