package com.zjlyj.core.authc;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil {

  // Token过期时间30分钟（用户登录过期时间是此时间的两倍，以token在reids缓存时间为准）
  public static final long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;

  public static boolean verify(String token, String account, String secret) {
    try {
      // 根据密码生成JWT效验器
      Algorithm algorithm = Algorithm.HMAC256(secret);
      JWTVerifier verifier = JWT.require(algorithm).withClaim("account", account).build();
      // 效验TOKEN
      DecodedJWT jwt = verifier.verify(token);
      return true;
    } catch (Exception exception) {
      return false;
    }
  }

  public static String getAccount(String token) {
    try {
      DecodedJWT jwt = JWT.decode(token);
      return jwt.getClaim("account").asString();
    } catch (JWTDecodeException e) {
      return null;
    }
  }

  public static Long getCreateTime(String token) {
    try {
      DecodedJWT jwt = JWT.decode(token);
      Date expiresAt = jwt.getExpiresAt();
      return expiresAt.getTime() - EXPIRE_TIME;
    } catch (JWTDecodeException e) {
      return null;
    }
  }

  public static String sign(String account, String secret) {
    Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
    Algorithm algorithm = Algorithm.HMAC256(secret);
    // 附带account信息
    return JWT.create().withClaim("account", account).withExpiresAt(date).sign(algorithm);

  }

}