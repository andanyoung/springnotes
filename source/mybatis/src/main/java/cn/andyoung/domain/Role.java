package cn.andyoung.domain;

import java.util.List;

public class Role {
  private Integer roleId;
  private String roleName;
  private String roleDesc;

  // 多对多的关系映射：一个角色可以赋予多个用户
  private List<User> users;

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  @Override
  public String toString() {
    return "Role{" + "roleId=" + roleId + ", roleName='" + roleName + '\'' + '}';
  }

  public String getRoleDesc() {
    return roleDesc;
  }

  public void setRoleDesc(String roleDesc) {
    this.roleDesc = roleDesc;
  }
}
