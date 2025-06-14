package com.medical.adminservice.Service;

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


    public void generateQRCode(String data,int width,int height,String destination)
    {
        QRCodeWriter qrCodeWriter=new QRCodeWriter();
        try
        {
            BitMatrix bitMatrix=qrCodeWriter.encode(data, BarcodeFormat.QR_CODE,width,height);
            Path dest= Paths.get(destination);
            MatrixToImageWriter.writeToPath(bitMatrix,"PNG",dest);
        }
        catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
