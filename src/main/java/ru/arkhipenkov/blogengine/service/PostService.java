package ru.arkhipenkov.blogengine.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import ru.arkhipenkov.blogengine.enums.ModerationStatus;
import ru.arkhipenkov.blogengine.model.Post;
import ru.arkhipenkov.blogengine.model.User;
import ru.arkhipenkov.blogengine.model.dto.CalendarDto;
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

  public List<Post> findPostsByQuery(String query, Integer offset, Integer limit) {
    return postRepository.findAllByTitleContainingOrTextContainingAndTimeBeforeAndIsActiveAndModerationStatus(
        query,
        query,
        LocalDateTime.now(),
        (byte) 1,
        ModerationStatus.ACCEPTED,
        PageRequest.of(offset / limit, limit)
    );
  }

  public Integer countPostsByQuery(String query) {
    return postRepository.countAllByTitleContainingOrTextContainingAndTimeBeforeAndIsActiveAndModerationStatus(
        query,
        query,
        LocalDateTime.now(),
        (byte) 1,
        ModerationStatus.ACCEPTED
    );
  }

  public Integer countPosts() {
    return postRepository.countByIsActiveAndModerationStatusAndTimeBefore(
        (byte) 1, ModerationStatus.ACCEPTED, LocalDateTime.now());
  }

  public List<Integer> findPublicationYears() {
    return postRepository.findAllDistinctByTimeYear();
  }

  public List<Post> getPostsByDate(String date, Integer offset, Integer limit) {
    return postRepository.findAllByTime_DateAndIsActiveAndModerationStatus(
        date, PageRequest.of(offset / limit, limit));
  }

  public Integer countPostsByDate(String date) {
    return postRepository.countByTime(date);
  }

  public List<Post> findPostsByTag(String tag, Integer offset, Integer limit) {
    return postRepository.findAllByTag(tag, PageRequest.of(offset / limit, limit));
  }

  public Integer countPostsByTag(String tag) {
    return postRepository.countAllByTag(tag);
  }

  public Post getPostByIdAndModerationStatus(Integer postId, User user) {

    Post post;
    if (user != null) {
      post = postRepository.findById(postId).orElse(null);

      if (user.getIsModerator() == 0 && post != null) {
        if (!(post.getAuthor().getId().equals(user.getId()) ||
            post.getIsActive() == (byte) 1 ||
            post.getModerationStatus() == ModerationStatus.ACCEPTED)
        ) {
          post = null;
        }
      }
    } else {
      post = postRepository
          .findByIdAndIsActiveAndModerationStatus(postId, (byte) 1, ModerationStatus.ACCEPTED)
          .orElse(null);
    }

    if (post != null) {
      if (user != null) {
        if (post.getAuthor().getId().equals(user.getId())) {
          return post;
        }
      }
      post.setViewCount(post.getViewCount() + 1);
      postRepository.updateViewCount(post.getId(), post.getViewCount());
    }
    return post;
  }

  public List<Post> getPostsByNeedModeration(String status, User moderator, Integer offset,
      Integer limit) {
    List<Post> postList = null;

    switch (status) {
      case "new":
        postList = postRepository.findAllByModerationStatusAndIsActive(
            ModerationStatus.NEW, (byte) 1,
            PageRequest.of(offset / limit, limit)
        );
        break;
      case "declined":
        postList = postRepository.findAllByModerationStatusAndModeratorAndIsActive(
            ModerationStatus.DECLINED, moderator, (byte) 1,
            PageRequest.of(offset / limit, limit)
        );
        break;
      case "accepted":
        postList = postRepository.findAllByModerationStatusAndModeratorAndIsActive(
            ModerationStatus.ACCEPTED, moderator, (byte) 1,
            PageRequest.of(offset / limit, limit)
        );
        break;
    }

    return postList;
  }

  public Integer countPostsByNeedModeration(String status, User moderator) {
    Integer count = 0;

    switch (status) {
      case "new":
        count = postRepository.countAllByModerationStatusAndIsActive(ModerationStatus.NEW, (byte) 1);
        break;
      case "declined":
        count = postRepository.countAllByModerationStatusAndModeratorAndIsActive(
            ModerationStatus.DECLINED, moderator, (byte) 1
        );
        break;
      case "accepted":
        count = postRepository.countAllByModerationStatusAndModeratorAndIsActive(
            ModerationStatus.ACCEPTED, moderator, (byte) 1
        );
        break;
    }

    return count;
  }

  public Integer countPostsNeedModeration() {
    return postRepository.countByIsActiveAndModerationStatusAndTimeBefore(
        (byte) 1, ModerationStatus.NEW, LocalDateTime.now());
  }

  public CalendarDto getCalendarDto(Integer year) {
    List<Integer> yearsWithPublications = findPublicationYears();

    List<Post> postList = postRepository.findAllByYear(year);

    Map<String, Integer> postMap = new TreeMap<>();
    postList.forEach(post -> {
      String postDate = post.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      Integer postCount = countPostsByDate(postDate);
      postMap.put(postDate, postCount);
    });

    return new CalendarDto(yearsWithPublications, postMap);
  }

}

