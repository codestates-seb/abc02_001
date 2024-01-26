package com.example.codestates.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.io.Encoders;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


@Component
public class JwtTokenizer {
    @Getter
    @Value("${jwt.key}")
    private String secretKey;

    @Getter
    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    @Getter
    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;

    //(1) plain text 형태인 Secretkey의 byte[]를 Base64형식의 문자열로 인코딩
    //Plain Text 자체를 Secret Key로 사용하는 것을 권장하지 않는다.
    public String encodeBase64SecretKey(String secretKey){
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    //(2) 인증된 사용자에게 JWT를 최초로 발급해 주기 위한 JWT 생성 메서드
    public String generateAccessToken(Map<String,Object> claims,
                                      String subject,
                                      Date expiration,
                                      String base64EncodedSecretKey){
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
        //2-1 base64형식 secret key 문자열을 이욯애 key 객체를 얻는다
        return Jwts.builder()
                .setClaims(claims) //2-2 JWT에 포함시킬 Custom Claims를 추가, Custom Claims에는 주로 인증된 사요자의 관련된 정보를 추가
                .setSubject(subject) //2-3 JWT에 대한 제목을 추가
                .setIssuedAt(Calendar.getInstance().getTime()) //2-4 JWT 발행 일자를 설정, Data 타입
                .setExpiration(expiration)//2-5 JWT 만료일시를 지정. Data 타입
                .signWith(key)//2-6 서명을 위한 key 객체를 설정
                .compact();//2-7 JWT를 생성하고 직렬화

    }
    //(3)
    public String generateRefreshToken(String subject, Date expiration, String base64EncodedSecretKey){
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
        //ACCESS token을 새로 발급해주는 역할이기 때문에 별도의 Custom Claims는 추가할 필요 없음
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();

    }

    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey){
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
        return claims;
    }

    //JWT 검증을 위한 메서드
    public void verifySignature(String jws, String base64EncodedSecretKey){
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jwts.parserBuilder()
                .setSigningKey(key) //서명에 사용된 secret 키를 설정한다
                .build()
                .parseClaimsJws(jws);//JWT를 파싱해서 Claims를 얻는다
    }

    //JWT 만료일시를 지정하기 위한 메서드 JWT 생성시 사용
    public Date getTokenExpiration(int expirationMinutes){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        Date expiration = calendar.getTime();

        return expiration;
    }


    //(4) JWT의 서명에 사용할 Secret Key를 생성
    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        /*
        (4-1)의 Decoders.BASE64.decode() 메서드는 Base64 형식으로 인코딩 된 Secret Key를 디코딩한 후, byte array를 반환합니다.
        (4-2)의 Keys.hmacShaKeyFor() 메서드는 key byte array를 기반으로 적절한 HMAC 알고리즘을 적용한 Key(java.security.Key) 객체를 생성합니다.
         jjwt 0.9.x 버전에서는 서명 과정에서 HMAC 알고리즘을 직접 지정해야 했지만 최신 버전에서는 내부적으로 적절한 HMAC 알고리즘을 지정해 준다는 사실을 기억하기 바랍니다.
         */
        return key;
    }
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getKeyFromBase64EncodedKey(encodeBase64SecretKey(secretKey)))
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    public String getRefreshToken(String refreshToken){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getKeyFromBase64EncodedKey(encodeBase64SecretKey(secretKey)))
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();
            return claims.getSubject();
        }catch (JwtException | IllegalArgumentException e){
            return null;
        }
    }

}
