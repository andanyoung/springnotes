package cn.andyoung.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller("session")
@SessionAttributes(
    value = {"username", "password"},
    types = {Integer.class})
public class SessionController {
  /**
   * 把数据存入 SessionAttribute
   *
   * @param model
   * @return Model 是 spring 提供的一个接口，该接口有一个实现类 ExtendedModelMap 该类继承了 ModelMap，而 ModelMap 就是
   *     LinkedHashMap 子类
   */
  @RequestMapping("/testPut")
  public String testPut(Model model) {
    model.addAttribute("username", "泰斯特");
    model.addAttribute("password", "123456");
    model.addAttribute("age", 31);
    // 跳转之前将数据保存到 username、password 和 age 中，因为注解@SessionAttribute 中有这几个参数
    return "success";
  }

  @RequestMapping("/testGet")
  public String testGet(ModelMap model) {
    System.out.println(
        model.get("username") + ";" + model.get("password") + ";" + model.get("age"));
    return "success";
  }

  @RequestMapping("/testClean")
  public String complete(SessionStatus sessionStatus) {
    sessionStatus.setComplete();
    return "success";
  }
}
