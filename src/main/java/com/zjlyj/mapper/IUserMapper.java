package com.zjlyj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjlyj.entity.user.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserMapper extends BaseMapper<UserEntity> {
}
