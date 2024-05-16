package com.zjlyj.core.authc.aop;

import com.zjlyj.core.authc.JwtToken;
import com.zjlyj.core.authc.ShiroAuthContants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

  @Override
  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
    //try {
    //TODO return true
    return executeLogin(request, response);
        /*}
        catch (Exception e) {
            throw new AuthenticationException("Token失效，请重新登录", e);
        }*/
  }

  @Override
  protected boolean executeLogin(ServletRequest request, ServletResponse response) {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String token = httpServletRequest.getHeader(ShiroAuthContants.X_ACCESS_TOKEN);

    JwtToken jwtToken = new JwtToken(token);

    // 提交给realm进行登入，如果错误他会抛出异常并被捕获
    getSubject(request, response).login(jwtToken);

    // 如果没有抛出异常则代表登入成功，返回true
    return true;
  }

  @Override
  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
    httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
    httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
    // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
    if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
      httpServletResponse.setStatus(HttpStatus.OK.value());
      return false;
    }
    return super.preHandle(request, response);
  }

    /*@Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception
    {
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        httpResponse.getWriter().write("{\"code\":401,\"msg\":\"未授权！\",\"ret\":false}");
        return false;
    }*/
}
