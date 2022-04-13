package ru.arkhipenkov.blogengine.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.arkhipenkov.blogengine.api.response.CheckResponse;
import ru.arkhipenkov.blogengine.api.response.InitResponse;
import ru.arkhipenkov.blogengine.api.response.SettingsResponse;
import ru.arkhipenkov.blogengine.model.Tag;
import ru.arkhipenkov.blogengine.model.dto.TagDto;
import ru.arkhipenkov.blogengine.model.dto.TagListDto;
import ru.arkhipenkov.blogengine.service.CheckService;
import ru.arkhipenkov.blogengine.service.Post2TagService;
import ru.arkhipenkov.blogengine.service.PostService;
import ru.arkhipenkov.blogengine.service.SettingsService;
import ru.arkhipenkov.blogengine.service.TagService;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ApiGeneralController {

  private final SettingsService settingsService;
  private final InitResponse initResponse;
  private final CheckService checkService;
  private final TagService tagService;
  private final Post2TagService post2TagService;
  private final PostService postService;

  @GetMapping("/settings")
  private SettingsResponse settings() {
    return settingsService.getGlobalSettings();
  }

  @GetMapping("/init")
  private InitResponse init() {
    return initResponse;
  }

  @GetMapping("/auth/check")
  private CheckResponse checkResponse() {
    return checkService.check();
  }

  @GetMapping("tag")
  public ResponseEntity<?> getTags(@RequestParam(required = false) String query) {
    List<Tag> tagList = query != null ? tagService.findByStartsWith(query) : tagService.findAllTags();

    if (tagList.size() == 0) {
      return ResponseEntity.ok(null);
    }

    List<TagDto> tagDtoList = tagList.stream()
        .map(this::getTagDto)
        .sorted(Comparator.comparing(TagDto::getWeight).reversed())
        .collect(Collectors.toList());

    tagDtoList = tagService.setWeights(tagDtoList);

    return ResponseEntity.ok(new TagListDto(tagDtoList));
  }

  private TagDto getTagDto(Tag tag) {
    Integer postWithTagCount = post2TagService.countPostsWithTag(tag.getId());
    Integer postTotalCount = postService.countPosts();
    Float weight = (float)postWithTagCount / postTotalCount;

    return new TagDto(tag.getName(), weight);
  }

}
