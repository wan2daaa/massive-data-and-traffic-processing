package me.wane.redis.sessionStore;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/redis")
public class LoginWithRedisController {

  @GetMapping("/login")
  public String login(HttpSession session, @RequestParam String name) {
    session.setAttribute("name", name);
    return "saved";
  }

  @GetMapping("/my-name")
  public String myName(HttpSession session) {
    String myName = session.getAttribute("name").toString();

    return myName;
  }
}
