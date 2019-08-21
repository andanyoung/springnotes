package cn.andyoung.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReturnController {

  /**
   * 返回 ModeAndView
   *
   * @return
   */
  @RequestMapping("/testReturnModelAndView")
  public ModelAndView modelAndView() {
    ModelAndView mv = new ModelAndView();
    // 添加模型到对象中
    mv.addObject("username", "张三");
    // 用于设置逻辑视图的名称，视图解析器会根据名称前往指定的视图
    mv.setViewName("return");
    return mv;
  }

  /**
   * 转发
   *
   * @return
   */
  @RequestMapping("/testForward")
  public String testForward() {
    System.out.println("ReturnController 的 testForward 方法执行了。。。。");
    return "forward:/WEB-INF/pages/success.jsp";
  }

  /**
   * 重定向
   *
   * @return
   */
  @RequestMapping("/testRedirect")
  public String testRedirect() {
    System.out.println("ReturnController 的 testRedirect 方法执行了。。。。");
    return "redirect:testReturnModelAndView";
  }
}
