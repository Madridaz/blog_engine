package ru.arkhipenkov.blogengine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.arkhipenkov.blogengine.model.Post;
import ru.arkhipenkov.blogengine.service.PostService;

@RestController
@RequestMapping("/api")
public class ApiPostController {

  private final PostService postService;

  public ApiPostController(PostService postService) {
    this.postService = postService;
  }

//  @GetMapping("/post")
//  private Post post() {
//    return postService.testPost();
//  }
}
