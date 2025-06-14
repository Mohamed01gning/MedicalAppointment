package com.medical.secretaireservice.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class QRCodeService
{
    public void generateQRCode(String matricule,int w,int h)
            throws WriterException, IOException
    {
        QRCodeWriter qrCodeWriter=new QRCodeWriter();
        BitMatrix bitMatrix=qrCodeWriter.encode(matricule, BarcodeFormat.QR_CODE,w,h);
        Path dest= Paths.get("secretaire-service/src/main/resources/ImageProfil/qrCode.png");
        MatrixToImageWriter.writeToPath(bitMatrix,"PNG",dest);
    }
}
