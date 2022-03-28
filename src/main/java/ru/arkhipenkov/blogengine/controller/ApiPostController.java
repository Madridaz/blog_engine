package ru.arkhipenkov.blogengine.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.arkhipenkov.blogengine.api.response.PostResponse;
import ru.arkhipenkov.blogengine.model.dto.PostDto;
import ru.arkhipenkov.blogengine.model.dto.PostUserDto;
import ru.arkhipenkov.blogengine.service.PostService;

@RestController
@RequestMapping("/api")
public class ApiPostController {

  private final PostService postService;

  public ApiPostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping("/post")
  private PostResponse postResponse() {
    List<PostDto> postDtoList = new ArrayList<>();
    PostDto postDto = new PostDto(1, 123456789000254l, new PostUserDto(1, "Вася"), "тестовый пост", "анонс",
        10,5,3,2);
    postDtoList.add(postDto);
    return new PostResponse(1, postDtoList);
  }
}
