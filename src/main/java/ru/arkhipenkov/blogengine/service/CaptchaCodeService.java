package ru.arkhipenkov.blogengine.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.imageio.ImageIO;
import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.arkhipenkov.blogengine.model.CaptchaCode;
import ru.arkhipenkov.blogengine.model.dto.CaptchaDto;
import ru.arkhipenkov.blogengine.repository.CaptchaCodesRepository;

@Service
@AllArgsConstructor
public class CaptchaCodeService {

  private final CaptchaCodesRepository captchaCodesRepository;

  public CaptchaCode findCaptchaByCodeAndSecretCode(String code, String secretCode) {
    return captchaCodesRepository.findByCodeAndSecretCode(code, secretCode).orElse(null);
  }

  public void deleteCaptcha(CaptchaCode captchaCode) {
    captchaCodesRepository.delete(captchaCode);
  }

  public CaptchaDto getCaptchaDto() {
    captchaCodesRepository.deleteOld();

    String codeNumber = RandomStringUtils.random(4, false, true);
    String codeSecret = RandomStringUtils.randomAlphanumeric(22).toLowerCase();
    String imageBase64Encoded = getImageBase64(codeNumber, 20);

    captchaCodesRepository.save(new CaptchaCode(LocalDateTime.now(), codeNumber, codeSecret));

    return new CaptchaDto(codeSecret, imageBase64Encoded);
  }

  private String getImageBase64(String code, int codeSize) {
    BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = img.createGraphics();
    Font font = new Font("Times New Roman", Font.PLAIN, codeSize);
    g2d.setFont(font);
    FontMetrics fm = g2d.getFontMetrics();
    int width = fm.stringWidth(code);
    int height = fm.getHeight();
    g2d.dispose();

    img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    g2d = img.createGraphics();

    g2d.setPaint(Color.WHITE);
    g2d.fillRect(0, 0, width, height);

    g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
    g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

    g2d.setFont(font);
    fm = g2d.getFontMetrics();
    g2d.setColor(Color.BLACK);

    // Draw code...
    g2d.drawString(code, 0, fm.getAscent());

    g2d.dispose();

    String base64EncodedImage = "";

    try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
      ImageIO.write(img, "png", os);

      base64EncodedImage = "data:image/png;charset=utf-8;base64, " +
          java.util.Base64.getEncoder().encodeToString(os.toByteArray());

    } catch (IOException e) {
      e.printStackTrace();
    }

    return base64EncodedImage;
  }
}
