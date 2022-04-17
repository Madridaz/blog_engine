package ru.arkhipenkov.blogengine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.arkhipenkov.blogengine.service.CaptchaCodeService;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class ApiAuthController {

  private final CaptchaCodeService captchaCodeService;

  @GetMapping("captcha")
  public ResponseEntity<?> getCaptcha() {
    return ResponseEntity.ok(captchaCodeService.getCaptchaDto());
  }

}
