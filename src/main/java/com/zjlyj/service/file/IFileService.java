package com.zjlyj.service.file;


import com.zjlyj.core.base.BaseService;
import com.zjlyj.entity.file.FileEntity;
import com.zjlyj.entity.file.FileUrlType;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService extends BaseService<FileEntity> {

    /**
     * 文件上传并将数据添加到数据库
     **/
    FileEntity upload(MultipartFile file, FileEntity fileEntity, FileUrlType FileUrlType);



}
