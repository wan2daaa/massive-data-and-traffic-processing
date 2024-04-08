//package me.wane.redis.PubSubChat;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@RequiredArgsConstructor
//@SpringBootApplication
//public class PubSubRunner implements CommandLineRunner {
//
//
//  private final ChatService chatService;
//
//  public static void main(String[] args) {
//    SpringApplication.run(PubSubRunner.class, args);
//  }
//  @Override
//  public void run(String... args) throws Exception {
//    System.out.println("Application started...");
//
//    chatService.enterChatRoom("chat1");
//  }
//}
