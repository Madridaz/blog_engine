package ru.arkhipenkov.blogengine.model;

import java.sql.Timestamp;
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
@Table(name = "post_votes")
@Data
@NoArgsConstructor(force = true)
public class PostVotes {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  //id пользователя, поставившего лайк/дизлайк
  @NotNull
  @Column(name = "userId")
  private int userId;

  //id поста, которому поставивили лайк/дизлайк
  @NotNull
  @Column(name = "post_id")
  private int postId;

  //дата и время лайка/дизлайка
  @NotNull
  @Column(name = "time")
  private Timestamp time;

  //лайк или дизлайк: 1 или -1
  @NotNull
  @Column(name = "value")
  private int value;
}
