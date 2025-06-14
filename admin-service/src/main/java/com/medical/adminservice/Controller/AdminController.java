package com.medical.adminservice.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.medical.adminservice.Exception.AdminNotFoundEx;
import com.medical.adminservice.Exception.AdminValidationException;
import com.medical.adminservice.Model.*;
import com.medical.adminservice.Service.AdminService;
import com.medical.adminservice.Service.PdfService;
import com.medical.adminservice.Service.QRCodeService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/admin")
public class AdminController
{
    private final Validator validator;
    private final QRCodeService qrCodeService;
    private final AdminService adminService;
    private final PdfService pdfService;

    public AdminController(Validator validator, QRCodeService qrCodeService, AdminService adminService, PdfService pdfService) {
        this.validator = validator;
        this.qrCodeService = qrCodeService;
        this.adminService = adminService;
        this.pdfService = pdfService;
    }

    @GetMapping("/profil/{mat}")
    public ResponseEntity<Resource> getProfilImage(@PathVariable String mat)
            throws IOException {
        Path profilPath= Paths.get("admin-service/src/main/resources/ImageProfil/"+mat+".png");
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
    public ResponseEntity<Map<String,String>> setPassword(
            @Validated(ValidationGroup.groupSequence.class)
            @RequestBody AdminPwd adminPwd
            )
    {
        adminService.setPassword(adminPwd);
        Map<String,String> message=new HashMap<>();
        message.put("msg","Password defini avec success");
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ALL')")
    public ResponseEntity<Resource> addAdmin(
            @RequestPart("admin") String adminJson,
            @RequestPart(value = "profil",required = true) MultipartFile image
    ) throws IOException
    {
        if(image.isEmpty() || image==null)
        {
            throw new AdminNotFoundEx("Veillez inserer une image de profil ");
        }

        ObjectMapper mapper = new ObjectMapper();
        AdminDTO adminDTO = mapper.readValue(adminJson, AdminDTO.class);
        Set<ConstraintViolation<AdminDTO>> violations=validator.validate(adminDTO,ValidationGroup.groupSequence.class);
        if (!violations.isEmpty())
        {
            Map<String,String> errors=new HashMap<>();
            violations.forEach(violation ->
                    errors.put(violation.getPropertyPath().toString(),violation.getMessage())
            );
            throw new AdminValidationException(errors);
        }

        String matricule= adminService.generateMatricule();
        System.out.println("Matricule : "+matricule);
        adminDTO.setMatricule(matricule);
        adminService.addAdmin(adminDTO);
        Resource resource=pdfService.generatePdfFile(adminDTO,image);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(pdfService.getContentType()))
                //.header(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=\""+pdfService.getFileName()+"\"")
                .body(resource);
    }

    @GetMapping("/matAdmin/{mat}")
    public ResponseEntity<AdminDTO> getAdminByMat(@PathVariable String mat)
    {
        AdminDTO adminDTO= adminService.getAdminByMatricule(mat);
        return new ResponseEntity<>(adminDTO,HttpStatus.OK);
    }

    @GetMapping("/service/token/{mat}")
    @PreAuthorize("hasAuthority('ROLE_SERVICE')")
    public ResponseEntity<AdminToken> getAdminByMatForFilerToken(@PathVariable String mat)
    {
        AdminToken adminToken= adminService.getAdminByMatForFilterToken(mat);
        return new ResponseEntity<>(adminToken,HttpStatus.OK);
    }

    @GetMapping("/service/{mat}")
    @PreAuthorize("hasAuthority('ROLE_SERVICE')")
    public ResponseEntity<AdminCnx> getAdminByMatForCnx(@PathVariable String mat)
    {
        AdminCnx adminCnx= adminService.getAdminByMatForCnx(mat);
        return new ResponseEntity<>(adminCnx,HttpStatus.OK);
    }

    @GetMapping("/getAdmins")
    @PreAuthorize("hasAuthority('ROLE_ALL') or hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SERVICE')")
    public ResponseEntity<List<AdminDTO>> getAllAdmin()
    {
        List<AdminDTO> adminDTOs=adminService.getAllAdmin();
        return new ResponseEntity<>(adminDTOs,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{mat}")
    @PreAuthorize("hasAuthority('ROLE_ALL')")
    public ResponseEntity<Map<String,String>> deleteAdmin(@PathVariable String mat)
    {
        adminService.deleteAdmin(mat);
        Map<String,String> message=new HashMap<>();
        message.put("msg","Utilisateur supprimé avec success");
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

}
