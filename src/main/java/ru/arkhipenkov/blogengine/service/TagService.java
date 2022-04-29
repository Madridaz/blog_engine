package ru.arkhipenkov.blogengine.service;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.arkhipenkov.blogengine.model.Tag;
import ru.arkhipenkov.blogengine.model.dto.TagDto;
import ru.arkhipenkov.blogengine.repository.TagsRepository;

@Service
@AllArgsConstructor
public class TagService {

  private final TagsRepository tagsRepository;

  public List<Tag> findAllTags() {
    return tagsRepository.findAll();
  }

  public List<Tag> findByStartsWith(String query) {
    return tagsRepository.findByNameStartingWith(query);
  }

  public List<Integer> findIdsByNames(List<String> tagNames) {
    List<Integer> tagIds = new ArrayList<>();
    tagNames.forEach(tagName -> {
      Tag exist = tagsRepository.findByName(tagName).orElse(null);

      if (exist != null) {
        tagIds.add(exist.getId());
      }
    });

    return tagIds;
  }

  public List<String> findAllByPostId(Integer postId) {
    return tagsRepository.findAllByPostId(postId);
  }

  public List<Integer> saveTags(List<String> tagNamesNew, Integer postId) {
    List<Integer> tagIds = new ArrayList<>();

    for (String tagName : tagNamesNew) {
      Tag exist = tagsRepository.findByName(tagName).orElse(null);

      if (exist == null) {
        exist = tagsRepository.save(new Tag(tagName));
      }

      tagIds.add(exist.getId());
    }

    return tagIds;
  }

  public List<TagDto> setWeights(List<TagDto> tagDtoList) {
    Float biggestWeight = tagDtoList.get(0).getWeight();
    if (biggestWeight <= 0.0) {
      return null;
    }

    tagDtoList.get(0).setWeight(1f);

    float multiplicationCoefficient = 1 / biggestWeight;
    tagDtoList.forEach(tagDto -> {
      Float tagWeight = tagDto.getWeight();
      if (tagWeight < 0.3 && tagWeight != 0) {
        tagWeight *= multiplicationCoefficient;

        if (tagWeight < 0.3) {
          tagWeight = 0.3f;
        }
        tagDto.setWeight(tagWeight);
      }
    });

    return tagDtoList;
  }
}
