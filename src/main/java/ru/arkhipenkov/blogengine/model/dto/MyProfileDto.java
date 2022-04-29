package ru.arkhipenkov.blogengine.model.dto;

import lombok.Data;

@Data
public class MyProfileDto {

  private Byte removePhoto;

  private String name;

  private String email;

  private String password;
}
