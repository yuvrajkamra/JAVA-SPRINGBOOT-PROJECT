package com.example.ewallet.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.WeakHashMap;





@Configuration
public class TxnSecurityConfig{
    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
        //auth.userDetailsService(txnService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable() // to use POST mapping
                .httpBasic()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/txn/**").hasAuthority("usr") //user driven actions
                .and()
                .formLogin();
        return http.build();
    }

    @Autowired
    TxnService txnService;

    @Bean
    PasswordEncoder getPE(){
        return new BCryptPasswordEncoder();
    }
}
