package ru.arkhipenkov.blogengine.service;

import org.springframework.stereotype.Service;
import ru.arkhipenkov.blogengine.api.response.CheckResponse;

@Service
public class CheckService {

  public CheckResponse check() {
    CheckResponse checkResponse = new CheckResponse();
    checkResponse.setResult(false);
    return checkResponse;
  }

}
