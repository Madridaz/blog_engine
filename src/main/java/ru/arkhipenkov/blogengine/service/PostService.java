package ru.arkhipenkov.blogengine.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import ru.arkhipenkov.blogengine.enums.ModerationStatus;
import ru.arkhipenkov.blogengine.model.Post;
import ru.arkhipenkov.blogengine.repository.PostRepository;

@Service
@AllArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  public List<Post> getPostsBySort(String mode, Integer offset, Integer limit) {
    List<Post> postList = null;

    switch (mode) {
      case "recent":
        postList = postRepository.findAllByTimeBeforeAndIsActiveAndModerationStatus(
            LocalDateTime.now(),
            (byte) 1,
            ModerationStatus.ACCEPTED,
            PageRequest.of(
                offset / limit,
                limit,
                Sort.by(Direction.DESC, "time")
            )
        );
        break;
      case "popular":
        postList = postRepository.findAllByModerationStatusAndIsActiveAndCommentsCount(
            ModerationStatus.ACCEPTED, (byte) 1, PageRequest.of(offset / limit, limit)
        );
        break;
      case "best":
        postList = postRepository.findAllByModerationStatusAndIsActiveAndLikesCount(
            ModerationStatus.ACCEPTED, (byte) 1, PageRequest.of(offset / limit, limit)
        );
        break;
      case "early":
        postList = postRepository.findAllByTimeBeforeAndIsActiveAndModerationStatus(
            LocalDateTime.now(),
            (byte) 1,
            ModerationStatus.ACCEPTED,
            PageRequest.of(
                offset / limit,
                limit,
                Sort.by(Direction.ASC, "time")
            )
        );
        break;
    }

    return postList;
  }

  public Integer countPosts() {
    return postRepository.countByIsActiveAndModerationStatusAndTimeBefore(
        (byte) 1, ModerationStatus.ACCEPTED, LocalDateTime.now());
  }


}

