package com.zipchack.scheduler;

import com.zipchack.entity.TopTenEntity;
import com.zipchack.task.TopTenTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class TopTenScheduler {

  private final TopTenTask task;

  public TopTenScheduler(TopTenTask task) {

    this.task = task;
  }

  @Scheduled(cron = "0 */5 * * * *")
  public void topTenScheduler() {

    TopTenEntity topTenEntity = task.saveCurrentTopTen(LocalDateTime.now().withSecond(0).withNano(0));
    log.info(topTenEntity.toString());
  }
}
