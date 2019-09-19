package cn.andyoung.swagger2.controller;

import cn.andyoung.swagger2.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

@RestController
@Api(
    value = "用户api",
    tags = {"用户"},
    description = "用户相关接口")
@RequestMapping("/user")
public class UserController {

  Map<Integer, User> users = Collections.synchronizedMap(new HashMap<>());

  @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
  @GetMapping("")
  public List<User> getUsers() {
    return new ArrayList<>(users.values());
  }

  @ApiOperation(value = "获取用户", notes = "根据用户id获取获取列表")
  @GetMapping("/{id}")
  @ApiImplicitParam(
      name = "id",
      value = "用户id",
      required = true,
      dataType = "int",
      paramType = "path")
  public User getUserById(@PathVariable Integer id) {
    return users.get(id);
  }

  @ApiOperation(value = "添加用户", notes = "添加用户")
  @PostMapping("")
  public String postUser(@RequestBody User user) {
    users.put(user.id, user);
    return "success";
  }

  @ApiOperation(value = "更新信息", notes = "根据url的id来指定更新用户信息")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "id",
        value = "用户id",
        required = true,
        dataType = "int",
        paramType = "path"),
    @ApiImplicitParam(name = "user", value = "用户info", required = true, dataType = "User")
  })
  @PutMapping("/{id}")
  public String put(@PathVariable Integer id, @RequestBody User user) {
    User user1 = users.get(id);
    user1.username = user.username;
    user1.password = user.password;
    return "修改成功";
  }

  @ApiOperation(value = "删除用户", notes = "根据url的id来删除用户信息")
  @ApiImplicitParam(name = "user", value = "用户info", required = true, dataType = "User")
  @DeleteMapping("/{id}")
  public String delete(@PathVariable Integer id) {
    users.remove(id);
    return "删除成功";
  }

  @ApiIgnore
  @RequestMapping("/ig")
  public String jsonIgnore() {
    return "jsonIgnore";
  }
}
