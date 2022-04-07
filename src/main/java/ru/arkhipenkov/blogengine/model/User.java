package ru.arkhipenkov.blogengine.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "is_moderator", nullable = false)
  private Byte isModerator;

  @Column(name = "reg_time", nullable = false)
  private LocalDateTime regTime;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  private String code;

  private String photo;

  public User(Byte isModerator, LocalDateTime regTime, String name, String email, String password,
      String code, String photo) {
    this.isModerator = isModerator;
    this.regTime = regTime;
    this.name = name;
    this.email = email;
    this.password = password;
    this.code = code;
    this.photo = photo;
  }
}
