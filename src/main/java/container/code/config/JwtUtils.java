package container.code.config;

import container.code.data.dto.UserPrinciple;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.security.Keys.secretKeyFor;

@Component
public class JwtUtils {

    private final String jwtSecret = "wyk52dSUWe6vaMYOsdOrBlWwE0kyQjj6zzPMy4xWVzbRLzPqT31AQaYqpZy3q4w8RR6of0LKPHrr+wJc7NxelA==";
    public String createToken(UserPrinciple userPrinciple) {

        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 6000000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getJwt(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer")){
            return authHeader.replace("Bearer", "");
        }
        return null;
    }
    public boolean validateToken(String token) throws SignatureException, MalformedJwtException, UnsupportedJwtException, ExpiredJwtException, IllegalArgumentException{
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException | ExpiredJwtException | MalformedJwtException | UnsupportedJwtException |
                IllegalArgumentException e){
        }
        return false;
    }

    public String getUsernameFromToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}
