package cn.andyoung.controller;

import cn.andyoung.dao.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JsonController {
  @RequestMapping("/testResponseJson")
  public @ResponseBody User testResponseJson(@RequestBody User user) {
    System.out.println("异步请求：" + user);
    return user;
  }
}
