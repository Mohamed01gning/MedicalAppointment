package com.medical.secretaireservice.Service;

import com.google.zxing.WriterException;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Image;
import com.medical.secretaireservice.Model.Sec;
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
    private final QRCodeService qrCodeService;

    public PdfService(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    public void generatePdfFileFrofil(Sec sec, MultipartFile image) throws IOException {
        String modelUrl="secretaire-service/src/main/resources/PdfProfil/Model.pdf";
        String destUrl="secretaire-service/src/main/resources/PdfProfil/"+sec.getMatricule()+".pdf";
        PdfReader model=new PdfReader(modelUrl);
        PdfWriter dest=new PdfWriter(destUrl);
        PdfDocument pdfDoc=new PdfDocument(model,dest);
        PdfAcroForm form=PdfAcroForm.getAcroForm(pdfDoc,true);

        insertPersonnalInfo(sec,form);
        insertProfilImage(sec,image,form);
        try {
            qrCodeService.generateQRCode(sec.getMatricule(),300,300);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        insertQRCode(form);

        form.flattenFields();
        pdfDoc.close();
    }


    private  void insertPersonnalInfo(Sec sec,PdfAcroForm form)
    {
        form.getField("mat").setValue(sec.getMatricule());
        form.getField("prenom").setValue(sec.getPrenom());
        form.getField("nom").setValue(sec.getNom());
        form.getField("email").setValue(sec.getEmail());
        form.getField("tel").setValue(sec.getTel());
        form.getField("role").setValue("SECRETAIRE");
    }

    private void insertQRCode(PdfAcroForm form)
    {
        String qrCodeUrl= "secretaire-service/src/main/resources/ImageProfil/qrCode.png";
        Rectangle rec=form.getField("qrCode").getWidgets().get(0).getRectangle().toRectangle();
        try
        {
            Image image=new Image(ImageDataFactory.create(qrCodeUrl));
            image.setFixedPosition(rec.getLeft(),rec.getBottom());
            image.setWidth(rec.getWidth());
            image.setHeight(rec.getHeight());

            PdfCanvas pdfCanvas=new PdfCanvas(form.getPdfDocument().getFirstPage(), true);
            Canvas canvas=new Canvas(pdfCanvas,rec,true);
            canvas.add(image);
            form.removeField("qrCode");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    private void insertProfilImage(Sec sec, MultipartFile image, PdfAcroForm form)
            throws FileNotFoundException, MalformedURLException {
        //INSERTION DE L'IMAGE DANS LE DOSSIER 'ImageProfil'
        Path ImageProfil = Paths.get("secretaire-service/src/main/resources/ImageProfil/");
        if (!Files.exists(ImageProfil))
        {
            throw new FileNotFoundException("Dossier inexistente");
        }

        Path dest=ImageProfil.resolve(sec.getMatricule()+".png").normalize();
        try
        {
            image.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //INSERTION DE L'IMAGE DANS LE PDF
        String imageUrl="secretaire-service/src/main/resources/ImageProfil/"+sec.getMatricule()+".png";
        PdfFormField imageField=form.getField("image");
        Rectangle rec=imageField.getWidgets().get(0).getRectangle().toRectangle();
        Image im=new Image(ImageDataFactory.create(imageUrl));
        im.setWidth(rec.getWidth()); im.setHeight(rec.getHeight());
        im.setFixedPosition(rec.getLeft(),rec.getBottom());

        PdfCanvas pdfCanvas=new PdfCanvas(form.getPdfDocument().getFirstPage(), true);
        Canvas canvas =new Canvas(pdfCanvas,rec,true);
        canvas.add(im);
    }

}
