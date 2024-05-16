package com.zjlyj.serviceImpl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjlyj.core.base.BaseServiceImpl;
import com.zjlyj.entity.user.UserEntity;
import com.zjlyj.mapper.IUserMapper;
import com.zjlyj.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<IUserMapper, UserEntity> implements IUserService {


    /**
     * 根据账号查询用户信息
     **/
    @Override
    public UserEntity selectByAccount(String account) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ku_account", account);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 分页查询用户信息
     **/
    @Override
    public IPage<UserEntity> page(String account, Integer currentPage, Integer pageSize) {
        Page<UserEntity> page = new Page<>(currentPage, pageSize);
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(account)){
            queryWrapper.eq("ku_account", account);
        }
        return this.page(page, queryWrapper);
    }

    /**
     * 用户信息新增
     **/
    @Override
    public boolean add(UserEntity userEntity) {
        userEntity.setPhone(userEntity.getAccount());
        return baseMapper.insert(userEntity) > 0;
    }

    /**
     * 用户信息修改
     **/
    @Override
    public boolean update(UserEntity userEntity) {
        return baseMapper.updateById(userEntity) > 0;
    }


}
