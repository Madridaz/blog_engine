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

  //id комментария, на на который был оставлен этот комментарий
  @Column(name = "parent_id")
  private int parentId;

  //id поста, к которому оставлен комментарий
  @NotNull
  @Column(name = "post_id")
  private int postId;

  //id автора комментария
  @NotNull
  @Column(name = "user_id")
  private int userId;

  //дата и время комментария
  @NotNull
  @Column(name = "time")
  private Timestamp time;

  //текст комментария
  @NotBlank
  @Column(columnDefinition = "TEXT")
  private String text;

}
