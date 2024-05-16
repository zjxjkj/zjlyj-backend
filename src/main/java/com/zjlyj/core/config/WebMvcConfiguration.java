package com.zjlyj.core.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {


  @Bean
  public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration corsConfiguration = new CorsConfiguration();
    /* 是否允许请求带有验证信息 */
    corsConfiguration.setAllowCredentials(true);
    /* 允许访问的客户端域名 */
    corsConfiguration.addAllowedOrigin("*");
    /* 允许服务端访问的客户端请求头 */
    corsConfiguration.addAllowedHeader("*");
    /* 允许访问的方法名,GET POST等 */
    corsConfiguration.addAllowedMethod("HEAD");
    corsConfiguration.addAllowedMethod("DELETE");
    corsConfiguration.addAllowedMethod("PUT");
    corsConfiguration.addAllowedMethod("OPTIONS");
    corsConfiguration.addAllowedMethod("GET");
    corsConfiguration.addAllowedMethod("POST");
    corsConfiguration.addAllowedMethod("PATCH");
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
    return new CorsFilter(urlBasedCorsConfigurationSource);
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("doc.html");
  }


  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
    //自定义 fastJson 配置
    FastJsonConfig config = new FastJsonConfig();
    config.setSerializerFeatures(
        // 是否输出值为null的字段,默认为false,我们将它打开
        SerializerFeature.WriteMapNullValue,
        // 将Collection类型字段的字段空值输出为[]
        SerializerFeature.WriteNullListAsEmpty,
        // 将字符串类型字段的空值输出为空字符串
        SerializerFeature.WriteNullStringAsEmpty,
        // 将数值类型字段的空值输出为0
        SerializerFeature.WriteNullNumberAsZero,
        SerializerFeature.WriteDateUseDateFormat,
        // 禁用循环引用
        SerializerFeature.DisableCircularReferenceDetect
    );
    SerializeConfig serializeConfig = new SerializeConfig();
    serializeConfig.put(Long.class, ToStringSerializer.instance);
    serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
    config.setSerializeConfig(serializeConfig);
    fastJsonHttpMessageConverter.setFastJsonConfig(config);
    // 添加支持的MediaTypes;不添加时默认为*/*,也就是默认支持全部
    // 但是MappingJackson2HttpMessageConverter里面支持的MediaTypes为application/json
    // 参考它的做法, fastjson也只添加application/json的MediaType
    List<MediaType> fastMediaTypes = new ArrayList<>();
    fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
    fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
    converters.add(0, new ByteArrayHttpMessageConverter());
    converters.add(1, fastJsonHttpMessageConverter);
  }


  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(60)).setReadTimeout(Duration.ofSeconds(5 * 60)).build();
  }

  // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // addPathPatterns("/**") 表示拦截所有的请求，
    // excludePathPatterns("/login", "/register") 表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问
//    registry.addInterceptor(imageInterceptor).addPathPatterns("/**");
//    registry.addInterceptor(xsrfTokenInterceptor).addPathPatterns("/client/**").excludePathPatterns("/client/xsrf/token");
  }

}
