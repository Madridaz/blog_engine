package ru.arkhipenkov.blogengine.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
  @Column(name = "is_moderator", nullable = false)
  private boolean isModerator;

  //дата и время регистрации пользователя
  @NotNull
  @Column(name = "reg_time", nullable = false)
  private Instant regTime;

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

  // Публикации пользователя
  @NotNull
  @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, orphanRemoval = true)
  private final Set<Post> posts = new HashSet<>();

  // Публикации, модерируемые пользователем
  @NotNull
  @OneToMany(mappedBy = "moderatedBy", fetch = FetchType.LAZY, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.EXTRA)
  private final Set<Post> moderatedPosts = new HashSet<>();

  // Комментарии пользователя

  @NotNull
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
  private final Set<PostComments> comments = new HashSet<>();

  // Лайки / дизлайки пользователя

  @NotNull
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
  private final Set<PostVotes> votes = new HashSet<>();

}
