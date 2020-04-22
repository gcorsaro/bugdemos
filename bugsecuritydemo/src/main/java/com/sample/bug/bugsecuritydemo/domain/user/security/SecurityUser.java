package com.sample.bug.bugsecuritydemo.domain.user.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public final class SecurityUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String username;
    
    public SecurityUser(String username) {
    	this.username=username;
	}
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	if (username.equals("Admin"))
    		return Arrays.asList(new SimpleGrantedAuthority("ADMIN"));
    	else
    		return Arrays.asList(new SimpleGrantedAuthority("USER"));
    }

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}