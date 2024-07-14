package com.test.security.config;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.test.Entity.User;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class UserdetialInfo implements UserDetails {
	public String name;
	public String Password;
	public	 List<SimpleGrantedAuthority> authorites;
	UserdetialInfo(User user){
		
		name=user.getName();
		Password= user.getPassword();
		//authorites=user.getRole().stream().map(r -> new SimpleGrantedAuthority("ROLE_" + user.getRole())).toList();
		
		authorites=Arrays.stream(user.getRole().split(",")).map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());
		
		log.info(" it si a UserdetialInfo {}",user);
	}	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorites.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.getAuthority())).toList();
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return Password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
