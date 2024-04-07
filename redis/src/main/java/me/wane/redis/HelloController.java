package me.wane.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HelloController {

  private final StringRedisTemplate stringRedisTemplate;

  StringRedisTemplate redisTemplate;

  // /setFruit?name=banana
  // /getFruit

  @GetMapping("/setFruit")
  public String setFruit(@RequestParam String name) {
    ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
    ops.set("fruit", name);

    return "saved";
  }

  @GetMapping("/getFruit")
  public String getFruit() {
    ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

    return ops.get("fruit");
  }


}
