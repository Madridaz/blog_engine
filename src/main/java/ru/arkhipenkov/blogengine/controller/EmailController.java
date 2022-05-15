package ru.arkhipenkov.blogengine.controller;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
  private final JavaMailSender mailSender;

  @Autowired
  public EmailController(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @RequestMapping(value = "/send")
  public String send() {
// адрес который увидит получатель в поле ОТ:
    String from = "blogmanager2022@yandex.ru";
// адрес получателя, куда отправляем письмо (email пользователя)
    String to = "blogmanager2022@yandex.ru";

    SimpleMailMessage message = new SimpleMailMessage();

    message.setFrom(from);
    message.setTo(to);
    message.setSubject("Это тема письма");
    message.setText("Воу! А это текст письма! Это просто текст, без оформления и HTML!");

    mailSender.send(message);

    return "Письмо отправлено в " + LocalDateTime.now().toString();
  }
}
