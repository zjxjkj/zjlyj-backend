package com.zjlyj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableScheduling
@EnableSwagger2
@EnableCaching
@EnableAsync
@ServletComponentScan
@ComponentScan(basePackages = {"com.zjlyj.**","com.zjlyj.**.**","com.zjlyj.**.**.**"})
@MapperScan(basePackages = {"com.zjlyj.**","com.zjlyj.**.**","com.zjlyj.**.**.**"})
@SpringBootApplication
public class ZjlyjApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZjlyjApplication.class, args);
    }

}
