package ru.arkhipenkov.blogengine.controller;

import java.time.LocalDateTime;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.arkhipenkov.blogengine.model.CaptchaCode;
import ru.arkhipenkov.blogengine.model.User;
import ru.arkhipenkov.blogengine.model.dto.ErrorsDto;
import ru.arkhipenkov.blogengine.model.dto.RegisterDto;
import ru.arkhipenkov.blogengine.model.dto.ResultTrueFalseDto;
import ru.arkhipenkov.blogengine.service.AuthService;
import ru.arkhipenkov.blogengine.service.CaptchaCodeService;
import ru.arkhipenkov.blogengine.service.UserService;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class ApiAuthController {

  private final CaptchaCodeService captchaCodeService;
  private final AuthService authService;
  private final UserService userService;
  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @GetMapping("captcha")
  public ResponseEntity<?> getCaptcha() {
    return ResponseEntity.ok(captchaCodeService.getCaptchaDto());
  }

  @PostMapping("register")
  public ResponseEntity<?> register(@RequestBody RegisterDto registerDto,
      HttpServletRequest request) {
    CaptchaCode captcha = captchaCodeService.findCaptchaByCodeAndSecretCode(
        registerDto.getCaptcha(), registerDto.getCaptchaSecret());

    Map<String, String> errors = authService.checkOnErrors(
        registerDto.getPassword(),
        captcha,
        userService.findUserByEmail(registerDto.getEmail()),
        new User(),
        registerDto.getName()
    );

    if (errors.size() > 0) {
      return ResponseEntity.ok(new ErrorsDto(false, errors));
    }

    User newUser = new User(
        (byte) 0,
        LocalDateTime.now(),
        registerDto.getName(),
        registerDto.getEmail(),
        passwordEncoder.encode(registerDto.getPassword()),
        null,
        String.format("http://%s:%s/%s", request.getServerName(), request.getServerPort(),
            "/src/main/resources/uploads/default-1.png")
    );
    userService.saveUser(newUser);

//Удаление капчи после использования
    captchaCodeService.deleteCaptcha(captcha);

    return ResponseEntity.ok(new ResultTrueFalseDto(true));
  }

}
