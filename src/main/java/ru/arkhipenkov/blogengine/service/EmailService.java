package ru.arkhipenkov.blogengine.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
  private final JavaMailSender mailSender;

  @Autowired
  public EmailService(JavaMailSender mailSender) {
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
