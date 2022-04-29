package ru.arkhipenkov.blogengine.service;

import java.util.List;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.arkhipenkov.blogengine.enums.GlobalSettingsEnum;
import ru.arkhipenkov.blogengine.model.GlobalSettings;
import ru.arkhipenkov.blogengine.repository.GlobalSettingsRepository;

@Service
@AllArgsConstructor
public class GlobalSettingsService {

  private final GlobalSettingsRepository globalSettingsRepository;

  @PostConstruct
  public void init() {
    List<GlobalSettings> exist = globalSettingsRepository.findAll();

    if (exist.size() == 0) {
      exist.add(new GlobalSettings(
          GlobalSettingsEnum.MULTIUSER_MODE,
          "Многопользовательский режим",
          "YES"
      ));
      exist.add(new GlobalSettings(
          GlobalSettingsEnum.POST_PREMODERATION,
          "Премодерация постов",
          "YES"
      ));
      exist.add(new GlobalSettings(
          GlobalSettingsEnum.STATISTICS_IS_PUBLIC,
          "Показывать всем статистику блога",
          "YES"
      ));

      saveSettings(exist);
    }
  }

  public List<GlobalSettings> findAll() {
    return globalSettingsRepository.findAll();
  }

  public void saveSettings(List<GlobalSettings> settings) {
    settings.forEach(globalSettingsRepository::save);
  }

  public GlobalSettings getGlobalSettingByCode(GlobalSettingsEnum code) {
    return globalSettingsRepository.findByCode(code).orElse(null);
  }
}
