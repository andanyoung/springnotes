package cn.andyoung.druid4mybatis.config.mybatis;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ReadWriteSplitRoutingDataSource extends AbstractRoutingDataSource {
  @Override
  protected Object determineCurrentLookupKey() {
    return DbContextHolder.getDbType();
  }
}
