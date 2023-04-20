package com.naito.techpit.chat.chatbachend.app.controller;

import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.naito.techpit.chat.chatbachend.domain.hello.model.Hello;

@RestController
public class HelloController {

  @GetMapping("/hello")
  public Hello hello(@RequestParam("name") Optional<String> name) {
    //  Optional:null許容
    //  orElse:optionalインスタンスがnullでない場合はその値を返す
    String resName = name.orElse("world!");
    return new Hello("Hello, " + resName);
  }
}
