package ru.arkhipenkov.blogengine.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordRestoreDto {

  private String code;

  private String password;

  private String captcha;

  @JsonProperty("captcha_secret")
  private String captchaSecret;
}
