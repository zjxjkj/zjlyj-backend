package com.zjlyj.core.base;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@NoArgsConstructor
public class ControllerUtil {

  public static <T> BaseResponse<T> setResultSuccess(String msg, T data) {
    return setResult(BaseResultCode.SUCCESS, msg, data);
  }

  public static <T> BaseResponse<T> setResultFault(String msg, T data) {
    return setResult(BaseResultCode.FAULT, msg, data);
  }

  @SuppressWarnings("rawtypes")
  public static BaseResponse setResultFault(String msg) {
    //noinspection unchecked
    return new BaseResponse(new BaseResult(BaseResultCode.FAULT, msg), null);
  }

  private static <T> BaseResponse<T> setResult(String code, String msg, T data) {
    return new BaseResponse<T>(new BaseResult(code, msg), data);
  }
}