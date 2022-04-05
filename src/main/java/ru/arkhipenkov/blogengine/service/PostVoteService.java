package ru.arkhipenkov.blogengine.service;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.arkhipenkov.blogengine.model.PostVote;
import ru.arkhipenkov.blogengine.model.dto.ResultTrueFalseDto;
import ru.arkhipenkov.blogengine.repository.PostsVotesRepository;

@Service
@AllArgsConstructor
public class PostVoteService {

  private final PostsVotesRepository postsVotesRepository;

  public ResultTrueFalseDto votePost(Integer postId, Integer userId, Integer value) {
    Optional<PostVote> exist = postsVotesRepository.findByPostIdAndUserId(postId, userId);
    if (exist.isPresent()) {
      postsVotesRepository.updatePostVoteByPostIdAndUserId(postId, userId, value);
    } else {
      postsVotesRepository.save(new PostVote(userId, postId, LocalDateTime.now(), value));
    }

    return new ResultTrueFalseDto(true);
  }

  public Integer countVotesByPostIdAndValue(Integer postId, Integer value) {
    return postsVotesRepository.countByPostIdAndValue(postId, value);
  }

  public Integer countVotesByValue(Integer value) {
    return postsVotesRepository.countPostVotesByValue(value);
  }
}
