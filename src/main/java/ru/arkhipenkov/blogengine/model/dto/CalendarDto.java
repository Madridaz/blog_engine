package ru.arkhipenkov.blogengine.model.dto;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalendarDto {

  private List<Integer> years;

  private Map<String, Integer> posts;
}
