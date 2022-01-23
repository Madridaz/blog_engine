package ru.arkhipenkov.blogengine.service;

import java.time.Instant;
import org.springframework.stereotype.Service;
import ru.arkhipenkov.blogengine.api.response.PostResponse;
import ru.arkhipenkov.blogengine.enums.ModerationStatus;
import ru.arkhipenkov.blogengine.model.Post;
import ru.arkhipenkov.blogengine.model.User;

@Service
public class PostService {

  private Post firstPost = new Post();
  private User user = new User();

  public Post testPost() {
    PostResponse postResponse = new PostResponse(firstPost, user);
    firstPost.setId(0);
    firstPost.setActive(true);
    firstPost.setModerationStatus(ModerationStatus.NEW);
    firstPost.setModeratedBy(null);
    firstPost.setTitle("This is first post");

    user.setId(0);
    user.setCode(null);
    user.setEmail("mail@mail.ru");
    user.setModerator(true);
    user.setName("Василий Белоглазов");
    user.setPassword("12345");
    user.setPhoto(null);
    user.setRegTime(Instant.now());

    firstPost.setAuthor(user);
    firstPost.setText("first post, created manually");
    firstPost.setTime(Instant.now());
    firstPost.setViewCount(10);

    return postResponse.getPost();

  }


}
