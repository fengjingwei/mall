package com.hengxunda.springcloud.common.security.jwt;

import com.hengxunda.springcloud.common.utils.FastJsonUtils;
import com.hengxunda.springcloud.common.utils.SnowFlakeUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.RandomUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public abstract class JwtUtils {

    private static final String SECRET_KEY = "neoamyAYwuHCo2IFAgd1oRpSP0nzL1BF5t6ItqpKViM";

    public static String createJwt(String subject, long ttlMillis) {
        return createJwt(SnowFlakeUtils.getUUID(), subject, ttlMillis);
    }

    private static String createJwt(String id, String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder()
                // JWT的唯一标识
                .setId(id)
                // 时间戳,JWT的签发时间
                .setIssuedAt(now)
                // JWT的主体,即它的所有人
                .setSubject(subject)
                // JWT的签发主体
                .setIssuer("192837465")
                .signWith(signatureAlgorithm, signingKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            builder.setExpiration(new Date(expMillis));
        }

        return builder.compact();
    }

    public static <T> T parseJwt(String jwt, Class<T> clazz) {
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .parseClaimsJws(jwt)
                    .getBody();
            return FastJsonUtils.parseObject(claims.getSubject(), clazz);
        } catch (Exception e) {
            System.out.println("e = " + e.getMessage());
            return null;
        }
    }

    public static boolean verifyJwt(String jwt) {
        try {
            parseJwt(jwt, null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        AccountJwt accountJwt = AccountJwt.builder()
                .userId(RandomUtils.nextLong(1, 100))
                .account("18588257670")
                .loginTime("2019-08-08 09:35:00")
                .build();
        final String jwt = createJwt(FastJsonUtils.toJSONString(accountJwt), 36000000L);
        System.out.println("jwt = " + jwt);
        accountJwt = parseJwt(jwt, AccountJwt.class);
        accountJwt.setJwt(jwt);
        System.out.println("accountJwt = " + accountJwt);
        verifyJwt(jwt);
    }

    private Date generateExpirationDate(long expiration) {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
}
