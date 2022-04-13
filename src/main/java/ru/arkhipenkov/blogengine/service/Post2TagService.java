package ru.arkhipenkov.blogengine.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.arkhipenkov.blogengine.model.Post2Tag;
import ru.arkhipenkov.blogengine.repository.Post2TagRepository;

@Service
@AllArgsConstructor
public class Post2TagService {

  private final Post2TagRepository post2TagRepository;

  public Integer countPostsWithTag(Integer tagId) {
    return post2TagRepository.countByTagId(tagId);
  }

  public void savePost2Tag(Integer postId, List<Integer> tagIds) {
    tagIds.forEach(tagId -> {
      Post2Tag exist = post2TagRepository.findByPostIdAndTagId(postId, tagId).orElse(null);

      if (exist == null) {
        post2TagRepository.save(new Post2Tag(postId, tagId));
      }
    });
  }

  public void deletePost2Tag(Integer postId, List<Integer> tagIds) {
    tagIds.forEach(tagId -> {
      Post2Tag exist = post2TagRepository.findByPostIdAndTagId(postId, tagId).orElse(null);

      if (exist != null) {
        post2TagRepository.delete(exist);
      }
    });
  }
}
