package ru.arkhipenkov.blogengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arkhipenkov.blogengine.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
