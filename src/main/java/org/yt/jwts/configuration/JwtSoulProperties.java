package org.yt.jwts.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置属性
 */
@ConfigurationProperties(prefix = "jwts")
public class JwtSoulProperties {

    /**
     * 0是localTokenStore 1是 redisTokenStore ，2是 jdbcTokenStore ，默认是0
     */
    private Integer storeType;

    /**
     * 拦截路径，默认是/**
     */
    private String[] path;

    /**
     * 排除拦截路径，默认无
     */
    private String[] excludePath;

    /**
     * 单个用户最大token数，默认-1不限制
     */
    private Integer maxToken;

    /**
     * 7天 单位:秒 使用
     */
    private Long expiration = 604800L;

    /**
     * 本地配置的jwt密钥
     * 必须32个字符以上
     */
    private String secretKey = "37b2d108f4b193edac2c9b8dbd95fdc9";

    /**
     * 本地配置的md5加密混淆key
     */
    private String md5Key = "localMd5Key";

    public Integer getStoreType() {
        return storeType;
    }

    public void setStoreType(Integer storeType) {
        this.storeType = storeType;
    }

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

    public String[] getExcludePath() {
        return excludePath;
    }

    public void setExcludePath(String[] excludePath) {
        this.excludePath = excludePath;
    }

    public Integer getMaxToken() {
        return maxToken;
    }

    public void setMaxToken(Integer maxToken) {
        this.maxToken = maxToken;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }
}
