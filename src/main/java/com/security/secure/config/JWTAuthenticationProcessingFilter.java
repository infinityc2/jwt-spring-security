package com.security.secure.config;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.secure.entity.Member;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class JWTAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    
    protected JWTAuthenticationProcessingFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl);
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        Member member = new ObjectMapper().readValue(request.getInputStream(), Member.class);
        return getAuthenticationManager().authenticate(
            new UsernamePasswordAuthenticationToken(
                member.getUsername(),
                member.getPassword(),
                Collections.emptyList()
            )
        );
    }

    
}