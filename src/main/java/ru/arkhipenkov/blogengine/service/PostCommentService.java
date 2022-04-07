package ru.arkhipenkov.blogengine.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.arkhipenkov.blogengine.model.PostComment;
import ru.arkhipenkov.blogengine.model.dto.AddCommentDto;
import ru.arkhipenkov.blogengine.repository.PostsCommentsRepository;

@Service
@AllArgsConstructor
public class PostCommentService {

  private final PostsCommentsRepository postsCommentsRepository;

  public Integer countByPostId(Integer postId) {
    return postsCommentsRepository.countByPostId(postId);
  }

  public List<PostComment> findAllByPostId(Integer postId) {
    return postsCommentsRepository.findAllByPostId(postId);
  }

  public PostComment findById(Integer id) {
    return postsCommentsRepository.findById(id).orElse(null);
  }

  public Integer saveComment(AddCommentDto addCommentDto, Integer authorId) {
    PostComment postComment = new PostComment(
        addCommentDto.getParentId(),
        addCommentDto.getPostId(),
        authorId,
        LocalDateTime.now(),
        addCommentDto.getText()
    );

    return postsCommentsRepository.save(postComment).getId();
  }
}
