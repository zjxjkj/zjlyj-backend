package com.zjlyj.service.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjlyj.core.base.BaseService;
import com.zjlyj.entity.user.UserEntity;

public interface IUserService extends BaseService<UserEntity> {

    /**
     * 根据账号查询用户信息
     **/
    UserEntity selectByAccount(String account);

    /**
     * @Author: DongYuZhi
     * @Date: 2021/10/18 16:03
     * @Description: 分页查询用户信息
     **/
    IPage<UserEntity> page(String account,Integer currentPage,Integer pageSize);

    /**
     * 用户信息新增
     **/
    boolean add(UserEntity userEntity);

    /**
     * 用户信息修改
     **/
    boolean update(UserEntity userEntity);

}
