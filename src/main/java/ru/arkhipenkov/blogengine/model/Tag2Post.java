package ru.arkhipenkov.blogengine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tag2post")
@Data
@NoArgsConstructor(force = true)
public class Tag2Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  //id поста
  @NotNull
  @Column(name = "post_id")
  private int postId;

  //id тега
  @NotNull
  @Column(name = "tag_id")
  private int tagId;
}
