package com.projectbyanuj.Secure_Journal_Application.auth_services.security;

import com.projectbyanuj.Secure_Journal_Application.auth_services.service.AppUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtAuthUtil jwtAuthUtil;
    private final AppUserDetailsService appUserDetailsService;
    private final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");

        if (requestHeader == null || !requestHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = requestHeader.substring(7);
        String email = "";

        try {
            email = jwtAuthUtil.getEmailFromToken(token);
        }catch (IllegalArgumentException ex) {
            logger.info("Illegal Argument while fetching the email.");
        } catch (ExpiredJwtException ex) {
            logger.warn("Given Jwt token is expired.");
        } catch (MalformedJwtException ex) {
            logger.info("Some changed has done in token ! Invalid Token.");
        } catch (Exception e) {
            logger.warn("Invalid Header Value.");
        }

        if (!email.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
            //load user by email
            UserDetails userDetails = this.appUserDetailsService.loadUserByUsername(email);

            if(this.jwtAuthUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                logger.warn("Validation Failed.");
            }

        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.startsWith("/api/auth/signin")
                || path.startsWith("/api/auth/signup");
    }
}
