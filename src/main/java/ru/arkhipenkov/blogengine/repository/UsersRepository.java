package ru.arkhipenkov.blogengine.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import ru.arkhipenkov.blogengine.model.User;

public interface UsersRepository extends CrudRepository<User, Integer> {

  Optional<User> findByEmail(String email);

  Optional<User> findByCode(String code);
}
