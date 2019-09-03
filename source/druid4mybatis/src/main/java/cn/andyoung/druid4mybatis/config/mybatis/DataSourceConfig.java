package cn.andyoung.druid4mybatis.config.mybatis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {
  @Value("${spring.datasource.druid.type}")
  private Class<? extends DataSource> dataSourceType;

  @Bean(name = "masterDataSource")
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.druid.master")
  public DataSource masterDataSource() {
    return DataSourceBuilder.create().type(dataSourceType).build();
  }

  @Bean(name = "slaveDataSource")
  @ConfigurationProperties(prefix = "druid.slave")
  public DataSource slaveDataSource1() {
    return DataSourceBuilder.create().type(dataSourceType).build();
  }
}
