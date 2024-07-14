package com.test.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.Entity.User;
import com.test.Repositry.UserRepositry;

@Service
public class UserService {
	@Autowired
	private UserRepositry userRepositry;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public Object saveUser(User user) {
	
		// TODO Auto-generated method stub
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepositry.save(user);
	}
	

}
