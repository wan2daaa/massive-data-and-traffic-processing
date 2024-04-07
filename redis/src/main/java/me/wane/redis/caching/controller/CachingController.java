package me.wane.redis.caching.controller;

import lombok.RequiredArgsConstructor;
import me.wane.redis.caching.dto.UserProfile;
import me.wane.redis.caching.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CachingController {

  private final UserService userService;

  @GetMapping("/users/{userId}/profile")
  public UserProfile getUserProfile(@PathVariable String userId) {
    return userService.getUserProfile(userId);
  }

}
