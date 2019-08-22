package cn.andyoung.controller;

import cn.andyoung.domain.User;
import cn.andyoung.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MapperController {
  @Autowired private UserMapper userMapper;

  @RequestMapping("/queryUser")
  @ResponseBody
  public List<User> queryUser() {
    List<User> users = userMapper.queryUserList();
    return users;
  }
}
