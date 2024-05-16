package com.zjlyj.serviceImpl.file;

import cn.hutool.core.util.IdUtil;
import com.zjlyj.core.base.BaseServiceImpl;
import com.zjlyj.entity.file.FileEntity;
import com.zjlyj.entity.file.FileUrlType;
import com.zjlyj.entity.file.FilesConfig;
import com.zjlyj.mapper.IFileMapper;
import com.zjlyj.service.file.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class FileServiceImpl extends BaseServiceImpl<IFileMapper, FileEntity> implements IFileService {

    @Autowired
    private FilesConfig filesConfig;

    /**
     * 文件上传并将数据添加到数据库
     **/
    @Override
    public FileEntity upload(MultipartFile file, FileEntity asFile, FileUrlType FileUrlType) {
        this.fileUpload(file, asFile, FileUrlType);
        baseMapper.insert(asFile);
        return asFile;
    }

    private void fileUpload(MultipartFile file, FileEntity asFile, FileUrlType FileUrlType) {
        if (null != file && !file.isEmpty()) {
            Long size = filesConfig.getFileSize();
            if (file.getSize() > size) {
                throw new RuntimeException("上传限制" + Math.ceil(size / 1024.0 / 1024.0) + "M以内");
            }
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String newFileName = String.format("%s.%s", IdUtil.objectId(), suffix);
            SimpleDateFormat formatter = new SimpleDateFormat("yyMM");
            String filePath = new StringBuilder().append("/").append(FileUrlType.getPath()).append("/")
                    .append("/").append(formatter.format(new Date())).append("/")
                    .append(newFileName).toString();
            try {
                File newFile = new File(filesConfig.getUploadRoot() + filePath);
                if (!newFile.getParentFile().exists()) {
                    newFile.getParentFile().mkdirs();
                }
                file.transferTo(newFile.getAbsoluteFile());
            } catch (Exception e) {
                log.error("上传文件失败", e);
                throw new RuntimeException("上传失败，请联系管理员");
            }
            asFile.setFilePath(filePath);
            asFile.setFileName(originalFilename);
            asFile.setFileSize(file.getSize());
            asFile.setCreateTime(new Date());
        } else {
            log.error("文件为空");
        }
    }


}
