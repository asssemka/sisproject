package kz.narxoz.springbootdemo1.config;

import kz.narxoz.springbootdemo1.service.UserService;
import kz.narxoz.springbootdemo1.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true, securedEnabled = true)
public class SecurityConfig {

    @Bean
    public UserService userService(){
        return new UserServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
      http.exceptionHandling().accessDeniedPage("/403");
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService()).passwordEncoder(passwordEncoder());

        http.authorizeRequests().antMatchers("/css/**", "/js/**").permitAll();

        http.formLogin()
          .loginProcessingUrl("/auth").permitAll() // <form action = "/auth" method = "post">
          .defaultSuccessUrl("/profile") // response.sendRedirect("/profile");
          .loginPage("/signin").permitAll() // authentication page
          .failureUrl("/signin?error") // if authentication failed
          .usernameParameter("user_email") // <input type = "email" name = "user_email">
          .passwordParameter("user_password"); // <input type = "password" name = "user_password">

        http.logout()
          .logoutSuccessUrl("/signin")
          .logoutUrl("/signout");

        http.csrf().disable();

        return http.build();
    }

}
