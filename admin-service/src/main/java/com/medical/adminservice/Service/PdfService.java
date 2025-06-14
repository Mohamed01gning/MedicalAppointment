package com.medical.adminservice.Service;


import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Image;
import com.medical.adminservice.Model.AdminDTO;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PdfService
{

    private String contentType;
    private String fileName;
    private final QRCodeService qrCodeService;

    public PdfService(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public Resource generatePdfFile(AdminDTO adminDTO, MultipartFile image)
            throws IOException
    {
        PdfReader model=new PdfReader("admin-service/src/main/resources/PdfProfil/Model.pdf");
        PdfWriter enroullement=new PdfWriter("admin-service/src/main/resources/PdfProfil/"+adminDTO.getMatricule()+".pdf");
        PdfDocument pdfDoc=new PdfDocument(model,enroullement);
        PdfAcroForm form=PdfAcroForm.getAcroForm(pdfDoc,true);
        insertPersonnalInfo(form,adminDTO);
        insertProfilImage(image,adminDTO,form,pdfDoc);
        insertQRCode(form,pdfDoc,adminDTO);

        form.flattenFields();
        pdfDoc.close();

        return getResource(adminDTO);
    }

    public Resource getResource(AdminDTO adminDTO) throws IOException
    {
        Path urlFile=Paths.get("admin-service/src/main/resources/PdfProfil/"+adminDTO.getMatricule()+".pdf");

        Resource resource=new UrlResource(urlFile.toUri());
        if (!resource.exists() || !resource.isReadable())
        {
            throw new FileNotFoundException("Ce fichier est inexistente");
        }
        this.contentType= Files.probeContentType(resource.getFile().toPath());
        if (contentType == null)
        {
            contentType="application/octet-stream";
        }
        this.fileName= adminDTO.getMatricule()+".pdf";
        return resource;
    }

    public void insertQRCode(PdfAcroForm form,PdfDocument pdfDoc,AdminDTO adminDTO)
    {
        String qrCodeUrl="admin-service/src/main/resources/ImageProfil/qrCode.png";
        PdfFormField qrCodeField=form.getField("qrCode");
        Rectangle rec=qrCodeField.getWidgets().get(0).getRectangle().toRectangle();
        qrCodeService.generateQRCode(adminDTO.getMatricule(),(int) rec.getWidth(),(int) rec.getHeight(),qrCodeUrl);
        try
        {
            ImageData imageData= ImageDataFactory.create(qrCodeUrl);
            Image image=new Image(imageData);
            image.setFixedPosition(rec.getLeft(),rec.getBottom());
            image.setWidth(rec.getWidth());image.setHeight(rec.getHeight());
            PdfCanvas pdfCanvas=new PdfCanvas(pdfDoc.getFirstPage());
            Canvas canvas=new Canvas(pdfCanvas,rec,true);
            canvas.add(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void insertPersonnalInfo(PdfAcroForm form,AdminDTO adminDTO)
    {
        form.getField("mat").setValue(adminDTO.getMatricule());
        form.getField("prenom").setValue(adminDTO.getPrenom());
        form.getField("nom").setValue(adminDTO.getNom());
        form.getField("email").setValue(adminDTO.getEmail());
        form.getField("tel").setValue(adminDTO.getTel());
        form.getField("role").setValue("ADMINISTRATEUR("+adminDTO.getRole()+")");
    }

    public void insertProfilImage(MultipartFile image,AdminDTO adminDTO,PdfAcroForm form,PdfDocument pdfDoc)
    {
        // CHARGEMENT ET COPY DE L'IMAGE
        Path imageProfil= Paths.get("admin-service/src/main/resources/ImageProfil/");
        String fileName= adminDTO.getMatricule()+".png";
        Path dest=imageProfil.resolve(fileName).normalize();
        try
        {
            image.transferTo(dest);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //-------------------------------------------------------------------------------
        // INSERTION DE L'IMAGE DANS LE FICHIER PDF
        String imageUrl="admin-service/src/main/resources/ImageProfil/"+adminDTO.getMatricule()+".png";
        PdfFormField imageField=form.getField("image");
        Rectangle rec=imageField.getWidgets().get(0).getRectangle().toRectangle();
        try
        {
            ImageData imageData= ImageDataFactory.create(imageUrl);
            Image im=new Image(imageData);
            im.setWidth(rec.getHeight()); im.setHeight(rec.getHeight());
            im.setFixedPosition(rec.getLeft(),rec.getBottom());
            PdfCanvas pdfCanvas=new PdfCanvas(pdfDoc.getFirstPage());
            Canvas canvas=new Canvas(pdfCanvas,rec,true);
            canvas.add(im);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


}
