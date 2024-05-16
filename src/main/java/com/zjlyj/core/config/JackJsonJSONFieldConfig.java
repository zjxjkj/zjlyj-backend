package com.zjlyj.core.config;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;


@Configuration
public class JackJsonJSONFieldConfig {

  @Autowired
  private ObjectMapper objectMapper;


  @Bean("jackson2ObjectMapperBuilderCustomizer")
  public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
    Jackson2ObjectMapperBuilderCustomizer customizer = new Jackson2ObjectMapperBuilderCustomizer() {
      @Override
      public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance)
            .serializerByType(Long.TYPE, ToStringSerializer.instance);
      }
    };
    return customizer;
  }

  @PostConstruct
  public void setAnnotationIntrospector() {
    objectMapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {

      @Override
      public boolean isAnnotationBundle(Annotation ann) {
        if (ann.annotationType() == JSONField.class) {
          return true;
        }
        return super.isAnnotationBundle(ann);
      }

      @Override
      public PropertyName findNameForSerialization(Annotated a) {
        PropertyName nameForSerialization = super.findNameForSerialization(a);
        if (nameForSerialization == null || nameForSerialization == PropertyName.USE_DEFAULT) {
          JSONField jsonField = _findAnnotation(a, JSONField.class);
          if (jsonField != null) {
            return PropertyName.construct(jsonField.name());
          }
        }
        return nameForSerialization;
      }

      @Override
      public PropertyName findNameForDeserialization(Annotated a) {
        PropertyName nameForDeserialization = super.findNameForDeserialization(a);
        if (nameForDeserialization == null || nameForDeserialization == PropertyName.USE_DEFAULT) {
          JSONField jsonField = _findAnnotation(a, JSONField.class);
          if (jsonField != null) {
            return PropertyName.construct(jsonField.name());
          }
        }
        return nameForDeserialization;
      }
    });
  }
}
