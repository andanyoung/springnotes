package cn.andyoung.swagger2.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户")
public class User {

  @ApiModelProperty(required = true, value = "id")
  public Integer id;

  @ApiModelProperty(required = true, value = "用户名")
  public String username;

  @ApiModelProperty(required = true, value = "密码")
  public String password;

  @ApiModelProperty(required = true, value = "年龄")
  public int age;
}
