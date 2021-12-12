package ru.arkhipenkov.blogengine.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor(force = true)
public class User {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  //является ли пользователь модератором
  @NotNull
  @Column(name = "is_moderator")
  private int isModerator;

  //дата и время регистрации пользователя
  @NotNull
  @Column(name = "reg_time")
  private Timestamp regTime;

  //имя пользователя
  @NotNull
  @Size(max = 255)
  private String name;

  //e-mail пользователя
  @NotBlank
  @Size(max = 255)
  private String email;

  //хэш пароля пользователя
  @NotBlank
  @Size(max = 255)
  private String password;

  //код для восстановления пароля
  @Size(max = 255)
  private String code;

  //ссылка на файл с фотографией
  @Column(columnDefinition = "TEXT")
  private String photo;
}
