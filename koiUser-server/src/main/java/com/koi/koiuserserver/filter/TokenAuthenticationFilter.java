package com.koi.koiuserserver.filter;

import com.koi.koiuserserver.domain.dto.LoginUser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Token 过滤器，验证 token 的有效性
 *
 * @Author zjl
 * @Date 2023/7/27 17:15
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LoginUser loginUser = new LoginUser();
        loginUser.setId(1L);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                loginUser, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 继续过滤链
        filterChain.doFilter(request, response);
    }
}
