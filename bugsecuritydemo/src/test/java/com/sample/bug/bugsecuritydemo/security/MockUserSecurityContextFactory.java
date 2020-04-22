package com.sample.bug.bugsecuritydemo.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.sample.bug.bugsecuritydemo.config.security.DemoUserDetailsService;

public class MockUserSecurityContextFactory implements WithSecurityContextFactory<WithUser> {

	@Autowired
    private DemoUserDetailsService userSvc;
	
	@Override
	public SecurityContext createSecurityContext(WithUser user) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

    	UserDetails userDet = userSvc.loadUserByUsername(user.username());
    	Authentication auth = new UsernamePasswordAuthenticationToken(userDet, "password", userDet.getAuthorities());
    	context.setAuthentication(auth);
		return context;
	}

}
