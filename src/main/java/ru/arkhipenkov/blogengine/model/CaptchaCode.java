package ru.arkhipenkov.blogengine.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "captcha_codes")
public class CaptchaCode {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private LocalDateTime time;

  @Column(nullable = false)
  private String code;

  @Column(name = "secret_code", nullable = false)
  private String secretCode;

  public CaptchaCode(LocalDateTime time, String code, String secretCode) {
    this.time = time;
    this.code = code;
    this.secretCode = secretCode;
  }
}
