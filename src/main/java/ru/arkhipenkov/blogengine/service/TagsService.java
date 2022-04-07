package ru.arkhipenkov.blogengine.service;

import org.springframework.stereotype.Service;
import ru.arkhipenkov.blogengine.api.response.TagsResponse;

@Service
public class TagsService {

  public String getTags() {
    TagsResponse tagsResponse = new TagsResponse();
    tagsResponse.setName("test tag");
    return tagsResponse.getName();
  }

}
