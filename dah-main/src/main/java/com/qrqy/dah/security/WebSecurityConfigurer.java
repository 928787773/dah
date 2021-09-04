package com.qrqy.dah.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author : Luis
 * @date : 2021/4/1 16:19
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("+++++ WebSecurityConfigurer configure(HttpSecurity http) +++++");
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/*.html", "/*.css", "/*.js", "/*.json", "/*.txt", "/favicon.ico").permitAll()
                .antMatchers("/**/*.html", "/**/*.css", "/**/*.js", "/**/*.json", "/**/*.txt").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/dp/v*/common*").permitAll()
                .antMatchers("/dp/v*/api*").permitAll()
//                .antMatchers("/v1/education*").hasRole("PHYSICIAN")
//                .antMatchers("/v1/system*").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout().permitAll()
                .and()
                .formLogin().disable();
        http.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 解决跨域的问题，与security的过滤器有冲突，要放在其前面
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");
        config.setAllowCredentials(true);
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);
    }

}
