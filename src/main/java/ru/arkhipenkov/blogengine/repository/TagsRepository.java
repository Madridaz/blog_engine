package ru.arkhipenkov.blogengine.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.arkhipenkov.blogengine.model.Tag;

public interface TagsRepository extends CrudRepository<Tag, Integer> {

  List<Tag> findAll();

  List<Tag> findByNameStartingWith(String query);

  @Query(
      "select t.name from Tag t " +
          "join Post2Tag as p2t on p2t.tagId = t.id " +
          "where p2t.postId = :postId")
  List<String> findAllByPostId(Integer postId);

  Optional<Tag> findByName(String name);
}
