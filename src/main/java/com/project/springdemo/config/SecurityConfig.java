package com.project.springdemo.config;

import com.project.springdemo.jwt.JwtFilter;
import com.project.springdemo.service.UserService;
import com.project.springdemo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;
    private final UserServiceImpl userServiceImpl;
    private final JwtFilter jwtFilter;

    public SecurityConfig(DataSource dataSource, UserServiceImpl userServiceImpl, JwtFilter jwtFilter) {
        this.dataSource = dataSource;

        this.userServiceImpl = userServiceImpl;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/login","/jwt/login").permitAll()
                /*.antMatchers("/users/**").hasAnyAuthority("USER","ADMIN")
                .antMatchers("/admin/**").hasAnyAuthority("ADMIN")*/
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                //.and().formLogin().loginPage("/login")
                ;//.usernameParameter("username");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       /* auth.inMemoryAuthentication()
                .withUser("peyman")
                .password("123")
                .roles("USER");*/

       /* auth.jdbcAuthentication()
                .dataSource(this.dataSource)
                .usersByUsernameQuery("select username,password,enabled from user where username=? ")
                .authoritiesByUsernameQuery("select username, user_roles from authorities where username=?");*/

        auth.userDetailsService(userServiceImpl);


    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
