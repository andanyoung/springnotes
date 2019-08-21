package cn.andyoung.exception;

public class SysException extends Exception {
  private String message;

  public String getMessage() {
    return message;
  }

  public SysException(String message) {
    this.message = message;
  }
}
