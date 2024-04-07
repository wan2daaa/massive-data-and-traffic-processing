package me.wane.redis.caching.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class ExternalApiService {

  public String getUsername(String userId) {
    // 외부 서비스나 DB 호출

    try {
      Thread.sleep(500);
    }catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    log.info("Getting username from other service...");

    if (userId.equals("A")) {
      return "Adam";
    }
    if (userId.equals("B")) {
      return "Bob";
    }

    return "";

  }

  @Cacheable(cacheNames = "userAgeCache", key = "#userId")
  public int getUserAge(String userId) {
    // 외부 서비스나 DB 호출

    try {
      Thread.sleep(500);
    }catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    log.info("Getting username from other service...");

    if (userId.equals("A")) {
      return 28;
    }
    if (userId.equals("B")) {
      return 32;
    }

    return 0;

  }


}
