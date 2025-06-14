package com.medical.secretaireservice.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medical.secretaireservice.Exception.AccessDeniedEx;
import com.medical.secretaireservice.Exception.SecNotFoundEx;
import com.medical.secretaireservice.Exception.SecValidationEx;
import com.medical.secretaireservice.Model.*;
import com.medical.secretaireservice.Service.ConvertionService;
import com.medical.secretaireservice.Service.PdfService;
import com.medical.secretaireservice.Service.SecService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/sec")
public class SecController
{
    private final Validator validator;
    private final PdfService pdfService;
    private final SecService secService;
    private final ConvertionService convertionService;

    public SecController(Validator validator, PdfService pdfService, SecService secService, ConvertionService convertionService) {
        this.validator = validator;
        this.pdfService = pdfService;
        this.secService = secService;
        this.convertionService = convertionService;
    }

    @GetMapping("/profil/{mat}")
    public ResponseEntity<Resource> getProfilImage(@PathVariable String mat)
            throws IOException {
        Path profilPath= Paths.get("secretaire-service/src/main/resources/ImageProfil/"+mat+".png");
        Resource resource=new UrlResource(profilPath.toUri());
        if (!resource.exists() && !resource.isReadable())
        {
            throw new FileNotFoundException("image inexistente");
        }

        String contentType= Files.probeContentType(resource.getFile().toPath());
        if (contentType==null)
        {
            contentType="application/octect-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline;filename=\""+resource.getFilename()+"\"")
                .body(resource);
    }

    @PostMapping("/addPwd")
    public ResponseEntity<Map<String,String>> addPassword(
            @Validated(ValidationGroup.groupSequence.class)
            @RequestBody SecPwd secPwd)
    {
        secService.addPassword(secPwd);
        Map<String,String> msg=new HashMap<>();
        msg.put("msg","Mot de Pass Defini avec Success");
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ALL') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Resource> addSecretaire(
            @Validated(ValidationGroup.groupSequence.class)
            @RequestPart("sec") String secJson,
            @RequestPart(value = "profil",required = true)MultipartFile image
            )
            throws IOException
    {
        if(image.isEmpty())
        {
            throw new SecNotFoundEx("Veillez inserer une image de profil ");
        }

        ObjectMapper mapper = new ObjectMapper();
        Sec sec= mapper.readValue(secJson, Sec.class);
        Set<ConstraintViolation<Sec>> violations=validator.validate(sec,ValidationGroup.groupSequence.class);
        if (!violations.isEmpty())
        {
            Map<String,String> errors=new HashMap<>();
            violations.forEach(violation ->{
                errors.put(violation.getPropertyPath().toString(),violation.getMessage());
            });
            errors.forEach((k,v) -> System.out.println(k+" : "+v));
            throw new SecValidationEx(errors);
        }

        secService.addSecretaire(sec);
        Map<String,String> msg=new HashMap<>();
        msg.put("msg","Secretaire ajouté avec success");
        pdfService.generatePdfFileFrofil(sec,image);
        Resource resource= secService.getResource(sec);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(secService.getContentType()))
               // .header(HttpHeaders.CONTENT_DISPOSITION, secService.getHeader())
                .body(resource);
    }

    @GetMapping("/matSec/{mat}")
    public ResponseEntity<Sec> getSecretaire(@PathVariable String mat)
    {

        Sec sec= secService.getSecretaireByMat(mat);
        if (sec==null)
        {
            throw new SecNotFoundEx("Secretaire inexistente");
        }
        return new ResponseEntity<>(sec,HttpStatus.OK);
    }

    @GetMapping("/service/token/{mat}")
    public ResponseEntity<SecToken> getSecretaireToken(@PathVariable String mat)
    {

        SecToken secToken= secService.getSecretaireForAuthToken(mat);
        return new ResponseEntity<>(secToken,HttpStatus.OK);
    }

    @GetMapping("/service/{mat}")
    @PreAuthorize("hasAuthority('ROLE_SERVICE')")
    public ResponseEntity<SecCnx> getSecretaireCnx(@PathVariable String mat)
    {

        SecCnx secCnx= secService.getSecretaireForCnx(mat);
        return new ResponseEntity<>(secCnx,HttpStatus.OK);
    }

    @GetMapping("/getSec")
    @PreAuthorize("hasAuthority('ROLE_ALL') or hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SERVICE')")
    public ResponseEntity<List<Sec>> getAllSecretaire()
    {
        System.out.println("/getSec ---> ");
        List<Sec> secs=secService.getAllSecretaire();
        return new ResponseEntity<>(secs,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{mat}")
    @PreAuthorize("hasAuthority('ROLE_ALL')")
    public ResponseEntity<Map<String,String>> deleteSecretaire(@PathVariable String mat)
    {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        UserDetails admin= (UserDetails) auth.getPrincipal();
        boolean hasAuth=admin.getAuthorities()
                .stream()
                .anyMatch(role -> role.equals("ROLE_ALL"));
        if (!hasAuth)
        {
            throw new AccessDeniedEx("Votre Role Ne Vous Permet Pas D'effectuer Cette Operation");
        }

        getSecretaire(mat);
        secService.deleteSecretaire(mat);
        Map<String,String> msg=new HashMap<>();
        msg.put("msg","Secretaire supprimé avec success");
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }
}
