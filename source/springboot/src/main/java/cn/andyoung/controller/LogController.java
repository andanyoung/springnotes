package cn.andyoung.controller;

import cn.andyoung.MySpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {
  private static Logger logger = LoggerFactory.getLogger(MySpringBootApplication.class);

  @GetMapping("/log")
  public String test() {
    logger.info("test logging...");
    return "hello";
  }
}
