package ru.arkhipenkov.blogengine.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ImageService {

  @Value("${upload.path}")
  private String location;

  public void init() {
    try {
      Files.createDirectories(Paths.get(location));
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize storage", e);
    }
  }
}
