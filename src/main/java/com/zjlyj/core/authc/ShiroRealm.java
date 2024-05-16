package com.zjlyj.core.authc;

import com.zjlyj.entity.user.UserEntity;
import com.zjlyj.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

  @Autowired
  @Lazy
  private IUserService userService;


  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof JwtToken;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    log.info("shiro doGetAuthorizationInfo");

    if (principals == null) {
      throw new AuthenticationException("非法登录!");
    }

    UserEntity pmAuthUser = (UserEntity) principals.getPrimaryPrincipal();

    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

    if (null == pmAuthUser) {
      throw new AuthenticationException("非法登录!");
    }

    log.info("shiro doGetAuthorizationInfo success");

    return info;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
    String token = (String) auth.getCredentials();

    if (token == null) {
      //TODO log
      throw new AuthenticationException("token 为空!");
    }

    // 校验token有效性
    UserEntity loginUser = this.checkUserTokenIsEffect(token);

    return new SimpleAuthenticationInfo(loginUser, token, getName());
  }

  public UserEntity checkUserTokenIsEffect(String token) throws AuthenticationException {
    // 解密获得account，用于和数据库进行对比
    String account = JwtUtil.getAccount(token);
    if (account == null) {
      throw new AuthenticationException("token 无效!");
    }
    UserEntity pmAuthUser = userService.selectByAccount(account);
    if (pmAuthUser == null) {
      throw new AuthenticationException("用户不存在!");
    }
    // 校验token是否超时失效 & 或者账号密码是否错误
    if (!JwtUtil.verify(token, account, pmAuthUser.getPassword())) {
      throw new AuthenticationException("Token失效，请重新登录!");
    }

    return pmAuthUser;
  }

  @Override
  public void clearCache(PrincipalCollection principals) {
    super.clearCache(principals);
  }

}