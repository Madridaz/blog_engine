package ru.arkhipenkov.blogengine.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostPublishDto {

  private Long timestamp;

  private Byte active;

  private String title;

  private String text;

  private List<String> tags;
}
