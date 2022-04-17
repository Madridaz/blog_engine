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

