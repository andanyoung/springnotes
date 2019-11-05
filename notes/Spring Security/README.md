# Spring Security

- 1、Java web 应用中安全框架使用率高的莫过于：

> spring-security：[https://spring.io/projects/spring-security](https://spring.io/projects/spring-security)
>
> Apache Shiro ：[http://shiro.apache.org/](http://shiro.apache.org/)

- 2、Spring Security 是 Spring 官网的顶级项目，与 spring boot、spring data、spring cloud 等齐名。

- 3、Spring Security 是一个专注于向 Java 应用程序提供身份验证和授权的安全框架，与所有 Spring 项目一样，Spring Security 的真正威力在于它可以很容易地扩展以满足定制需求。

> Spring Security is a framework that focuses on providing both authentication and authorization to Java applications. Like all Spring projects, the real power of Spring Security is found in how easily it can be extended to meet custom requirements

- 4、Spring Security  是  Spring Boot  底层安全模块默认的技术选型，可以实现强大的  web  安全控制，对于安全控制，仅需引入 spring-boot-starter-security  模块，进行少量的配置，即可实现强大的安全管理。

## Features（特性）

- 1）Comprehensive and extensible support for both Authentication and Authorization（全面和可扩展的身份验证和授权支持）

- 2）Protection against attacks like session fixation, clickjacking, cross site request forgery, etc（防止攻击，如会话固定，点击劫持，跨站请求伪造等）

- 3）Servlet API integration（Servlet API 的集成）

- 4）Optional integration with Spring Web MVC （与 Spring Web MVC 的可选集成）

## 认证 &  授权

- 1、应用程序安全的两个主要区域是“认证”和“授权”，spring security 的主要核心功能也是认证和授权。

### 认证（Authentication）

> 用户认证指的是验证某个用户是否为系统中的合法主体，即用户能否访问该系统。用户认证一般要求用户提供账号和密码，系统通过校验账号和密码来完成认证过程。

### 授权（Authorization）

> 用户授权指的是验证某个用户是否有权限执行某个操作。在一个系统中，不同用户所具有的权限通常是不同的，以一个为例，有的用户只能读，有的用户能读能写。一般来说，系统会为不同的用户分配不同的角色，而每个角色则对应一系列的权限。

## Web &  安全

- 1、登陆/注销

-HttpSecurity  配置登陆、注销功能

- 2、Thymeleaf  提供  SpringSecurity  标签支持

–需要引入 thymeleaf-extras-springsecurity4

–sec:authentication=“name”获得当前用户的用户名

–sec:authorize=“hasRole(‘ADMIN’)”当前用户必须拥有 ADMIN 权限时才会显示标签内容

- 3、remember me

–表单添加  remember-me  的  checkbox

–配置启用  remember-me  功能

- 4、CSRF（Cross-site request forgery）跨站请求伪造

-HttpSecurity  启用  csrf  功能，会为表单添加  _csrf  的值，提交携带来预防  CSRF

## 框架内容

> 众所周知   想要对对 Web 资源进行保护，最好的办法莫过于 Filter，要想对方法调用进行保护，最好的办法莫过于 AOP。所以 springSecurity 在我们进行用户认证以及授予权限的时候，通过各种各样的拦截器来控制权限的访问，从而实现安全。

如下为其主要过滤器

- WebAsyncManagerIntegrationFilter

* SecurityContextPersistenceFilter
* HeaderWriterFilter
* CorsFilter
* LogoutFilter
* RequestCacheAwareFilter
* SecurityContextHolderAwareRequestFilter
* AnonymousAuthenticationFilter
* SessionManagementFilter
* ExceptionTranslationFilter
* FilterSecurityInterceptor
* UsernamePasswordAuthenticationFilter
* BasicAuthenticationFilter

框架的核心组件

- SecurityContextHolder：提供对 SecurityContext 的访问
- SecurityContext,：持有 Authentication 对象和其他可能需要的信息
- AuthenticationManager 其中可以包含多个 AuthenticationProvider
- ProviderManager 对象为 AuthenticationManager 接口的实现类
- AuthenticationProvider 主要用来进行认证操作的类 调用其中的 authenticate()方法去进行认证操作
- Authentication：Spring Security 方式的认证主体
- GrantedAuthority：对认证主题的应用层面的授权，含当前用户的权限信息，通常使用角色表示
- UserDetails：构建 Authentication 对象必须的信息，可以自定义，可能需要访问 DB 得到
- UserDetailsService：通过 username 构建 UserDetails 对象，通过 loadUserByUsername 根据 userName 获取 UserDetail 对象 （可以在这里基于自身业务进行自定义的实现   如通过数据库，xml,缓存获取等）

## 具体目录

- [1.Spring Security 详细简绍与入门](./1.Spring%20Security%20详细简绍与入门.md)
- [2.用户注销与 Thymeleaf-权限控制](./2.用户注销与Thymeleaf-权限控制.md)
- [3.Spring Security 自定义用户认证](./3.Spring%20Security自定义用户认证.md)
- [4.Spring Security 添加图形验证码](./4.Spring%20Security添加图形验证码.md)
- [5.Spring Security 短信验证码登录.md](./5.Spring%20Security短信验证码登录.md)
- [6.Spring Security Session 管理.md](./6.Spring%20Security%20Session管理.md)
- [7.Spring Security 退出登录.md](./7.Spring%20Security退出登录.md)
- [8.Spring Security 权限控制.md](./8.Spring%20Security权限控制.md)
