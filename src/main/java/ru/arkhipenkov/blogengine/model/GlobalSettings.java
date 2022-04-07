package ru.arkhipenkov.blogengine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.arkhipenkov.blogengine.enums.GlobalSettingsEnum;

@Data
@Entity
@Table(name = "global_settings")
@NoArgsConstructor
public class GlobalSettings {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private GlobalSettingsEnum code;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String value;

  public GlobalSettings(GlobalSettingsEnum code, String name, String value) {
    this.code = code;
    this.name = name;
    this.value = value;
  }
}
