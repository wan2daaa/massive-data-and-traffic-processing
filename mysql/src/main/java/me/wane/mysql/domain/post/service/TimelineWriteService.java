package me.wane.mysql.domain.post.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.post.entity.TimeLine;
import me.wane.mysql.domain.post.repository.TimelineRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TimelineWriteService {

  private final TimelineRepository timelineRepository;

  public void deliveryToTimeline(Long postId, List<Long> toMemberIds) {
    List<TimeLine> timeLines = toMemberIds.stream()
        .map(memberId -> toTimeline(postId, memberId)).toList();
    timelineRepository.bulkInsert(timeLines);
  }

  private static TimeLine toTimeline(Long postId, Long memberId) {
    return TimeLine.builder().memberId(memberId).postId(postId).build();
  }

}
