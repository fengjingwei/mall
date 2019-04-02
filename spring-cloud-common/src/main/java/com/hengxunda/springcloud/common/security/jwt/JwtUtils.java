package com.hengxunda.springcloud.common.security.jwt;

import com.hengxunda.springcloud.common.utils.FastJsonUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

public abstract class JwtUtils {

    /**
     * 密钥
     */
    private static final String LEXICAL_XSD_BASE_64_BINARY = "neoamyAYwuHCo2IFAgd1oRpSP0nzL1BF5t6ItqpKViM";

    public static String createSimpleJWT(String subject, long ttlMillis) {
        return createJWT(UUID.randomUUID().toString(), "192837465", subject, ttlMillis);
    }

    /**
     * Sample method to construct a JWT
     *
     * @param id
     * @param issuer
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String issuer, String subject, long ttlMillis) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(LEXICAL_XSD_BASE_64_BINARY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setId(id)// JWT的唯一标识
                .setIssuedAt(now)// 时间戳,JWT的签发时间
                .setSubject(subject)// JWT的主体,即它的所有人
                .setIssuer(issuer)// JWT的签发主体
                .signWith(signatureAlgorithm, signingKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            builder.setExpiration(new Date(expMillis));
        }

        return builder.compact();
    }

    /**
     * Sample method to validate and read the JWT
     *
     * @param jwt
     * @return
     */
    public static AccountJWT parseJWT(String jwt) {
        // This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(LEXICAL_XSD_BASE_64_BINARY))
                .parseClaimsJws(jwt)
                .getBody();

        return FastJsonUtils.parseObject(claims.getSubject(), AccountJWT.class);
    }

    private Date generateExpirationDate(long expiration) {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    public static void main(String[] args) {
        AccountJWT accountJWT = AccountJWT.builder().phone("18588257670").email("951159049@qq.com").build();

        String jwt = createSimpleJWT(FastJsonUtils.toJSONString(accountJWT), 3600000L);

        System.out.println("jwt = " + jwt.length());

        accountJWT = parseJWT(jwt);

        System.out.println("accountJWT = " + accountJWT.getPhone());
    }
}
