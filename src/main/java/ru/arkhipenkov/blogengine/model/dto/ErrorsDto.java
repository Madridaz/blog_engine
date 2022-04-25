package ru.arkhipenkov.blogengine.model.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorsDto {

  private Boolean result;

  private Map<String, String> errors;
}
