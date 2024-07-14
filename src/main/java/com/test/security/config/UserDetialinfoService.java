package com.test.security.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.test.Entity.User;
import com.test.Repositry.UserRepositry;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class UserDetialinfoService implements UserDetailsService {
	@Autowired
	private UserRepositry userRepositry;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		log.info("user name is{}",username);
		Optional<User> dd= userRepositry.findByname(username);
		log.info("the user is {}",dd);
		return dd.map(UserdetialInfo::new)
				.orElseThrow(()-> new UsernameNotFoundException("does not have user withs this name"+username));
	}

}
