package com.zjlyj.entity.file;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjlyj.core.base.BaseEntity;
import lombok.Data;

import java.util.Date;


@Data
@TableName("kl_file")
public class FileEntity extends BaseEntity {

    /**
     * 文件名称
     **/
    @TableField("file_name")
    private String fileName;
    /**
     * 文件大小(字节)
     **/
    @TableField("file_size")
    private Long fileSize;
    /**
     * 文件存储路径
     **/
    @TableField("file_path")
    private String filePath;
    /**
     * 上传时间
     **/
    @TableField("create_time")
    private Date createTime;

}
