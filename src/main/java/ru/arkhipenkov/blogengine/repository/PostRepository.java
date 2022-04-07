package ru.arkhipenkov.blogengine.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.arkhipenkov.blogengine.enums.ModerationStatus;
import ru.arkhipenkov.blogengine.model.Post;
import ru.arkhipenkov.blogengine.model.User;

public interface PostRepository extends JpaRepository<Post, Integer> {

  @Transactional
  @Modifying
  @Query("UPDATE Post p SET p.viewCount = :viewCount WHERE p.id = :postId")
  void updateViewCount(Integer postId, Integer viewCount);

  List<Post> findAllByTimeBeforeAndIsActiveAndModerationStatus(
      LocalDateTime time,
      Byte isActive,
      ModerationStatus status,
      Pageable pageable
  );

  @Query("SELECT p FROM Post p " +
      "JOIN PostComment pc on pc.postId = p.id " +
      "WHERE p.moderationStatus = :status " +
      "AND p.isActive = :isActive " +
      "GROUP BY pc.postId " +
      "ORDER BY count(p) DESC")
  List<Post> findAllByModerationStatusAndIsActiveAndCommentsCount(ModerationStatus status,
      Byte isActive, Pageable pageable);

  @Query("SELECT p FROM Post p " +
      "JOIN PostVote pv on pv.postId = p.id " +
      "WHERE p.moderationStatus = :status " +
      "AND p.isActive = :isActive " +
      "AND pv.value = 1 " +
      "GROUP BY pv.postId " +
      "ORDER BY count(p) DESC")
  List<Post> findAllByModerationStatusAndIsActiveAndLikesCount(ModerationStatus status,
      Byte isActive, Pageable pageable);

  List<Post> findAllByTitleContainingOrTextContainingAndTimeBeforeAndIsActiveAndModerationStatus(
      String title,
      String text,
      LocalDateTime time,
      Byte isActive,
      ModerationStatus status,
      Pageable pageable
  );

  Integer countAllByTitleContainingOrTextContainingAndTimeBeforeAndIsActiveAndModerationStatus(
      String title,
      String text,
      LocalDateTime time,
      Byte isActive,
      ModerationStatus status
  );

  @Query(value =
      "SELECT * FROM posts " +
          "WHERE date(time) = :date " +
          "AND is_active = 1 " +
          "AND moderation_status = 'ACCEPTED' ", nativeQuery = true)
  List<Post> findAllByTime_DateAndIsActiveAndModerationStatus(@Param("date") String date,
      Pageable pageable);

  @Query(value =
      "select count(*) from posts " +
          "where date(time) = :date " +
          "and is_active = 1 " +
          "and moderation_status = 'ACCEPTED'", nativeQuery = true)
  Integer countByTime(@Param("date") String date);

  Optional<Post> findByIdAndIsActiveAndModerationStatus(Integer id, Byte isActive,
      ModerationStatus status);

  List<Post> findAllByModerationStatusAndModeratorAndIsActive(
      ModerationStatus status,
      User moderator,
      Byte isActive,
      Pageable pageable
  );

  Integer countAllByModerationStatusAndModeratorAndIsActive(
      ModerationStatus status,
      User moderator,
      Byte isActive
  );

  List<Post> findAllByModerationStatusAndIsActive(ModerationStatus status, Byte isActive,
      Pageable pageable);

  Integer countAllByModerationStatusAndIsActive(ModerationStatus status, Byte isActive);

  List<Post> findAllByModerationStatusAndIsActiveAndAuthorId(
      ModerationStatus status,
      Byte isActive,
      Integer authorId,
      Pageable pageable
  );

  Integer countAllByModerationStatusAndIsActiveAndAuthorId(
      ModerationStatus status,
      Byte isActive,
      Integer authorId
  );

  List<Post> findAllByIsActiveAndAuthorId(Byte isActive, Integer authorId, Pageable pageable);

  Integer countAllByIsActiveAndAuthorId(Byte isActive, Integer authorId);

  @Query(
      "select p from Post p " +
          "join Post2Tag p2t on p2t.postId = p.id " +
          "join Tag t on t.id = p2t.tagId " +
          "where t.name = :tagName")
  List<Post> findAllByTag(String tagName, Pageable pageable);

  @Query("select count(p) from Post p " +
      "join Post2Tag p2t on p2t.postId = p.id " +
      "join Tag t on t.id = p2t.tagId " +
      "where t.name = :tagName")
  Integer countAllByTag(String tagName);

  Integer countByIsActiveAndModerationStatusAndTimeBefore(
      Byte isActive, ModerationStatus status, LocalDateTime time
  );


  Integer countPostsByAuthorId(Integer authorId);

  @Query(
      "select count(p) from Post p " +
          "join PostVote pv on pv.postId = p.id " +
          "where p.author.id = :authorId " +
          "and pv.value = :value"
  )
  Integer countTotalVotesByAuthorIdAndValue(Integer authorId, Integer value);

  @Query(
      "select sum(p.viewCount) from Post p " +
          "where p.author.id = :authorId"
  )
  Integer countViewCountByAuthorId(Integer authorId);

  @Query(
      "select sum(p.viewCount) from Post p"
  )
  Integer countViewCount();

  @Query(
      "select min(p.time) from Post p " +
          "where p.author.id = :authorId")
  Optional<LocalDateTime> findByAuthorIdFirstPublicationDate(Integer authorId);

  @Query("select min(p.time) from Post p")
  Optional<LocalDateTime> findFirstPublicationDate();

  Optional<Post> findByIdAndIsActive(Integer postId, Byte isActive);

  @Query("select distinct year(p.time) from Post p")
  List<Integer> findAllDistinctByTimeYear();

  @Query(value =
      "SELECT * FROM posts " +
          "WHERE year(time) = :year " +
          "AND is_active = 1 " +
          "AND moderation_status = 'ACCEPTED' " +
          "AND time <= now()", nativeQuery = true)
  List<Post> findAllByYear(@Param("year") Integer year);
}
