package me.wane.mysql.domain.post.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.post.dto.DailyPostCount;
import me.wane.mysql.domain.post.dto.DailyPostCountRequest;
import me.wane.mysql.domain.post.entity.Post;
import me.wane.mysql.domain.post.entity.TimeLine;
import me.wane.mysql.domain.post.repository.PostRepository;
import me.wane.mysql.domain.post.repository.TimelineRepository;
import me.wane.mysql.util.CursorRequest;
import me.wane.mysql.util.PageCursor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TimelineReadService {

  private final TimelineRepository timelineRepository;

  public PageCursor<TimeLine> getTimeLines(Long memberId, CursorRequest cursorRequest) {
    List<TimeLine> timelines = findAllBy(memberId, cursorRequest);
    long nextKey = timelines.stream()
        .mapToLong(TimeLine::getId)
        .min().orElse(CursorRequest.NONE_KEY);
    return new PageCursor<>(cursorRequest.next(nextKey), timelines);
  }


  private List<TimeLine> findAllBy(Long memberId, CursorRequest cursorRequest) {
    if (cursorRequest.hasKey()) {
      return timelineRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(
          cursorRequest.key(), memberId, cursorRequest.size());
    }
    return timelineRepository.findAllByMemberIdOrderByIdDesc(
        memberId, cursorRequest.size());
  }

}
