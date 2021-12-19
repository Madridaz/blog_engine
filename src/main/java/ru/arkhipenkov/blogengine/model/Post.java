package ru.arkhipenkov.blogengine.model;

import java.time.Instant;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.arkhipenkov.blogengine.enums.ModerationStatus;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor(force = true)
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  //скрыта или активна публикация: 0 или 1
  @Column(name = "is_active", nullable = false)
  private boolean isActive;

  //статус модерации, по умолчанию "NEW"
  @Enumerated(EnumType.STRING)
  @NotNull
  @Column(name = "moderation_status", length = 10, nullable = false)
  private ModerationStatus moderationStatus = ModerationStatus.NEW;

  //id пользователя-модератора, принявшего решение
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "moderator_id", referencedColumnName = "id")
  private User moderatedBy;

  //автор поста
  @NotNull
  @ManyToOne(cascade = CascadeType.MERGE, optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User author;

  //дата и время регистрации пользователя
  @NotNull
  @Column(nullable = false)
  private Instant time;

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
