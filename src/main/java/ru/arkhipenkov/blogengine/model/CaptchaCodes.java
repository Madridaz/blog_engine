package ru.arkhipenkov.blogengine.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "captcha_codes")
@Data
@NoArgsConstructor(force = true)
public class CaptchaCodes {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  //дата и время генерации кода капчи
  @NotNull
  @Column(name = "time")
  private Timestamp time;

  //код на картинке капчи
  @NotBlank
  @Column(columnDefinition = "TINYTEXT")
  private String code;

  //код, передаваемый в параметре
  @NotBlank
  @Column(name = "secret_code", columnDefinition = "TINYTEXT")
  private String secretCode;


}
