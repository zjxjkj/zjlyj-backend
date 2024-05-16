package com.zjlyj.core.config;

import com.zjlyj.core.authc.ShiroRealm;
import com.zjlyj.core.authc.aop.JwtFilter;
import com.zjlyj.core.authc.cache.ShiroCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class ShiroConfig {

  @Value("${pcm.shiro.excludeUrls:}")
  private String excludeUrls;


  @Bean("shiroFilter")
  public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    shiroFilterFactoryBean.setSecurityManager(securityManager);

    // 拦截器
    Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
    if (!StringUtils.isEmpty(excludeUrls)) {
      String[] permissionUrl = excludeUrls.split(",");
      for (String url : permissionUrl) {
        filterChainDefinitionMap.put(url, "anon");
      }
    }
    // 配置不会被拦截的链接 顺序判断
    filterChainDefinitionMap.put("/", "anon");
    filterChainDefinitionMap.put("/druid/**", "anon");
    filterChainDefinitionMap.put("/swagger-ui.html", "anon");
    filterChainDefinitionMap.put("/doc.html", "anon");
    filterChainDefinitionMap.put("/swagger**/**", "anon");
    filterChainDefinitionMap.put("/webjars/**", "anon");
    filterChainDefinitionMap.put("/v2/**", "anon");

    //登录
    filterChainDefinitionMap.put("/login", "anon");
    //验证码相关
    filterChainDefinitionMap.put("/verify/**", "anon");

    // 添加自己的过滤器并且取名为jwt
    Map<String, Filter> filterMap = new HashMap<String, Filter>(1);
    filterMap.put("jwt", new JwtFilter());
    shiroFilterFactoryBean.setFilters(filterMap);

    // <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
    filterChainDefinitionMap.put("/server/**", "jwt");

    // 未授权界面返回JSON
    //TODO /403
    //shiroFilterFactoryBean.setUnauthorizedUrl("/403");
    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

    return shiroFilterFactoryBean;
  }

  @Bean("securityManager")
  public DefaultWebSecurityManager securityManager(ShiroRealm myRealm, ShiroCacheManager shiroCacheManager) {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(myRealm);

    DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
    DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
    defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
    subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
    securityManager.setSubjectDAO(subjectDAO);

    // 自定义缓存
    securityManager.setCacheManager(shiroCacheManager);

    return securityManager;
  }

  @Bean
  @DependsOn("lifecycleBeanPostProcessor")
  public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
    defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
    return defaultAdvisorAutoProxyCreator;
  }

  @Bean
  public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }

  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
    advisor.setSecurityManager(securityManager);
    return advisor;
  }

}
