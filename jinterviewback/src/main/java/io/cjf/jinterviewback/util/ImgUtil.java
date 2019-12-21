package io.cjf.jinterviewback.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImgUtil {

    public static byte[] redraw(InputStream originImgInputStream, int pixel) throws IOException {
        final BufferedImage originImage = ImageIO.read(originImgInputStream);
        final int width = originImage.getWidth();
        final int height = originImage.getHeight();
        BufferedImage newImage = originImage;
        if (width < height) {
            if (width > pixel) {
                double newHeight = 1.0 * pixel * height / width;
                newImage = new BufferedImage(pixel, (int) newHeight, originImage.getType());
                final Graphics2D graphics = newImage.createGraphics();
                graphics.drawImage(originImage, 0, 0, pixel, (int) newHeight, null);
                graphics.dispose();
            }
        } else {
            if (height > pixel) {
                double newWidth = 1.0 * pixel * width / height;
                newImage = new BufferedImage((int) newWidth, pixel, originImage.getType());
                final Graphics2D graphics = newImage.createGraphics();
                graphics.drawImage(originImage, 0, 0, (int) newWidth, pixel, null);
                graphics.dispose();
            }
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newImage, "jpg", baos);
        byte[] data = baos.toByteArray();

        return data;
    }
}
