package com.zjlyj.core.base;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 定义的统一返回对象
 */
@Data
@NoArgsConstructor
public class BaseResponse<T>
{
    /** 请求状态 */
    private BaseResult result;

    /** 请求结果 */
    private T body;

    /**
     * cons
     * @param result {@link BaseResult}
     * @param body 返回结果
     */
    public BaseResponse(BaseResult result, T body)
    {
        this.result = result;
        this.body = body;
    }

}