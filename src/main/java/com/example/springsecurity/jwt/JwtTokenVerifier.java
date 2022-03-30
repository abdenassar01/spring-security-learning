package com.example.springsecurity.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, 
                                    HttpServletResponse httpServletResponse, 
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHedear = httpServletRequest.getHeader("Authorization");
        
        
        if(Strings.isNullOrEmpty(authorizationHedear) || !authorizationHedear.startsWith("Bearer ")){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        
        String token = authorizationHedear.replace("Bearer ", "");
        try {
            String sucret = "supersucretkeythatnoonecangesssupersucretkeythatnoonecangesssupersucretkeythatnoonecangesssupersucretkeythatnoonecangess";
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(sucret.getBytes()))
                    .parseClaimsJws(token);

            Claims body = claimsJws.getBody();

            String username = body.getSubject();

            List<Map<String, String >> authorities = (List<Map<String, String>>) body.get("authorities");

            Set<SimpleGrantedAuthority> simpleAuthorities = authorities
                    .stream()
                    .map(auth -> new SimpleGrantedAuthority(auth.get("authority")))
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            
        }catch (JwtException ex){
            throw new IllegalStateException("token cannot be trusted:  " + token);
        }
        
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
