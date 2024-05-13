package com.gerenciador.tarefas.config;

import com.gerenciador.tarefas.filter.AuthenticatedFilter;
import com.gerenciador.tarefas.filter.LoginFilter;
import com.gerenciador.tarefas.permissions.RoleEnum;
import com.gerenciador.tarefas.service.AuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    AuthenticatedUserService authenticatedUserService;
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(crsf -> crsf.disable())
                .authorizeHttpRequests(auth -> {
                   auth.requestMatchers("/login").permitAll()
                           .requestMatchers(HttpMethod.GET, "/test-api").permitAll()
                           .requestMatchers(HttpMethod.GET, "/test-api-welcome").hasAuthority(RoleEnum.ADMIN.toString())
                           .requestMatchers(HttpMethod.GET, "/users").hasAuthority(RoleEnum.USER.toString())
                           .requestMatchers(HttpMethod.POST, "/users").hasAuthority(RoleEnum.ADMIN.toString())
                           .anyRequest().permitAll();
                });

        http.addFilterBefore(new LoginFilter("/login", authenticationConfiguration.getAuthenticationManager()), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new AuthenticatedFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticatedUserService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
