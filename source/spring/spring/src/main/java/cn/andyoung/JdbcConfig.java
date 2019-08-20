package cn.andyoung;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class JdbcConfig {
  @Value("${jdbc.driver}")
  private String driver;

  @Value("${jdbc.url}")
  private String url;

  @Value("${jdbc.username}")
  private String username;

  @Value("${jdbc.password}")
  private String password;
  /** * 创建一个数据源，并存入 spring 容器中* @return */
  @Bean(name = "dataSource")
  public DataSource createDataSource() {

    return null;
  }
}
