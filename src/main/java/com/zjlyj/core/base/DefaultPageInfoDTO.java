package com.zjlyj.core.base;

import lombok.Data;

@Data
public class DefaultPageInfoDTO {

  /**
   * 总记录条数
   */
  private Long totalCount;

  /**
   * 当前页码
   */
  private Long currentPage;
  /**
   * 每页显示记录条数
   */
  private Long pageSize;
}