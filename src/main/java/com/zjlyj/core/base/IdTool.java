package com.zjlyj.core.base;

import cn.hutool.core.lang.Snowflake;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

public class IdTool {

  @Value("${id.worker:1}")
  private int workerId;
  @Value("${id.datacenter:1}")
  private int datacenterId;

  private Snowflake snowflake;

  @PostConstruct
  private void post() {
    this.snowflake = new Snowflake(workerId, datacenterId);
  }

  public Long createId() {
    return this.snowflake.nextId();
  }

  public String createStrId() {
    return String.valueOf(this.createId());
  }
}
