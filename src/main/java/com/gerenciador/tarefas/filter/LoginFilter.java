package com.gerenciador.tarefas.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciador.tarefas.entity.User;
import com.gerenciador.tarefas.entity.AuthenticatedUser;
import com.gerenciador.tarefas.service.AuthenticateService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    public LoginFilter(String url, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        AuthenticatedUser authenticatedUser = new ObjectMapper().readValue(collect, AuthenticatedUser.class);
        return getAuthenticationManager().
                authenticate(new UsernamePasswordAuthenticationToken(
                        authenticatedUser.getUsername(),
                        authenticatedUser.getPassword(),
                        Collections.emptyList()
                ));
    }

    protected void sucessfullAuthentication(HttpServletRequest httpServletRequest,
                                            HttpServletResponse httpServletResponse,
                                            Authentication authentication,
                                            FilterChain filterChain) throws IOException, ServletException {
        AuthenticateService.addJWTToken(httpServletResponse, authentication);
    }
}
