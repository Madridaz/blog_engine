package ru.arkhipenkov.blogengine.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommentUserDto extends PostUserDto {

  private String photo;

  public CommentUserDto(Integer id, String name, String photo) {
    super(id, name);
    this.photo = photo;
  }
}
