package com.example.config;

import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@Configuration
    @EnableWebSecurity
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    // private DataSource dataSource; //для манипуляций с базой данных
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/about", "/css/**", "/reg").permitAll()//адресса которые будут доступны для неавторизованных пользователей
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")//страница логина доступна всем
                .permitAll()
                .and()
                .logout()//страница выхода доступна всем
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.jdbcAuthentication()//технология jdbc
        auth.userDetailsService(userService)
                //.dataSource(dataSource)//чтобы jdbc мог контролировать все в базе данных
                .passwordEncoder(NoOpPasswordEncoder.getInstance());//для возможности кодировки паролей

    }
}
