package ru.arkhipenkov.blogengine.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.arkhipenkov.blogengine.model.User;
import ru.arkhipenkov.blogengine.repository.UsersRepository;

@Service
@AllArgsConstructor
public class UserService {

  private final UsersRepository usersRepository;

  public User findUserByEmail(String email) {
    return usersRepository.findByEmail(email).orElse(null);
  }

  public User findUserById(Integer id) {
    return id == null ? null : usersRepository.findById(id).orElse(null);
  }

  public User findUserByRecoverCode(String code) {
    return usersRepository.findByCode(code).orElse(null);
  }

  public void updatePhoto(User user, String url) {
    user.setPhoto(url);
    usersRepository.save(user);
  }

  public void saveUser(User user) {
    usersRepository.save(user);
  }
}
