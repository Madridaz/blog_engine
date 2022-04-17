package ru.arkhipenkov.blogengine.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.arkhipenkov.blogengine.model.CaptchaCode;

public interface CaptchaCodesRepository extends CrudRepository<CaptchaCode, Integer> {
  @Transactional
  @Modifying
  @Query(value =
      "delete from captcha_codes " +
          "where  time_to_sec(timediff(now(), time)) > 3600", nativeQuery = true)
  void deleteOld();

  Optional<CaptchaCode> findByCodeAndSecretCode(String Code, String secretCode);
}
