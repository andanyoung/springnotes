package cn.andyoung.druid4mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Druid4mybatisApplicationTests {

  @Autowired ApplicationContext applicationContext;

  @Test
  public void contextLoads() {
    DataSource dataSource = applicationContext.getBean(DataSource.class);
    System.out.println(dataSource);
  }
}
