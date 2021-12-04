package ru.arkhipenkov.blogengine.model;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

@Entity
@Data
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "is_moderator", nullable = false)
  private boolean isModerator;

  @NotNull
  @Column(name = "reg_time", nullable = false)
  private Instant regTime;

  @NotBlank
  @Size(max = 255)
  @Column(nullable = false)
  private String name;

  @NaturalId(mutable = true)
  @Email
  @NotBlank
  @Size(max = 255)
  @Column(nullable = false)
  private String email;

  @NotBlank
  @Size(max = 255)
  @Column(nullable = false)
  private String password;

  @Size(max = 255)
  private String code;

  @Column(columnDefinition = "TEXT")
  private String photo;


}
