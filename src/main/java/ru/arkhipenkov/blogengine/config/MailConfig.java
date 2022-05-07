package ru.arkhipenkov.blogengine.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

  @Value("${mail.host}")
  private String host;

  @Value("${mail.username}")
  private String username;

  @Value("${mail.password}")
  private String password;

  @Value("${mail.port}")
  private int port;

  @Value("${mail.protocol}")
  private String protocol;

  @Value("${mail.debug}")
  private String debug;

  @Bean
  public JavaMailSender javaMailSender(){

    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(host);
    mailSender.setPort(port);
    mailSender.setUsername(username);
    mailSender.setPassword(password);

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.smtp.starttls.enable", "true");

    return mailSender;
  }
}
