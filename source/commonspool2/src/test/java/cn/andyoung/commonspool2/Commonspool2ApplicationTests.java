package cn.andyoung.commonspool2;

import cn.andyoung.commonspool2.Factory.StudentFactory;
import cn.andyoung.commonspool2.domain.Student;
import cn.andyoung.commonspool2.pool.CommonObjectPool;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Commonspool2ApplicationTests {

  @Test
  public void pool() throws Exception {
    StudentFactory studentFactory = new StudentFactory();
    GenericObjectPoolConfig<Student> studentGenericObjectPoolConfig =
        new GenericObjectPoolConfig<>();
    AbandonedConfig abandonedConfig = new AbandonedConfig();
    CommonObjectPool pool =
        new CommonObjectPool(studentFactory, studentGenericObjectPoolConfig, abandonedConfig);

    Student student;

    student = pool.borrowObject();
    System.out.println(student);
    if (student != null) pool.returnObject(student);
    System.out.println(student);
  }
}
