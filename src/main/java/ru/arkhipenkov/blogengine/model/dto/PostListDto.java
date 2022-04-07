package ru.arkhipenkov.blogengine.model.dto;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostListDto {

  private Integer count;

  private Collection<PostDto> posts;
}
