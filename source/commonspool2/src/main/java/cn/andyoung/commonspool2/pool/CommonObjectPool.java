package cn.andyoung.commonspool2.pool;

import cn.andyoung.commonspool2.domain.Student;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class CommonObjectPool extends GenericObjectPool<Student> {
  public CommonObjectPool(PooledObjectFactory<Student> factory) {
    super(factory);
  }

  public CommonObjectPool(
      PooledObjectFactory<Student> factory, GenericObjectPoolConfig<Student> config) {
    super(factory, config);
  }

  public CommonObjectPool(
      PooledObjectFactory<Student> factory,
      GenericObjectPoolConfig<Student> config,
      AbandonedConfig abandonedConfig) {
    super(factory, config, abandonedConfig);
  }
}
