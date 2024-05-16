package com.zjlyj.dto.file;

import com.alibaba.fastjson.annotation.JSONField;
import com.zjlyj.core.base.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("文件DTO")
public class FileDTO extends BaseDTO {

    @ApiModelProperty(value = "文件路径")
    @JSONField(name = "filePath")
    private String filePath;
    @ApiModelProperty(value = "文件名称")
    @JSONField(name = "fileName")
    private String fileName;

}
