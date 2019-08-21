# SpringMVC中的拦截器
## 拦截器的作用
- Spring MVC 的处理器拦截器类似于 Servlet 开发中的过滤器 Filter，用于对处理器进行预处理和后处理。
- 用户可以自己定义一些拦截器来实现特定的功能。序联结成一条链。在访问被拦截的方法或字段时，拦截器链中的拦截器就会按其之前定义的顺序被调用。
说到这里，可能大家脑海中有了一个疑问，这不是我们之前学的过滤器吗？是的它和过滤器是有几分相似，但是也有区别，接下来我们就来说说他们的区别：
  - 过滤器是 servlet 规范中的一部分，任何 java web 工程都可以使用。
  - 拦截器是 SpringMVC 框架自己的，只有使用了 SpringMVC 框架的工程才能用。
  - 过滤器在 url-pattern 中配置了/*之后，可以对所有要访问的资源拦截。
  - 拦截器它是只会拦截访问的控制器方法，如果访问的是 jsp，html,css,image 或者 js 是不会进行拦截的。
> 它也是 AOP 思想的具体应用。
我们要想自定义拦截器， 要求必须实现：HandlerInterceptor 接口。
## 自定义拦截器的步骤
### 第一步：编写一个普通类实现 HandlerInterceptor 接口
```
public class MyInterceptor1 implements HandlerInterceptor {
  /**
   * 预处理，controller方法执行前 return true 放行，执行下一个拦截器，如果没有，执行controller中的方法 return false不放行
   *
   * @param request
   * @param response
   * @param handler
   * @return
   * @throws Exception
   */
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    System.out.println("MyInterceptor1执行了...前1111");
    // request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request,response);
    return true;
  }

  /**
   * 后处理方法，controller方法执行后，success.jsp执行之前
   *
   * @param request
   * @param response
   * @param handler
   * @param modelAndView
   * @throws Exception
   */
  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView)
      throws Exception {
    System.out.println("MyInterceptor1执行了...后1111");
    // request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request,response);
  }

  /**
   * success.jsp页面执行后，该方法会执行
   *
   * @param request
   * @param response
   * @param handler
   * @param ex
   * @throws Exception
   */
  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    System.out.println("MyInterceptor1执行了...最后1111");
  }
}
```
### 第二步：配置拦截器
```
<!-- 配置拦截器 -->
    <mvc:interceptors>
        <mvc:interceptor>
            <!--不要拦截的方法
           <mvc:exclude-mapping path=""/>
           -->
            <mvc:mapping path="/**"/>
            <bean id="handlerInterceptorDemo1"
                  class="cn.andyoung.interceptor.MyInterceptor2"></bean>
        </mvc:interceptor>
        <mvc:interceptor>
            <!--不要拦截的方法
           <mvc:exclude-mapping path=""/>
           -->
            <mvc:mapping path="/**"/>

            <bean id="handlerInterceptorDemo2"
                  class="cn.andyoung.interceptor.MyInterceptor2"></bean>
        </mvc:interceptor>
    </mvc:interceptors>
```
### 