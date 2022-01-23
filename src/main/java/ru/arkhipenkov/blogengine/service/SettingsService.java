package ru.arkhipenkov.blogengine.service;

import org.springframework.stereotype.Service;
import ru.arkhipenkov.blogengine.api.response.SettingsResponse;

@Service
public class SettingsService {

  public SettingsResponse getGlobalSettings() {
    SettingsResponse settingsResponse = new SettingsResponse();
    settingsResponse.setMultiuserMode(false);
    settingsResponse.setPostPremoderation(true);
    settingsResponse.setStatisticIsPublic(true);
    return settingsResponse;
  }
}
