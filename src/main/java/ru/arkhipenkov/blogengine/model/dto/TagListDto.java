package ru.arkhipenkov.blogengine.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagListDto {
  private List<TagDto> tags;
}
