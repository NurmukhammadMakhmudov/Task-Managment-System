//package org.example.taskmanagementsystem.security;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class JwtUtil {
//    private final String secretKey = "secret_key";
//
//    public String generateToken(UserDetails user) {
//        Map<String, Object> claims = new HashMap<String, Object>();
//        return createToken(claims, user.getUsername());
//    }
//
//    public String createToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder().setClaims(claims).setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
//                .signWith(SignatureAlgorithm.ES256, secretKey).compact();
//    }
//
//    public Boolean validateToken(String token, UserDetails user) {
//        final String username = extractUsername(token);
//        return (username.equals(user.getUsername()) && !isTokenExpired(token));
//    }
//
//    public String extractUsername(String token) {
//        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJwt(token).getBody().getSubject();
//    }
//
//    public boolean isTokenExpired(String token) {
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token).getBody().getExpiration().before(new Date());
//    }
//
//
//}
