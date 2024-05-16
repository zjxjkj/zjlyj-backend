package com.zjlyj.controller.file;

import com.zjlyj.core.base.BaseController;
import com.zjlyj.core.base.BaseResponse;
import com.zjlyj.dto.file.FileDTO;
import com.zjlyj.entity.file.FileEntity;
import com.zjlyj.entity.file.FileUrlType;
import com.zjlyj.service.file.IFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(value = "文件上传下载")
@RestController
@RequestMapping("/file")
public class FileController extends BaseController<FileDTO> {

    private IFileService fileService;

    @ApiOperation(value = "文件上传")
    @PostMapping(value = "/upload")
    public BaseResponse<FileDTO> upload(HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestParam("urlType") FileUrlType FileUrlType) {
        FileEntity fileEntity = new FileEntity();
        try {
            fileEntity = fileService.upload(file, fileEntity,FileUrlType);
            if (null != fileEntity) {
                return setResultSuccess("文件上传成功", beanMapper.map(fileEntity, FileDTO.class));
            }
            return setResultFault("文件上传失败");
        } catch (Exception e) {
            log.error("上传文件失败", e);
            e.printStackTrace();
            return setResultFault("文件上传失败");
        }
    }




}
