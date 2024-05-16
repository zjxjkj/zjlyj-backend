package com.zjlyj.core.base;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 详细问题代码
 */
public enum BaseResultAttachCode {
  /**
   * 无权限
   */
  SELECT_NOT_EXIST("999");

  /**
   * 附加状态码
   */
  @JsonValue
  private final String code;

  /**
   * cons
   *
   * @param code 附加状态码
   */
  BaseResultAttachCode(String code) {
    this.code = code;
  }
}