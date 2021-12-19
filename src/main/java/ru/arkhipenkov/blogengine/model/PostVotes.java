package ru.arkhipenkov.blogengine.model;

import java.time.Instant;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_votes")
@Data
@NoArgsConstructor(force = true)
public class PostVotes {

  public PostVotes(@NotNull User user, @NotNull Post post) {
    this.user = user;
    this.post = post;
  }

  public PostVotes(@NotNull User user, @NotNull Post post, @NotNull Instant time) {
    this(user, post);
    this.time = time;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  //id пользователя, поставившего лайк/дизлайк
  @NotNull
  @ManyToOne(cascade = CascadeType.MERGE, optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;

  //id поста, которому поставивили лайк/дизлайк
  @NotNull
  @ManyToOne(cascade = CascadeType.MERGE, optional = false)
  @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
  private Post post;

  //дата и время лайка/дизлайка
  @NotNull
  @Column(nullable = false)
  private Instant time = Instant.now();

  //лайк или дизлайк: 1 или -1
  @Column(nullable = false)
  private byte value;

  public void like() {
    this.value = 1;
  }

  public void dislike() {
    this.value = -1;
  }
}
