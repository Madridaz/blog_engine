package ru.arkhipenkov.blogengine.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import ru.arkhipenkov.blogengine.enums.GlobalSettingsEnum;
import ru.arkhipenkov.blogengine.model.GlobalSettings;

public interface GlobalSettingsRepository extends CrudRepository<GlobalSettings, Integer> {

  List<GlobalSettings> findAll();

  Optional<GlobalSettings> findByCode(GlobalSettingsEnum code);
}
