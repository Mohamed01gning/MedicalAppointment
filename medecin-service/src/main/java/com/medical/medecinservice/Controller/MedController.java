package com.medical.medecinservice.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medical.medecinservice.Exception.AccessDeniedEx;
import com.medical.medecinservice.Exception.MedecinNotFoundEx;
import com.medical.medecinservice.Exception.MedValidationException;
import com.medical.medecinservice.Model.*;
import com.medical.medecinservice.Service.MedService;
import com.medical.medecinservice.Service.PdfService;
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
@RequestMapping("/med")
public class MedController
{
    private final Validator validator;
    private final PdfService pdfService;
    private final MedService medService;

    public MedController(Validator validator, PdfService pdfService, MedService medService) {
        this.validator = validator;
        this.pdfService = pdfService;
        this.medService = medService;
    }

    @GetMapping("/profil/{mat}")
    public ResponseEntity<Resource> getProfilImage(@PathVariable String mat)
            throws IOException {
        Path profilPath= Paths.get("medecin-service/src/main/resources/ImageProfil/"+mat+".png");
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
            @RequestBody MedPwd medPwd)
    {
        medService.addPassword(medPwd);
        Map<String,String> msg=new HashMap<>();
        msg.put("msg","Mot de Pass Defini avec Success");
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_ALL')")
    public ResponseEntity<Resource> addMedecin(
            @Validated(ValidationGroup.groupSequence.class)
            @RequestPart("med") String medJson,
            @RequestPart(value = "profil",required = true) MultipartFile image
            )
            throws IOException
    {
        if(image.isEmpty())
        {
            throw new MedecinNotFoundEx("Veillez inserer une image de profil ");
        }

        ObjectMapper mapper = new ObjectMapper();
        Med med= mapper.readValue(medJson, Med.class);
        Set<ConstraintViolation<Med>> violations=validator.validate(med,ValidationGroup.groupSequence.class);
        if (!violations.isEmpty())
        {
            Map<String,String> errors=new HashMap<>();
            violations.forEach(violation ->
                    errors.put(violation.getPropertyPath().toString(),violation.getMessage())
            );
            throw new MedValidationException(errors);
        }

        medService.addMedecin(med);
        Map<String,String> msg=new HashMap<>();
        msg.put("msg","Medecin ajouté avec success");
        pdfService.generatePdfFileFrofil(med,image);
        Resource resource= medService.getResource(med);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(medService.getContentType()))
               // .header(HttpHeaders.CONTENT_DISPOSITION, medService.getHeader())
                .body(resource);
    }

    @GetMapping("/service/token/{mat}")
    public ResponseEntity<MedToken> getMedecinByMatforToken(@PathVariable String mat)
    {
        MedToken medToken= medService.getMedForAuthToken(mat);
        return new ResponseEntity<>(medToken,HttpStatus.OK);
    }

    @GetMapping("/medMat/{mat}")
    public ResponseEntity<Med> getMedecinByMat(@PathVariable String mat)
    {
        Med med= medService.getMedByMat(mat);
        return new ResponseEntity<>(med,HttpStatus.OK);
    }

    @GetMapping("/service/{mat}")
    @PreAuthorize("hasAuthority('ROLE_SERVICE')")
    public ResponseEntity<MedCnx> getMedecinByMatforCnx(@PathVariable String mat)
    {
        MedCnx medCnx= medService.getMedForCnx(mat);
        return new ResponseEntity<>(medCnx,HttpStatus.OK);
    }

    @GetMapping("/getMedecins")
    @PreAuthorize("hasAuthority('ROLE_ALL') or hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SERVICE')")
    public ResponseEntity<List<Med>> getAllMedecin()
    {
        List<Med> meds=medService.getAllMed();
        return new ResponseEntity<>(meds,HttpStatus.OK);
    }

    @DeleteMapping("/remove/{matricule}")
    public ResponseEntity<Map<String,String>> removeMedecin(@PathVariable String matricule)
    {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        UserDetails admin= (UserDetails) auth.getPrincipal();
        boolean hasAuth=admin.getAuthorities().stream()
                .anyMatch(role -> role.equals("ROLE_ALL"));

        if (!hasAuth)
        {
            throw new AccessDeniedEx("Votre Role Ne Vous Permet Pas D'effectuer Cette Operation");
        }
        medService.deleteMed(matricule);
        Map<String,String> msg=new HashMap<>();
        msg.put("msg","Medecin supprimé avec success ");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

}
