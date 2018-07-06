package com.task.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by Dimon on 01.07.2018.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    public static final String KEY_ROLES = "auth.roles";
    public static final String KEY_USER_LOGIN = "auth.users.login";
    public static final String KEY_ADMIN_LOGIN = "auth.admins.login";
    public static final String KEY_USER_PSW = "auth.users.psws";
    public static final String KEY_ADMIN_PSW = "auth.admins.psws";

    private Environment environment;

    private String[] roles;

    @Autowired
    public SecurityConfiguration(Environment environment) {
        this.environment = environment;
        roles = this.environment.getProperty(KEY_ROLES, String[].class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        String encodedUser = passwordEncoder().encode(environment.getProperty(KEY_USER_PSW, String.class));
        String encodedAdmin = passwordEncoder().encode(environment.getProperty(KEY_ADMIN_PSW, String.class));
        auth
                .inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser(environment.getProperty(KEY_USER_LOGIN, String.class))
                .password(encodedUser)
                .roles(roles[0])
                .and()
                .withUser(environment.getProperty(KEY_ADMIN_LOGIN, String.class))
                .password(encodedAdmin)
                .roles(roles[0], roles[1]);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/authors", "/books", "/author/info/**").hasAnyRole(roles[0], roles[1])
                .antMatchers("/author/**", "/book/**").hasRole(roles[1])
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .permitAll()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
