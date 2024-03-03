package dev.aminnorouzi.qrguard.service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import dev.aminnorouzi.qrguard.util.CameraUtil;
import dev.aminnorouzi.qrguard.util.DependencyInjector;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QrCodeService {

    private final CameraUtil util = DependencyInjector.getInstance(CameraUtil.class);

    public BufferedImage generate(String email) {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        try {
            QRCodeWriter barcodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = barcodeWriter.encode(email, BarcodeFormat.QR_CODE, 200, 200, hints);

            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(BufferedImage image) {
        try {
            String format = "png";
            String name = UUID.randomUUID().toString();
            String path = System.getProperty("user.home") + "/Desktop/";

            File output = new File(path + name + "." + format);
            ImageIO.write(image, format, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Result detect(Mat frame) {
        BufferedImage image = util.matToBufferedImage(frame);
        return decode(image);
    }

    public Result decode(BufferedImage img) {
        try {
            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(img);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            return new MultiFormatReader().decode(bitmap);
        } catch (NotFoundException e) {
            return null;
        }
    }
}
