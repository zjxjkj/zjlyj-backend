package com.zjlyj.entity.file;


public enum FileUrlType {

    PROJECT("project", "项目申报文件路径");

    /**
     * 路径
     */
    private String path;
    /**
     * 备注
     */
    private String remark;

    FileUrlType(String path, String remark) {
        this.path = path;
        this.remark = remark;
    }

    public String getPath() {
        return path;
    }

}
