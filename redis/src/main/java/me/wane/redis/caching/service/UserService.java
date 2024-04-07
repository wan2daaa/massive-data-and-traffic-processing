package me.wane.redis.caching.service;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import me.wane.redis.caching.dto.UserProfile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final ExternalApiService externalApiService;

  private final StringRedisTemplate stringRedisTemplate;

  public UserProfile getUserProfile(String userId) {

    String username = null;

    ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
    String cachedName = ops.get("nameKey:" + userId);
    if (cachedName != null) {
      username = cachedName;
    } else {
      username = externalApiService.getUsername(userId);
      ops.set("nameKey:" + userId, username, 5, TimeUnit.SECONDS);
    }

    int age = externalApiService.getUserAge(userId);
    return new UserProfile(username, age);
  }
}
