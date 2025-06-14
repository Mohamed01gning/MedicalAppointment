package com.medical.secretaireservice.Service;

import com.medical.secretaireservice.Exception.PwdException;
import com.medical.secretaireservice.Exception.SecNotFoundEx;
import com.medical.secretaireservice.Model.*;
import com.medical.secretaireservice.Repository.SecRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class SecService
{
    private String header;
    private String contentType;
    private final PasswordEncoder passwordEncoder;
    private final SecRepository secRepository;
    private final ConvertionService convertionService;

    public SecService(PasswordEncoder passwordEncoder, SecRepository secRepository, ConvertionService convertionService) {
        this.passwordEncoder = passwordEncoder;
        this.secRepository = secRepository;
        this.convertionService = convertionService;
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
    public void addPassword(SecPwd secPwd)
    {
        Sec sec=getSecretaireByMat(secPwd.getMatricule());
        if (!secPwd.getPwd().equals(secPwd.getPwdConf()))
        {
            throw new PwdException("Vos Deux Mot De Pass Fournis Ne Sont Pas Identiques");
        }
        String pwdEncoded= passwordEncoder.encode(secPwd.getPwdConf());
        sec.setPwd(pwdEncoded);
        Secretaire secretaire= convertionService.convertToSecretaire(sec);
        secRepository.save(secretaire);
    }

    public Resource getResource(Sec sec)
            throws IOException
    {
        String fileName=sec.getMatricule()+".pdf";
        Path medPdfFile= Paths.get("secretaire-service/src/main/resources/PdfProfil/"+sec.getMatricule()+".pdf");
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
            String mat="SEC-"+num1+"-"+num2;
            isMatExist=getAllSecretaire().stream()
                    .anyMatch(sec -> sec.getMatricule().equals(mat));

            if (!isMatExist)
            {
                matricule=mat;
            }
            System.out.println("Matricule exite : "+isMatExist+" Matricule : "+matricule+" longeur : "+matricule.length());
        } while(isMatExist || matricule.length()!=12);

        return matricule;
    }

    public void addSecretaire(Sec sec)
    {
        String matricule=generateMatricule();
        sec.setMatricule(matricule);
        Secretaire secretaire= convertionService.convertToSecretaire(sec);
        secRepository.save(secretaire);
    }

    public Sec getSecretaireByMat(String mat)
    {
        Secretaire secretaire=secRepository.findById(mat).orElse(null);
        if (secretaire==null)
        {
            throw new SecNotFoundEx("Secretaire inexistente");
        }
        Sec sec= convertionService.convertToSec(secretaire);
        return sec;
    }

    public SecToken getSecretaireForAuthToken(String mat)
    {
        Secretaire secretaire=secRepository.findById(mat).orElse(null);
        SecToken secToken= convertionService.convertToSecToken(secretaire);
        return secToken;
    }

    public SecCnx getSecretaireForCnx(String mat)
    {
        Secretaire secretaire=secRepository.findById(mat).orElse(null);
        SecCnx secCnx= convertionService.convertToSecCnx(secretaire);
        return secCnx;
    }

    public List<Sec> getAllSecretaire()
    {
        List<Sec> secs=new ArrayList<>();
        secRepository.findAll().forEach(secretaire -> {
            Sec sec=convertionService.convertToSec(secretaire);
            secs.add(sec);
        });
        return secs;
    }

    public void deleteSecretaire(String mat)
    {
        getSecretaireByMat(mat);
        secRepository.deleteById(mat);
    }

}
