package ru.arkhipenkov.blogengine.controller;

import static java.util.stream.Collectors.toList;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.arkhipenkov.blogengine.exceptions.BadRequestException;
import ru.arkhipenkov.blogengine.exceptions.UnauthorizedException;
import ru.arkhipenkov.blogengine.model.Post;
import ru.arkhipenkov.blogengine.model.PostComment;
import ru.arkhipenkov.blogengine.model.User;
import ru.arkhipenkov.blogengine.model.dto.CommentUserDto;
import ru.arkhipenkov.blogengine.model.dto.OnePostDto;
import ru.arkhipenkov.blogengine.model.dto.PostCommentDto;
import ru.arkhipenkov.blogengine.model.dto.PostDto;
import ru.arkhipenkov.blogengine.model.dto.PostListDto;
import ru.arkhipenkov.blogengine.model.dto.PostUserDto;
import ru.arkhipenkov.blogengine.service.AuthService;
import ru.arkhipenkov.blogengine.service.PostCommentService;
import ru.arkhipenkov.blogengine.service.PostService;
import ru.arkhipenkov.blogengine.service.PostVoteService;
import ru.arkhipenkov.blogengine.service.TagService;
import ru.arkhipenkov.blogengine.service.UserService;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class ApiPostController {

  private final PostService postService;
  private final PostVoteService postVoteService;
  private final PostCommentService postCommentService;
  private final UserService userService;
  private final AuthService authService;
  private final TagService tagService;

  @Value("${title.min.length}")
  private Integer titleMinLength;

  @Value("${text.min.length}")
  private Integer textMinLength;

  @Value("${announce.length}")
  private Integer announceLength;

  @GetMapping
  public ResponseEntity<?> getPosts(
      @RequestParam Integer offset,
      @RequestParam Integer limit,
      @RequestParam String mode
  ) {
    List<Post> postList = postService.getPostsBySort(mode, offset, limit);

    List<PostDto> dtos = getPostDtoList(postList);

    return ResponseEntity.ok(new PostListDto(postService.countPosts(), dtos));
  }

  @GetMapping("/search")
  public ResponseEntity<?> searchPosts(
      @RequestParam Integer offset,
      @RequestParam Integer limit,
      @RequestParam String query
  ) {
    List<Post> postList = postService.findPostsByQuery(query, offset, limit);

    List<PostDto> dtos = getPostDtoList(postList);

    return ResponseEntity.ok(new PostListDto(postService.countPostsByQuery(query), dtos));
  }

  @GetMapping("/byDate")
  public ResponseEntity<PostListDto> findPostsByDate(
      @RequestParam Integer offset,
      @RequestParam Integer limit,
      @RequestParam String date
  ) {
    List<Post> postList = postService.getPostsByDate(date, offset, limit);

    List<PostDto> dtos = getPostDtoList(postList);

    return ResponseEntity.ok(new PostListDto(postService.countPostsByDate(date), dtos));
  }

  @GetMapping("/byTag")
  public ResponseEntity<?> findPostsByTag(
      @RequestParam Integer offset,
      @RequestParam Integer limit,
      @RequestParam String tag
  ) {
    List<Post> postList = postService.findPostsByTag(tag, offset, limit);

    List<PostDto> dtos = getPostDtoList(postList);

    return ResponseEntity.ok(new PostListDto(postService.countPostsByTag(tag), dtos));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getPostById(@PathVariable Integer id) {
    User user = userService.findUserById(authService.getUserIdBySession());

    Post post = postService.getPostByIdAndModerationStatus(id, user);

    if (post == null) {
      throw new BadRequestException("Данный пост вам не доступен");
    }

    return ResponseEntity.ok(getOnePostDto(post));
  }

  @GetMapping("/moderation")
  public ResponseEntity<?> getPostsByNeedModeration(@RequestParam Integer offset,
      @RequestParam Integer limit,
      @RequestParam String status
  ) {
    if (!authService.checkAuthorization()) {
      throw new UnauthorizedException();
    }

    List<Post> postList = postService.getPostsByNeedModeration(
        status,
        userService.findUserById(authService.getUserIdBySession()),
        offset, limit);

    List<PostDto> dtos = getPostDtoList(postList);

    return ResponseEntity.ok(new PostListDto(postService.countPostsByNeedModeration(
        status,
        userService.findUserById(authService.getUserIdBySession())),
        dtos
    ));
  }

  private List<PostDto> getPostDtoList(List<Post> postList) {
    return postList
        .stream()
        .map(this::getPostDto)
        .collect(toList());
  }

  private PostDto getPostDto(Post post) {

    PostUserDto authorDto = new PostUserDto(post.getAuthor().getId(), post.getAuthor().getName());

    Integer postId = post.getId();
    Integer likeCount = postVoteService.countVotesByPostIdAndValue(postId, 1);
    Integer dislikeCount = postVoteService.countVotesByPostIdAndValue(postId, -1);
    Integer commentCount = postCommentService.countByPostId(postId);

    return new PostDto(
        postId,
        post.getTime().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC)
            .toEpochSecond(),
        authorDto,
        post.getTitle(),
        post.getText()
            .replaceAll("<(\"[^\"]*\"|'[^']*'|[^'\">])*>", "")
            .substring(0, announceLength),
        likeCount,
        dislikeCount,
        commentCount,
        post.getViewCount()
    );
  }

  private OnePostDto getOnePostDto(Post post) {
    PostDto postDto = getPostDto(post);

    List<PostComment> commentList = postCommentService.findAllByPostId(postDto.getId());
    List<PostCommentDto> comments = commentList.stream().map(this::getPostCommentDto).collect(
        Collectors.toList());

    List<String> tags = tagService.findAllByPostId(postDto.getId());

    return new OnePostDto(
        postDto.getId(),
        postDto.getTimestamp(),
        postDto.getUser(),
        postDto.getTitle(),
        postDto.getAnnounce(),
        postDto.getLikeCount(),
        postDto.getDislikeCount(),
        postDto.getCommentCount(),
        postDto.getViewCount(),
        post.getText(),
        comments,
        tags
    );
  }

  private PostCommentDto getPostCommentDto(PostComment comment) {
    User commentUser = userService.findUserById(comment.getUserId());

    return new PostCommentDto(
        comment.getId(),
        comment.getTime().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC)
            .toEpochSecond(),
        comment.getText(),
        new CommentUserDto(
            commentUser.getId(),
            commentUser.getName(),
            commentUser.getPhoto()
        )
    );
  }
}
