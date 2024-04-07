package me.wane.redis.sessionStore;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  /**
   * 1. login API /login?name=wane
   * 2. 이름을 가져오는 API /my-name => wane
   */

  HashMap<String, String> sessionMap = new HashMap<>();

  @GetMapping("/login")
  public String login(HttpSession session, @RequestParam String name) {
    sessionMap.put(session.getId(), name);

    return "saved";
  }

  @GetMapping("/my-name")
  public String myName(HttpSession session) {
    String myName = sessionMap.get(session.getId());

    return myName;
  }
}
