package ru.arkhipenkov.blogengine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "post2tag")
public class Post2Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "post_id")
  private Integer postId;

  @Column(name = "tag_id")
  private Integer tagId;

  public Post2Tag(Integer postId, Integer tagId) {
    this.postId = postId;
    this.tagId = tagId;
  }
}
