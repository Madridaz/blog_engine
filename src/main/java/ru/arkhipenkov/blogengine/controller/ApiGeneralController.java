package ru.arkhipenkov.blogengine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.arkhipenkov.blogengine.api.response.CheckResponse;
import ru.arkhipenkov.blogengine.api.response.InitResponse;
import ru.arkhipenkov.blogengine.api.response.SettingsResponse;
import ru.arkhipenkov.blogengine.service.CheckService;
import ru.arkhipenkov.blogengine.service.SettingsService;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {

  private final SettingsService settingsService;
  private final InitResponse initResponse;
  private final CheckService checkService;

  public ApiGeneralController(SettingsService settingsService,
      InitResponse initResponse, CheckService checkService) {
    this.settingsService = settingsService;
    this.initResponse = initResponse;
    this.checkService = checkService;
  }

  @GetMapping("/settings")
  private SettingsResponse settings() {
    return settingsService.getGlobalSettings();
  }

  @GetMapping("/init")
  private InitResponse init() {
    return initResponse;
  }

  @GetMapping("/auth/check")
  private CheckResponse checkResponse() {
    return checkService.check();
  }
}
