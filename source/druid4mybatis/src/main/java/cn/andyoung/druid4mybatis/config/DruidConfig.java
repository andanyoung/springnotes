package cn.andyoung.druid4mybatis.config;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.support.http.StatViewServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

// @Configuration
public class DruidConfig {
  private static final Logger logger = LoggerFactory.getLogger(DruidConfig.class);

  @Bean
  public ServletRegistrationBean druidServlet() {
    logger.info("init Druid Servlet Configuration");
    ServletRegistrationBean servletRegistrationBean =
        new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    // IP白名单
    servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
    // IP黑名单(共同存在时，deny优先于allow)
    servletRegistrationBean.addInitParameter("deny", "192.168.1.100");
    // 控制台管理用户
    servletRegistrationBean.addInitParameter("loginUsername", "admin");
    servletRegistrationBean.addInitParameter("loginPassword", "123456");
    // 是否能够重置数据 禁用HTML页面上的“Reset All”功能
    servletRegistrationBean.addInitParameter("resetEnable", "true");
    return servletRegistrationBean;
  }

  //  @Bean
  public Slf4jLogFilter logFilter() {
    Slf4jLogFilter filter = new Slf4jLogFilter();
    filter.setStatementCloseAfterLogEnabled(true);
    //    filter.setResultSetLogEnabled(true);
    //    filter.setConnectionLogEnabled(false);
    //    filter.setStatementParameterClearLogEnable(false);
    //    filter.setStatementCreateAfterLogEnabled(false);
    //    filter.setStatementCloseAfterLogEnabled(false);
    //    filter.setStatementParameterSetLogEnabled(false);
    //    filter.setStatementPrepareAfterLogEnabled(false);
    return filter;
  }

  //
  //  @ConfigurationProperties(prefix = "spring.druid")
  //  @Bean(initMethod = "init", destroyMethod = "close")
  //  public DruidDataSource dataSource() {
  //    DruidDataSource dataSource = new DruidDataSource();
  //    dataSource.setProxyFilters(Lists.newArrayList(statFilter()));
  //    return dataSource;
  //  }
}
