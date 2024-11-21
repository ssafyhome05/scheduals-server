package com.zipchack.task;

import com.zipchack.entity.SearchKeywordEntity;
import com.zipchack.entity.TopTenEntity;
import com.zipchack.repository.SearchKeywordRepository;
import com.zipchack.repository.TopTenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Slf4j
public class TopTenTask {

  private final SearchKeywordRepository searchKeywordRepository;
  private final TopTenRepository topTenRepository;

  public TopTenTask(SearchKeywordRepository searchKeywordRepository, TopTenRepository topTenRepository) {

    this.searchKeywordRepository = searchKeywordRepository;
    this.topTenRepository = topTenRepository;
  }

  public TopTenEntity saveCurrentTopTen(LocalDateTime now) {

    TopTenEntity nowEntity = new TopTenEntity(timeFormatter(now), analyzeKeyword(now));

    if (topTenRepository.existsById(timeFormatter(now.minusMinutes(5)))) {

      TopTenEntity prevEntity = topTenRepository.findById(timeFormatter(now.minusMinutes(5))).get();
      if (prevEntity.getElements() != null) {
        calcChanged(nowEntity, prevEntity);
        
      }
    }

    topTenRepository.save(nowEntity);
    return nowEntity;
  }

  private Map<String, TopTenEntity.Element> analyzeKeyword(LocalDateTime now) {

    Map<String, Integer> keywordScoreMap = new HashMap<>();
    for (SearchKeywordEntity entity : searchKeywordRepository.findAll()) {

      keywordScoreMap.put(
          entity.getKeyword(),
          keywordScoreMap.getOrDefault(entity.getKeyword(), 0) + TimeScore.calculateScore(entity.getSearchedAt(), now)
      );
    }

    
    List<String> keywords = keywordScoreMap.keySet().stream()
        .sorted(Comparator.comparingInt(keywordScoreMap::get).reversed())
        .limit(10).toList();

    return IntStream.range(0, Math.min(keywords.size(), 10))
        .mapToObj(i -> new TopTenEntity.Element(keywords.get(i), i + 1))
        .collect(Collectors.toMap(
            e -> e.getKeyword(),
            e -> e
        ));
  }

  private void calcChanged(TopTenEntity nowEntity, TopTenEntity prevEntity) {

    for (TopTenEntity.Element element : nowEntity.getElements().values()) {

      if (prevEntity.getElements().containsKey(element.getKeyword())) {
        element.setChanged(prevEntity.getElements().get(element.getKeyword()).getRank() - element.getRank());
      }
      else {
        element.setChanged(99);
      }
    }
  }

  private String timeFormatter(LocalDateTime time) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    return time.format(formatter);
  }
}
