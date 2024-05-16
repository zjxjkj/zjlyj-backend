package com.zjlyj.core.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.zjlyj.core.authc.ShiroAuthContants;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
@Profile({"dev", "test"})
public class Swagger2Config implements WebMvcConfigurer {

  private static List<Parameter> pars = new ArrayList<Parameter>(1);

  static {
    ParameterBuilder ticketPar = new ParameterBuilder();
    ticketPar.name(ShiroAuthContants.X_ACCESS_TOKEN).description("token")
        .modelRef(new ModelRef("string")).parameterType("header")
        .required(false).defaultValue("").build();
    pars.add(ticketPar.build());
  }


  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {

    registry.addResourceHandler("/**").addResourceLocations(
            "classpath:/static/");
    registry.addResourceHandler("swagger-ui.html").addResourceLocations(
            "classpath:/META-INF/resources/");
    registry.addResourceHandler("doc.html").addResourceLocations(
            "classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**").addResourceLocations(
            "classpath:/META-INF/resources/webjars/");
    WebMvcConfigurer.super.addResourceHandlers(registry);
  }


    @Bean
  public Docket loginServerApi() {
    return new Docket(DocumentationType.SWAGGER_2)
            .groupName("文件接口")
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.zjlyj.controller.file"))
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            .paths(PathSelectors.any())
            .build()
            .globalOperationParameters(pars);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("zjlyj api")
        .version("1.0")
        .description("API接口")
        .build();
  }

  // 自定义 swagger 扫描多个包
  private static final String splitor = ";";

  @SuppressWarnings("all")
  public static Predicate<RequestHandler> basePackage(final String basePackage) {
    return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
  }

  @SuppressWarnings("all")
  private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
    return input -> {
      // 循环判断匹配
      for (String strPackage : basePackage.split(splitor)) {
        boolean isMatch = input.getPackage().getName().startsWith(strPackage);
        if (isMatch) {
          return true;
        }
      }
      return false;
    };
  }

  @SuppressWarnings("all")
  private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
    return Optional.fromNullable(input.declaringClass());
  }

}
