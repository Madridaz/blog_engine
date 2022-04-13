package ru.arkhipenkov.blogengine.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.arkhipenkov.blogengine.model.Post2Tag;

public interface Post2TagRepository extends CrudRepository<Post2Tag, Integer> {

  @Query("select count(p2t) from Post2Tag as p2t " +
      "join Post as p on p.id = p2t.postId " +
      "where p2t.tagId = :tagId " +
      "and p.isActive = 1 " +
      "and p.moderationStatus = 'ACCEPTED' " +
      "and p.time < NOW()")
  Integer countByTagId(Integer tagId);

  Optional<Post2Tag> findByPostIdAndTagId(Integer postId, Integer tagId);
}
