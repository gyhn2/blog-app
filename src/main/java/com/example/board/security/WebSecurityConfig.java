package com.example.board.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.board.service.UsersService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    String someUsername = "abcd"; //change
    String somePassword = "qwer"; //change

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .mvcMatchers("/", "/h2-console/**", "/css/**",
                                "/js/**", "/img/**").permitAll()
                                .mvcMatchers("/sign-up", "/sign-in").hasRole("ANONYMOUS")
                                .mvcMatchers("/new", "/sign-out").hasAuthority("USER")
                        .mvcMatchers("/admin/**", "/users").hasRole("ADMIN")
//                        .anyRequest().authenticated()
                )
            .formLogin()
                .loginPage("/sign-in")
                .loginProcessingUrl("/sign-in")
//                .defaultSuccessUrl("/")
                .successHandler(successHandler())
//                .failureUrl("/sign-in")
                .failureHandler(failureHandler())
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/sign-out"))
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/")
                .and()
            .csrf().disable();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new AuthFailureHandler();
    }
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new AuthSuccessHandler();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder())
                //in-memory authentication example starting here
                .and()
                .inMemoryAuthentication();
//                .withUser(someUsername)
//                .password(passwordEncoder().encode(somePassword))
//                .roles("USER", "ADMIN")
//                .and()
//                .withUser(someUsername)
//                .password(passwordEncoder().encode(somePassword))
//                .roles("USER");

	}




}
