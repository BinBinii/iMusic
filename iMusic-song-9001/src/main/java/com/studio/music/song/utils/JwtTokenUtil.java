package com.studio.music.song.utils;

import com.studio.music.song.model.pojo.TbUser;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Configuration
@Component
@Slf4j
public class JwtTokenUtil {

    /**
     * token的头key
     */
    public static final String TOKEN_HEADER = "Authorization";
    /**
     * token前缀
     */
    public static final String TOKEN_PREFIX = "iMusic ";

    /**
     * token 过期时间 30分钟
     */
    public static final long EXPIRATION = 1000 * 60 * 30;

    /**
     * 加密的key
     */
    public static final Key APP_SECRET_KEY = null;

    /**
     * 权限的声明key
     */
    private static final String ROLE_CLAIMS = "role";

    private static SecretKey secretKey;

    public JwtTokenUtil() throws Exception {
        secretKey = RsaUtils.getSecretKey("/Users/binbini/Documents/workspace/iMusic/auth_key/id_key");
    }

    /**
     * 生成token
     *
     * @param email 邮箱
     * @param role 用户角色
     * @return token
     */
    public static String createToken(String email, String role, TbUser tbUser) {
        String token = Jwts
                .builder()
                .setSubject(email)
                .claim("id", tbUser.getId())
                .claim("image", tbUser.getImage())
                .claim("nickname", tbUser.getNickname())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(JwtTokenUtil.secretKey)
                .compact();
        return TOKEN_PREFIX + token;
    }


    /**
     * 获取当前登录用户
     *
     * @param token
     * @return
     */
    public TbUser getUser(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(JwtTokenUtil.secretKey).build().parseClaimsJws(token).getBody();
        Integer id = Integer.parseInt(claims.get("id").toString());
        String email = claims.getSubject();
        String nickname = claims.get("nickname").toString();
        String image = claims.get("image").toString();
        TbUser tbUser = new TbUser();
        tbUser.setId(id)
                .setEmail(email)
                .setNickname(nickname)
                .setImage(image);
        return tbUser;
    }

    /**
     * 获取当前登录用户角色
     *
     * @param token
     * @return
     */
    public static String getUserRole(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(JwtTokenUtil.secretKey).build().parseClaimsJws(token).getBody();
        return claims.get("role").toString();
    }


    /**
     * 检查token是否过期
     *
     * @param  token token
     * @return boolean
     */
    public static boolean isExpiration(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(JwtTokenUtil.secretKey).build().parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(JwtTokenUtil.secretKey).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(JwtTokenUtil.secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

}