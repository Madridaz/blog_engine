package ru.arkhipenkov.blogengine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModerationPostDto {

  @JsonProperty("post_id")
  private Integer postId;

  private String decision;
}
