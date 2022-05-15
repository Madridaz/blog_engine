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
import ru.arkhipenkov.blogengine.model.dto.AuthUserDto;
import ru.arkhipenkov.blogengine.model.dto.AuthUserInfoDto;
import ru.arkhipenkov.blogengine.model.dto.ErrorsDto;
import ru.arkhipenkov.blogengine.model.dto.LoginDto;
import ru.arkhipenkov.blogengine.model.dto.PasswordRestoreDto;
import ru.arkhipenkov.blogengine.model.dto.RegisterDto;
import ru.arkhipenkov.blogengine.model.dto.ResultTrueFalseDto;
import ru.arkhipenkov.blogengine.service.AuthService;
import ru.arkhipenkov.blogengine.service.CaptchaCodeService;
import ru.arkhipenkov.blogengine.service.PostService;
import ru.arkhipenkov.blogengine.service.UserService;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class ApiAuthController {

  private final CaptchaCodeService captchaCodeService;
  private final AuthService authService;
  private final UserService userService;
  private final PostService postService;
  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  //private final EmailService emailService;

  @GetMapping("captcha")
  public ResponseEntity<?> getCaptcha() {
    return ResponseEntity.ok(captchaCodeService.getCaptchaDto());
  }

  @PostMapping("login")
  public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

    User userFromDB = userService.findUserByEmail(loginDto.getEmail());

    if (userFromDB != null && passwordEncoder.matches(loginDto.getPassword(),
        userFromDB.getPassword())) {
      authService.saveSession(userFromDB.getId());
      return getAuthUserResponseEntityDto(userFromDB);
    }
    return ResponseEntity.ok(new ResultTrueFalseDto(false));
  }

  @GetMapping("check")
  public ResponseEntity<?> check() {

    if (authService.checkAuthorization()) {
      User userFromDB = userService.findUserById(authService.getUserIdBySession());
      return getAuthUserResponseEntityDto(userFromDB);
    }
    return ResponseEntity.ok(new ResultTrueFalseDto(false));
  }

  @GetMapping("logout")
  public ResponseEntity<ResultTrueFalseDto> logout() {

    authService.logout();

    return ResponseEntity.ok(new ResultTrueFalseDto(true));
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

//  @PostMapping("restore")
//  public ResponseEntity<?> restore(@RequestBody RestoreDto restoreDto) {
//    return recoverPassword(restoreDto.getEmail());
//  }


  @PostMapping("password")
  public ResponseEntity<?> password(@RequestBody PasswordRestoreDto passwordRestoreDto) {
    User existUser = userService.findUserByRecoverCode(passwordRestoreDto.getCode());
    CaptchaCode captcha = captchaCodeService.findCaptchaByCodeAndSecretCode(
        passwordRestoreDto.getCaptcha(), passwordRestoreDto.getCaptchaSecret());

    Map<String, String> errors = authService.checkOnErrors(
        passwordRestoreDto.getPassword(),
        captcha,
        null,
        existUser,
        null
    );

    if (errors.size() > 0) {
      return ResponseEntity.ok(new ErrorsDto(false, errors));
    }

    existUser.setCode("");
    existUser.setPassword(passwordEncoder.encode(passwordRestoreDto.getPassword()));
    userService.saveUser(existUser);

    captchaCodeService.deleteCaptcha(captcha);

    return ResponseEntity.ok(new ResultTrueFalseDto(true));
  }

//  private ResponseEntity<?> recoverPassword(String email) {
//    User user = userService.findUserByEmail(email);
//
//    if (user == null) {
//      return ResponseEntity.ok(new ResultTrueFalseDto(false));
//    }
//
//    String token = RandomStringUtils.randomAlphanumeric(45).toLowerCase();
//
//    user.setCode(token);
//    userService.saveUser(user);
//
//    String link = "http://localhost:8080/login/change-password/" + token;
//    String message = "<a href=\"" + link + "\">Восстановить пароль</a>";
//    emailService.send(email, "Восстановление пароля", message);
//
//    return ResponseEntity.ok(new ResultTrueFalseDto(true));
//  }

  private ResponseEntity<?> getAuthUserResponseEntityDto(User userFromDB) {
    Integer moderationCount = null;

    if (userFromDB.getIsModerator() == 1) {
      moderationCount = postService.countPostsNeedModeration();
      return getAuthUserDto(userFromDB, true, true, moderationCount);
    }
    return getAuthUserDto(userFromDB, false, false, moderationCount);
  }

  private ResponseEntity<AuthUserDto> getAuthUserDto
      (User userFromDB, Boolean isModerator, Boolean settings, Integer moderationCount) {

    AuthUserInfoDto userInfoDto = new AuthUserInfoDto(
        userFromDB.getId(),
        userFromDB.getName(),
        userFromDB.getPhoto(),
        userFromDB.getEmail(),
        isModerator,
        moderationCount,
        settings);

    return ResponseEntity.ok(new AuthUserDto(true, userInfoDto));
  }

}
