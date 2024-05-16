package com.zjlyj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjlyj.entity.file.FileEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFileMapper extends BaseMapper<FileEntity> {

    /**
     * 批量删除文件
     **/
    void deleteByUrls(@Param("fileUrls") List<String> fileUrls);

    /**
     *  根据路径集合查询文件信息
     **/
    List<FileEntity> selectByUrls(@Param("fileUrls") List<String> fileUrls);


}
