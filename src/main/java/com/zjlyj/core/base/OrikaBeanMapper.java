package com.zjlyj.core.base;

import ma.glasnost.orika.Converter;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

public class OrikaBeanMapper extends ConfigurableMapper implements ApplicationContextAware {

  private MapperFactory factory;
  private ApplicationContext applicationContext;

  public OrikaBeanMapper() {
    super(false);
  }

  @Override
  protected void configure(MapperFactory factory) {
    this.factory = factory;
    this.addAllSpringBeans(this.applicationContext);
  }

  private void addMapper(Mapper<?, ?> mapper) {
    this.factory.registerMapper(mapper);
  }

  public void addConverter(Converter<?, ?> converter) {
    this.factory.getConverterFactory().registerConverter(converter);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
    this.init();
  }

  @SuppressWarnings("rawtypes")
  private void addAllSpringBeans(ApplicationContext applicationContext) {
    Map<String, Mapper> mappers = applicationContext.getBeansOfType(Mapper.class);
    Iterator mapperIterator = mappers.values().iterator();

    if (mapperIterator.hasNext()) {
      do {
        Mapper mapper = (Mapper) mapperIterator.next();
        this.addMapper(mapper);
      } while (mapperIterator.hasNext());
    }

    Map<String, Converter> converters = applicationContext.getBeansOfType(Converter.class);
    Iterator converterIterator = converters.values().iterator();

    if (converterIterator.hasNext()) {
      do {
        Converter converter = (Converter) converterIterator.next();
        this.addConverter(converter);
      } while (converterIterator.hasNext());
    }

  }
}