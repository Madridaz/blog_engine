package ru.arkhipenkov.blogengine.api.response;

import java.util.List;
import lombok.Data;
import ru.arkhipenkov.blogengine.model.dto.PostDto;

@Data
public class PostResponse {

  private int count;
  private List<PostDto> posts;

  public PostResponse(int count, List<PostDto> posts) {
    this.count = count;
    this.posts = posts;
  }
}
