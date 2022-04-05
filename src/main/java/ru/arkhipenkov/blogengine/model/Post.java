package ru.arkhipenkov.blogengine.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.arkhipenkov.blogengine.enums.ModerationStatus;

@Data
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "is_active", nullable = false)
  private Byte isActive;

  @Enumerated(EnumType.STRING)
  @Column(name = "moderation_status", nullable = false)
  private ModerationStatus moderationStatus;

  @JoinColumn(name = "moderator_id")
  @ManyToOne
  private User moderator;

  @JoinColumn(name = "user_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private User author;

  @Column(nullable = false)
  private LocalDateTime time;

  @Column(nullable = false)
  private String title;

  @Column(length = 30000, nullable = false)
  private String text;

  @Column(name = "view_count", nullable = false)
  private Integer viewCount;

  public Post(Byte isActive, ModerationStatus moderationStatus, User author, LocalDateTime time, String title, String text, Integer viewCount) {
    this.isActive = isActive;
    this.moderationStatus = moderationStatus;
    this.author = author;
    this.time = time;
    this.title = title;
    this.text = text;
    this.viewCount = viewCount;
  }
}
