package cn.andyoung.swagger2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {
  @Value("${swagger2.enable}")
  private boolean swagger2Enable;

  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .enable(swagger2Enable)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("cn.andyoung.swagger2.controller"))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    Contact contact = new Contact("andyoung", "", "1218853253@qq.com");
    return new ApiInfoBuilder()
        .title("springboot利用swagger构建api文档")
        .description("简单优雅的restfun风格，https://github.com/AndyYoungCN/springnotes")
        .termsOfServiceUrl("https://github.com/AndyYoungCN/springnotes")
        .contact(contact)
        .version("1.0")
        .build();
  }
}
