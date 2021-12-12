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
import lombok.NonNull;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor(force = true)
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  //скрыта или активна публикация: 0 или 1
  @NonNull
  @Column(name = "is_active")
  private int isActive;

//moderation_status - ?

  //id пользователя-модератора, принявшего решение
  @NonNull
  @Column(name = "moderator_id")
  private int moderatorId;

  //автор поста
  @NonNull
  @Column(name = "user_id")
  private int userId;

  //дата и время регистрации пользователя
  @NotNull
  @Column(name = "time")
  private Timestamp time;

  //заголовок поста
  @NotBlank
  @Size(max = 255)
  private String title;

  //текст поста
  @Column(columnDefinition = "TEXT")
  private String text;

  //количество просмотров поста
  @NotNull
  @Column(name = "view_count")
  private int viewCount;

}
