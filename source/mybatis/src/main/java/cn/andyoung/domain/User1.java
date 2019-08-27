package cn.andyoung.domain;

import java.util.Date;

public class User1 {

  private Integer userId;
  private String userName;
  private Date userBirthday;
  private String userSex;
  private String userAddress;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Date getUserBirthday() {
    return userBirthday;
  }

  public void setUserBirthday(Date userBirthday) {
    this.userBirthday = userBirthday;
  }

  public String getUserSex() {
    return userSex;
  }

  public void setUserSex(String userSex) {
    this.userSex = userSex;
  }

  public String getUserAddress() {
    return userAddress;
  }

  public void setUserAddress(String userAddress) {
    this.userAddress = userAddress;
  }

  @Override
  public String toString() {
    return "User1{"
        + "userId="
        + userId
        + ", userName='"
        + userName
        + '\''
        + ", userBirthday="
        + userBirthday
        + ", userSex='"
        + userSex
        + '\''
        + ", userAddress='"
        + userAddress
        + '\''
        + '}';
  }
}
