package com.zjlyj.core.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口请求状态结果
 */
@Data
@NoArgsConstructor
@ApiModel(value = "接口请求状态结果")
public class BaseResult
{
    /** 操作结果 2:失败 1:成功 */
    @ApiModelProperty(value = "操作结果 2:失败 1:成功")
    private String code;

    /** 操作信息 */
    @ApiModelProperty(value = "信息")
    private String msg;

    /** 附加代码  */
    @ApiModelProperty(value = "详细的问题代码")
    private BaseResultAttachCode attcode;

    public BaseResult(String code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public BaseResult(String code, String msg, BaseResultAttachCode attcode)
    {
        this.code = code;
        this.msg = msg;
        this.attcode = attcode;
    }
}