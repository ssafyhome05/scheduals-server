package com.zipchack.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zipchack.api.NewsClient;
import com.zipchack.dto.NewsDto;
import com.zipchack.entity.TopTenEntity;
import com.zipchack.task.TopTenTask;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TopTenScheduler {

  private final TopTenTask task;
  private final NewsClient newsClient;

  public TopTenScheduler(TopTenTask task, NewsClient newsClient) {

    this.task = task;
    this.newsClient = newsClient;
  }

  @Scheduled(cron = "0 */5 * * * *")
  public void topTenScheduler() {

    TopTenEntity topTenEntity = task.saveCurrentTopTen(LocalDateTime.now().withSecond(0).withNano(0));
    log.info(topTenEntity.toString());
  }
  
  @Scheduled(cron = "0 */5 * * * *")
  public void newsScheduler() {
    newsClient.addNewsList();
    
  }
  
  
  
}
