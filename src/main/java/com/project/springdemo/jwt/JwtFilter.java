package com.project.springdemo.jwt;

import com.project.springdemo.domain.User;
import com.project.springdemo.service.UserService;
import com.project.springdemo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserServiceImpl userServiceImpl;

    public JwtFilter(JwtUtils jwtUtils, UserServiceImpl userServiceImpl) {
        this.jwtUtils = jwtUtils;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String jwt=httpServletRequest.getHeader("Authorization");
        if(jwt!=null){
            String userName=jwtUtils.getUserName(jwt);
            if(userName !=null && SecurityContextHolder.getContext().getAuthentication()==null){
                User user= (User) userServiceImpl.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                        new UsernamePasswordAuthenticationToken(user,"",user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
