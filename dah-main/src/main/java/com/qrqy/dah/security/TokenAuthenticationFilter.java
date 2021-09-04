package com.qrqy.dah.security;

import com.qrqy.dah.dto.CommonLoginDTO;
import com.qrqy.mysql.entity.AdminEntity;
import com.qrqy.mysql.manager.AdminManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Value("${token.header:Authorization}")
    private String tokenHeader;

    @Resource
    private RedisTemplate<String, UserDetails> redisTemplate;

    @Autowired
    private AdminManager manager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //log.info("request path:{},query str:{},form:{}", request.getRequestURI(), request.getQueryString(), JSON.toJSONString(request.getParameterMap()));

        //读取token
        String token = request.getHeader(tokenHeader);
        if (StringUtils.isEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        UserDetails userToken = null;
        try {
            userToken = redisTemplate.opsForValue().get(token);
            log.info("userToken {}:",userToken);

            //查询账号状态
            CommonLoginDTO user = (CommonLoginDTO)userToken;
            AdminEntity admin = manager.get(user.getId());
            log.info("管理员 admin{}",admin);
            if (admin == null) {
                filterChain.doFilter(request, response);
                return;
            }
            if (!admin.getStatus().equals("1")) {
                filterChain.doFilter(request, response);
                return;
            }

        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }
        if (userToken == null) {
            filterChain.doFilter(request, response);
            return;
        }
        //展期
        redisTemplate.expire(token, 2, TimeUnit.DAYS);

        //security认证设置
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userToken, null, userToken.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}

