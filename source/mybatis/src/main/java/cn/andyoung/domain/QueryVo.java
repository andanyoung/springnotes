package cn.andyoung.domain;

import java.util.List;

public class QueryVo {
  private User user;

  private List<Integer> ids;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<Integer> getIds() {
    return ids;
  }

  public void setIds(List<Integer> ids) {
    this.ids = ids;
  }
}
