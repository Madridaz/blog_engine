package ru.arkhipenkov.blogengine.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ru.arkhipenkov.blogengine.model.PostComment;

public interface PostsCommentsRepository extends CrudRepository<PostComment, Integer> {
  Integer countByPostId(Integer postId);

  List<PostComment> findAllByPostId(Integer postId);
}
