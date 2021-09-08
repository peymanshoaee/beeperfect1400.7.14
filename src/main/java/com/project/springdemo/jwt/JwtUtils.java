package com.project.springdemo.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtils {
    private final String SECRET="peyman_secret";


    public Map<String,String> generateToken(String username){
        Long expireTime=System.currentTimeMillis()+(60*60*24*1000);

        String token= Jwts.builder().setSubject(username)
                .setExpiration(new Date(expireTime))
                .signWith(SignatureAlgorithm.HS256,SECRET)
                .compact();
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("token",token);
        stringStringMap.put("expireTime",String.valueOf(expireTime));
        return stringStringMap;
    }

    public String getUserName(String token){
       String subject= Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
       return  subject;
    }
}
