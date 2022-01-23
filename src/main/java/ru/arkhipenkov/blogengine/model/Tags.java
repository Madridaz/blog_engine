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
@Table(name = "tags")
@Data
@NoArgsConstructor(force = true)
public class Tags {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  //текст тега
  @NotNull
  @Size(max = 255)
  private String name;
}
