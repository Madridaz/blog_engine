package ru.arkhipenkov.blogengine.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagDto {

  private String name;

  private Float weight;
}
