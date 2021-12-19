package ru.arkhipenkov.blogengine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_comments")
@Data
@NoArgsConstructor(force = true)
public class PostComments {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  //id комментария, на который был оставлен этот комментарий
  @ManyToOne
  @JoinColumn(name = "parent_id", referencedColumnName = "id")
  private PostComments parentComment;

  @NotNull
  @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY, orphanRemoval = true)
  private final Set<PostComments> childComments = new HashSet<>();

  // Автор комментария
  @NotNull
  @ManyToOne(cascade = CascadeType.MERGE, optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
  private User user;

  // Пост, к которому написан комментарий
  @NotNull
  @ManyToOne(cascade = CascadeType.MERGE, optional = false)
  @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false, updatable = false)
  private Post post;

  // Дата и время комментария
  @NotNull
  @Column(nullable = false)
  @JsonProperty("raw_time")
  private Instant time = Instant.now();

  //текст комментария
  @NotBlank
  @Column(columnDefinition = "TEXT")
  private String text;


}
