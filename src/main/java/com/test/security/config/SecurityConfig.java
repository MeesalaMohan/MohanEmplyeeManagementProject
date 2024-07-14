package com.test.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.test.Filter.JwtFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userdetialservice() {
		//UserDetails admin = User.withUsername("Mohan").password(Encoder.encode("Mohan@123")).roles("ADMIN").build();

		//UserDetails user = User.withUsername("sai").password(Encoder.encode("sai@7385")).roles("USER").build();
		//return new InMemoryUserDetailsManager(admin, user);
		return new UserDetialinfoService(); 
	}
	@Bean
	public SecurityFilterChain  securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable().authorizeHttpRequests()
				.requestMatchers("/emp/save","/emp/authintcate","/user/authentication").permitAll().and().
				authorizeHttpRequests().requestMatchers("/emp/**")
				.authenticated()
			.and()
			//.formLogin().and().build();
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			   .and()
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
	}
	@Bean
	public AuthenticationProvider authenticationProvider() { 
		DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userdetialservice());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
		
	}
	 @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
	    	return configuration.getAuthenticationManager();
	    }
	    
}
