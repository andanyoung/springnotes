package cn.andyoung.controller;

import cn.andyoung.dao.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller("helloController")
public class HelloController {

  @RequestMapping("/hello")
  public String hello() {
    System.out.println("HELLO WORLD");
    return "success";
  }

  @RequestMapping("/testServletAPI")
  public String testServletAPI(
      HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    System.out.println(request);
    System.out.println(response);
    System.out.println(session);
    return "success";
  }

  @RequestMapping("/useRequestParam")
  public String useRequestParam(
      @RequestParam("name") String username,
      @RequestParam(value = "age", required = false) Integer age) {
    System.out.println(username + "," + age);
    return "success";
  }

  @RequestMapping("/useRequestBody")
  public String useRequestBody(@RequestBody(required = false) String body) {
    System.out.println(body);
    return "success";
  }

  @RequestMapping("/usePathVariable/{id}")
  public String usePathVariable(@PathVariable("id") Integer id) {
    System.out.println(id);
    return "success";
  }

  /**
   * RequestHeader 注解
   *
   * @param user
   * @return
   */
  @RequestMapping("/useRequestHeader")
  public String useRequestHeader(
      @RequestHeader(value = "Accept-Language", required = false) String requestHeader) {
    System.out.println(requestHeader);
    return "success";
  }

  /**
   * Cookie 注解注解
   *
   * @return
   */
  @RequestMapping("/useCookieValue")
  public String useCookieValue(
      @CookieValue(value = "JSESSIONID", required = false) String cookieValue) {
    System.out.println(cookieValue);
    return "success";
  }

  /**
   * 被 ModelAttribute 修饰的方法
   *
   * @param user
   */
  @ModelAttribute
  public void showModel(User user) {
    System.out.println("执行了 showModel 方法" + user.getUsername());
  }
  /**
   * 接收请求的方法
   *
   * @param user
   * @return
   */
  @RequestMapping("/testModelAttribute")
  public String testModelAttribute(User user) {
    System.out.println("执行了控制器的方法" + user.getUsername());
    return "success";
  }

  @ModelAttribute
  public User showModel(String username) {
    // 模拟去数据库查询
    User abc = findUserByName(username);
    System.out.println("执行了 showModel 方法" + abc);
    return abc;
  }
  /**
   * 模拟修改用户方法
   *
   * @param user
   * @return
   */
  @RequestMapping("/updateUser")
  public String updateModelAttribute(User user) {
    System.out.println("控制器中处理请求的方法：修改用户：" + user);
    return "success";
  }
  /**
   * 模拟去数据库查询
   *
   * @param username
   * @return
   */
  private User findUserByName(String username) {
    User user = new User();
    user.setUsername(username);
    return user;
  }

  /**
   * 查询数据库中用户信息
   *
   * @param user
   */
  @ModelAttribute
  public void showModel(String username, Map<String, User> map) {
    // 模拟去数据库查询
    User user = findUserByName1(username);
    System.out.println("执行了 showModel 方法" + user);
    map.put("abc", user);
  }
  /**
   * 模拟修改用户方法
   *
   * @param user
   * @return
   */
  @RequestMapping("/updateUser1")
  public String testModelAttribute1(@ModelAttribute("abc") User user) {
    System.out.println("控制器中处理请求的方法：修改用户：" + user);
    return "success";
  }
  /**
   * 模拟去数据库查询
   *
   * @param username
   * @return
   */
  private User findUserByName1(String username) {
    User user = new User();
    user.setUsername(username);
    user.setAge(19);
    user.setPassword("123456"); // 不设置密码
    return user;
  }
}
