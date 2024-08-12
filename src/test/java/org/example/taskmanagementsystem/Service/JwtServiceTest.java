package org.example.taskmanagementsystem.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.example.taskmanagementsystem.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    private final JwtService jwtService = new JwtService();

    private final Key key = Keys.hmacShaKeyFor(jwtService.getSecretKey().getEncoded());

    @Test
    void testGenerateToken() {
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        Mockito.when(userDetails.getUsername()).thenReturn("testUser");

        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testExtractToken() {
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        Mockito.when(userDetails.getUsername()).thenReturn("testUser");

        String token = jwtService.generateToken(userDetails);
        String extractedUsername = jwtService.extractToken(token);

        assertEquals("testUser", extractedUsername);
    }

    @Test
    void testIsValidateToken() {
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        Mockito.when(userDetails.getUsername()).thenReturn("testUser");

        String token = jwtService.generateToken(userDetails);

        assertTrue(jwtService.isValidateToken(token, userDetails));
    }


    //TODO need to finish this Test
//    @Test
//    void testIsTokenExpired() {
//        UserDetails userDetails = Mockito.mock(UserDetails.class);
//        Mockito.when(userDetails.getUsername()).thenReturn("testUser");
//
//        String token = jwtService.generateToken(userDetails);
//        jwtService.s
//        // Wait for the token to expire
//        try {
//            Thread.sleep(1000 * 60 * 60 * 11); // 11 hours
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        assertFalse(jwtService.isValidateToken(token, userDetails));
//    }

    @Test
    void testExtractAllClaims() {
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        Mockito.when(userDetails.getUsername()).thenReturn("testUser");

        String token = jwtService.generateToken(userDetails);
        Claims claims = jwtService.extractAllClaims(token);

        assertNotNull(claims);
        assertEquals("testUser", claims.getSubject());
    }

    @Test
    void testGetSecretKey() {
        Key secretKey = jwtService.getSecretKey();

        assertNotNull(secretKey);
        assertEquals(key.getAlgorithm(), secretKey.getAlgorithm());
    }
}
