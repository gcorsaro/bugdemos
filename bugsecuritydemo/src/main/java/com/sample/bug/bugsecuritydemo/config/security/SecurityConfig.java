package com.sample.bug.bugsecuritydemo.config.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.j2ee.J2eePreAuthenticatedProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
class SecurityConfig {
	
	@Order(1)
	@Configuration
	public static class DefaultWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	    private final UserDetailsService userDetailsService;
	
	    @Autowired
	    DefaultWebSecurityConfigurerAdapter(UserDetailsService userDetailsService) {
	        this.userDetailsService = userDetailsService;
	    }
	
	    @Override
	    @Bean
	    public UserDetailsService userDetailsService() {
	        return super.userDetailsService();
	    }
	
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.logout()
		        .invalidateHttpSession(true)
		        .deleteCookies("JSESSIONID").and()
	                .authorizeRequests()
	                .antMatchers("/secure/**").authenticated()
	                .accessDecisionManager(accessDecisionManager())
	                .and().csrf().disable();
	    }
	
	    @Bean
	    AffirmativeBased accessDecisionManager() {
	        AffirmativeBased decisionManager = new AffirmativeBased(
	                Arrays.asList(new RoleVoter(), new WebExpressionVoter()));
	        decisionManager.setAllowIfAllAbstainDecisions(false);
	        return decisionManager;
	    }
	
	    @Override
	    @Bean
	    protected AuthenticationManager authenticationManager() {
	        return new ProviderManager(Collections.singletonList(preauthAuthProvider()));
	    }
	
	    @Bean
	    PreAuthenticatedAuthenticationProvider preauthAuthProvider() {
	        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
	        provider.setPreAuthenticatedUserDetailsService(userDetailsServiceWrapper());
	        return provider;
	    }
	
	    @Bean
	    UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> userDetailsServiceWrapper() {
	        UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper =
	                new UserDetailsByNameServiceWrapper<>();
	        wrapper.setUserDetailsService(userDetailsService);
	        return wrapper;
	    }
	}

	@Order(2)
	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	public static class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
		@Override
	    protected MethodSecurityExpressionHandler createExpressionHandler() {
	        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
	        return expressionHandler;
	    }
	}
}