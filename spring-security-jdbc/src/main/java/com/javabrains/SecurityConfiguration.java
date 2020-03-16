package com.javabrains;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	//Configure datasource 
	//application.properties 
	//spring.datasource.username,spring.datasource.password,spring.datasource.url
	@Autowired
	DataSource dataSource;
	
	
	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		 auth.jdbcAuthentication()
             .dataSource(dataSource)
             .withDefaultSchema()
             .withUser(
            		 User.withUsername("user")
            		 .password("pass")
            		 .roles("USER")
             )
             .withUser(
            		 User.withUsername("admin")
            		 .password("pass")
            		 .roles("ADMIN")
             );
	}*/
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		 auth.jdbcAuthentication()
             .dataSource(dataSource)
             .usersByUsernameQuery("select username,password,enabled"
             		+ "from users"
            		+ "where username = ?")
             .authoritiesByUsernameQuery("select username,password,enabled"
             		+ "from users"
            		+ "where username = ?");
		 //username come when you will configure datasource in application.properties file
             
	}
	
	@Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http.authorizeRequests()
		.antMatchers("/admin").hasRole("ADMIN")
        .antMatchers("/user").hasAnyRole("ADMIN", "USER")
        .antMatchers("/").permitAll()
        .and().formLogin();
		
	}
}
