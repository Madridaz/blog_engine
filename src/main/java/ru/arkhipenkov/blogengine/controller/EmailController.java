package ru.arkhipenkov.blogengine.controller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
  private final JavaMailSender mailSender;

  @Autowired
  public EmailController(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void send(String mailTo, String subject, String text) throws MessagingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();

    MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    message.setFrom("blogmanager2022@yandex.ru");
    message.setTo(mailTo);
    message.setSubject(subject);
    message.setText(text, true);

    mailSender.send(mimeMessage);
  }
}
