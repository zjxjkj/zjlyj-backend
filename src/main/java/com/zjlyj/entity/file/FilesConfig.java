package com.zjlyj.entity.file;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class FilesConfig {

  @Value("${upload.root:./upload}")
  private String uploadRoot;
  @Value("${upload.size:10737418240}")
  private Long fileSize;

}
