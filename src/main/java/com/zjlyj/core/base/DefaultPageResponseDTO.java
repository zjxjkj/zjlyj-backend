package com.zjlyj.core.base;

import lombok.Data;

import java.util.List;


@Data
public class DefaultPageResponseDTO<T extends BaseDTO> {

  /**
   * 分页页码相关信息
   */
  private DefaultPageInfoDTO page;

  /**
   * 返回数据
   */
  private List<T> lists;
}