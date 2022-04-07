package ru.arkhipenkov.blogengine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.arkhipenkov.blogengine.api.response.InitResponse;

@Controller
public class DefaultController {

  private final InitResponse initResponse;

  public DefaultController(InitResponse initResponse) {
    this.initResponse = initResponse;
  }

  @RequestMapping("/")
  public String index(Model model) {
    return "index";
  }

}
