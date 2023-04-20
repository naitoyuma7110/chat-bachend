package com.naito.techpit.chat.chatbachend.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  // @RequestMapping(path = "/hello", method = RequestMethod.GET)
  @GetMapping("/hello")
  public String hello() {
    return "Hello, world!";
  }
}
