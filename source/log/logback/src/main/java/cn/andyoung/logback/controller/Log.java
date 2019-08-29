package cn.andyoung.logback.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class Log {

  private static final Logger logger = LoggerFactory.getLogger(Log.class);

  @RequestMapping("/debug")
  public String debug() {
    logger.trace("trace 级别的日志");
    logger.debug("debug 级别的日志");
    logger.info("info 级别的日志");
    logger.warn("warn 级别的日志");
    logger.error("error 级别的日志");
    return "debug";
  }
}
