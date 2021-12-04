package ru.arkhipenkov.blogengine.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

  @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.GET}, value = "/**/{path:[^\\.]*}")
  public String redirectToIndex() {
    return "index";
  }

}
