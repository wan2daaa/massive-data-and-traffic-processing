package me.wane.mysql.application.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.post.dto.DailyPostCount;
import me.wane.mysql.domain.post.dto.DailyPostCountRequest;
import me.wane.mysql.domain.post.dto.PostCommand;
import me.wane.mysql.domain.post.service.PostReadService;
import me.wane.mysql.domain.post.service.PostWriteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

  private final PostWriteService postWriteService;
  private final PostReadService postReadService;


  @PostMapping
  public Long create(PostCommand command) {
    return postWriteService.create(command);
  }

  @GetMapping("/daily-post-counts")
  public List<DailyPostCount> getDailyPostCounts(DailyPostCountRequest request) {
    return postReadService.getDailyPostCount(request);
  }

}
