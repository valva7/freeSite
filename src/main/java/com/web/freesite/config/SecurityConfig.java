package com.web.freesite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.web.SecurityFilterChain;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String[] whiteList = new String[] {
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**",
            "/login/**",
    };

    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests()
                .requestMatchers(whiteList).authenticated()
                //.requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                //.requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and().formLogin().loginPage("/login");

        return http.build();
    }*/


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        RequestMatcher[] requestMatchers = new RequestMatcher[whiteList.length];
        for (int i = 0; i < whiteList.length; i++) {
            requestMatchers[i] = new AntPathRequestMatcher(whiteList[i]);
        }

        return httpSecurity.cors().disable()
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(new OrRequestMatcher(requestMatchers)).permitAll()
                                .anyRequest().authenticated()
                )
                .cors().disable()   // cors 사용 중지
                .csrf().disable()   // swagger API 호출시 403 에러 발생 방지
                .build();
    }
}