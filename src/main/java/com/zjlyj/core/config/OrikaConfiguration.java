package com.zjlyj.core.config;

import com.zjlyj.core.base.IdTool;
import com.zjlyj.core.base.OrikaBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OrikaConfiguration {

  @Bean
  public OrikaBeanMapper beanMapper() {
    return new OrikaBeanMapper();
  }


  @Bean
  public IdTool idTool() {
    return new IdTool();
  }
}