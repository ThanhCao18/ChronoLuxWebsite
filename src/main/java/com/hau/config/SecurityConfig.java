package com.hau.config;

import com.hau.security.CustomSuccessHandler;
import com.hau.service.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public CustomSuccessHandler customSuccessHandler() {
        return new CustomSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                .authorizeRequests()
                .antMatchers("/check", "/rasa/**").permitAll()
                .antMatchers("/admin/*").hasAnyRole("ADMIN")
                .antMatchers("/home").hasAnyRole("USER","ANONYMOUS","USER_FACEBOOK","USER_GOOGLE")
                .antMatchers("/cart*").hasAnyRole("USER","ANONYMOUS","USER_FACEBOOK","USER_GOOGLE")
                .antMatchers("/shop*").hasAnyRole("USER","ANONYMOUS","USER_FACEBOOK","USER_GOOGLE")
                .antMatchers("/checkout*").hasAnyRole("USER","ANONYMOUS","USER_FACEBOOK","USER_GOOGLE")
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .loginProcessingUrl("/j_spring_security_check")
                .successHandler(customSuccessHandler())
                .failureUrl("/login?incorrectAccount")
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDenied")
                .and()
                .rememberMe()
                              .key("AbcdefgHiJKlmnOpqrsut0123456789")
                              .tokenValiditySeconds(365*24*60*60)
                .and()

                .csrf().disable()
                ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resource", "/ws/**", "/check"); // nếu có tài nguyên tĩnh
    }

}
