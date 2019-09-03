package cn.andyoung.druid4mybatis.config.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.aspectj.apache.bcel.util.ClassLoaderRepository;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
public class MybatisConfiguration {
  private static Log logger = LogFactory.getLog(MybatisConfiguration.class);

  @Resource(name = "masterDataSource")
  private DataSource masterDataSource;

  @Resource(name = "slaveDataSource")
  private DataSource slaveDataSource;

  @Bean
  public SqlSessionFactory sqlSessionFactory(@Autowired MybatisAutoConfiguration configuration)
      throws Exception {
    return configuration.sqlSessionFactory(roundRobinDataSouceProxy());
  }

  public AbstractRoutingDataSource roundRobinDataSouceProxy() {
    ReadWriteSplitRoutingDataSource proxy = new ReadWriteSplitRoutingDataSource();
    Map<Object, Object> targetDataResources = new ClassLoaderRepository.SoftHashMap();
    targetDataResources.put(DbContextHolder.DbType.MASTER, masterDataSource);
    targetDataResources.put(DbContextHolder.DbType.SLAVE, slaveDataSource);
    // 默认源
    proxy.setDefaultTargetDataSource(masterDataSource);
    proxy.setTargetDataSources(targetDataResources);
    return proxy;
  }
}
