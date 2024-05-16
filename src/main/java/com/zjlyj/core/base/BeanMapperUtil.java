package com.zjlyj.core.base;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BeanMapperUtil {

  OrikaBeanMapper orikaBeanMapper;


  public BeanMapperUtil(OrikaBeanMapper orikaBeanMapper) {
    this.orikaBeanMapper = orikaBeanMapper;
  }


  public <D> D map(Object source, Class<D> destinationClass) {
    return orikaBeanMapper.map(source, destinationClass);
  }


  public <S, D> List<D> mapAsList(Iterable<S> source, Class<D> destinationClass) {
    return orikaBeanMapper.mapAsList(source, destinationClass);
  }
}