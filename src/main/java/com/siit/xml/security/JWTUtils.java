package com.siit.xml.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JWTUtils {

    @Value("myXAuthSecret")
    private String secret;

    @Value("18000") //in seconds (5 hours)
    private Long expiration;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = this.getClaimsFromToken(token);
            if(claims != null)
                username = claims.getSubject();
            else
                username = null;
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(this.secret)
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expirationDate;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            if(claims != null)
                expirationDate = claims.getExpiration();
            else
                expirationDate = null;
        } catch (Exception e) {
            expirationDate = null;
        }
        return expirationDate;
    }

    private boolean isTokenExpired(String token) {
        final Date expirationDate = this.getExpirationDateFromToken(token);
        return expirationDate.before(new Date(System.currentTimeMillis()));
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    public String generateToken(UserDetails userDetails, Long id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
		Iterator<? extends GrantedAuthority> iter = userDetails.getAuthorities().iterator();
		String auth_string = "";
		while(iter.hasNext()) {
			auth_string += ((SimpleGrantedAuthority)iter.next()).getAuthority() + ",";
		}
		auth_string = auth_string.substring(0,auth_string.length()-1);
		claims.put("roles", auth_string);
		claims.put("created", new Date(System.currentTimeMillis()));
        claims.put("id", id);
        return Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
}