package ru.arkhipenkov.blogengine.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "global_settings")
@Data
@NoArgsConstructor(force = true)
public class GlobalSettings {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  //системное имя настройки
  @NotNull
  @Size(max = 255)
  private String code;

  //название настройки
  @NotNull
  @Size(max = 255)
  private String name;

  //значение настройки
  @NotNull
  @Size(max = 255)
  private String value;
}
