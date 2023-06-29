package com.example.ewallet.demo1;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class UserSecurityConfig {

    @Autowired
    UserService userService;


    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
        //auth.userDetailsService(userService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .httpBasic()
                .and()
                .csrf().disable() // to use POST mapping
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST,"/user/**").permitAll() // signup of new accn
                .requestMatchers("/user/**").hasAuthority(UserConstants.USER_AUTHORITY) //user driven actions
                .requestMatchers("/**").hasAnyAuthority(UserConstants.ADMIN_AUTHORITY,UserConstants.SERVICE_AUTHORITY) //admin driven actions
                .and()
                .formLogin();
        return http.build();
    }
//    protected void configure(HttpSecurity http) throws Exception{
//        http
//                .csrf().disable() // to use POST mapping
//                .authorizeHttpRequests()
//                .antMatchers(HttpMethod.POST,...antPatterns:"/user/**").permitAll() // sinup of new accn
//                .antMatchers(...antPatterns: "/user/**").hasAuthority("usr") //user driven actions
//                .antMatchers(...antPatterns:"/**").hasAuthority("adm") //admin driven actions
//                .and()
//                .formLogin();
//    }


}
