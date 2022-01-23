package ru.arkhipenkov.blogengine.api.response;

import lombok.Data;
import ru.arkhipenkov.blogengine.model.Post;
import ru.arkhipenkov.blogengine.model.User;

@Data
public class PostResponse {

  private Post post;
  private User user;

  public PostResponse(Post post, User user) {
    this.post = post;
    this.user = user;
  }
}
