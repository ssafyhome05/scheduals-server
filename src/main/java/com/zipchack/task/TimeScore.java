package com.zipchack.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public enum TimeScore {
  TEN_MINUTES(10, 100),
  THIRTY_MINUTES(30, 50),
  ONE_HOUR(60, 25),
  SIX_HOURS(360, 10),
  TWELVE_HOURS(720, 5),
  ONE_DAY(1440, 1);  // 24시간 * 60분

  private final int minutes;
  private final int score;

  public static int calculateScore(LocalDateTime searchedAt, LocalDateTime now) {
    for (TimeScore timeScore : TimeScore.values()) {
      if (searchedAt.plusMinutes(timeScore.minutes).isAfter(now)) {
        return timeScore.score;
      }
    }
    return 0;
  }
}
