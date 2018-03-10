package uk.doneby.igt.auth.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CompositeFilter;
import org.springframework.web.filter.CorsFilter;

import uk.doneby.igt.auth.JWTAuthenticationFilter;
import uk.doneby.igt.auth.JWTAuthorizationFilter;
import uk.doneby.igt.auth.TokenUtils;
import uk.doneby.igt.controller.UtilController;
import uk.doneby.igt.model.User;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Controller
@EnableSocial
@EnableOAuth2Client
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	final static Logger LOG = LoggerFactory.getLogger(WebSecurityConfig.class);


	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	
	@Autowired
	private UserDetailsService userDetailsService;
    
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
    private TokenUtils tokenUtils;
	
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
    		.authorizeRequests()
    		.antMatchers("/docs/**").permitAll()
    		.antMatchers("/api/**").permitAll()
    		.antMatchers("/logout*").permitAll()
    		.antMatchers("/login**").permitAll()
        .antMatchers(HttpMethod.POST, tokenUtils.getSignUpUrl()).permitAll()
        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilter(corsFilter())
        .addFilter(new JWTAuthenticationFilter(authenticationManager(), tokenUtils));
		httpSecurity.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
		httpSecurity.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//		

        httpSecurity.headers().cacheControl();
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
    
    @Bean
    public JWTAuthorizationFilter jwtAuthorizationFilter() throws Exception {
        return new JWTAuthorizationFilter();
    }
    
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    
    @GetMapping("/logout")
	@PreAuthorize("hasAuthority('ROLE_USER')")
    public boolean logout(HttpServletRequest req) {
		return tokenUtils.invalidateToken(req);
    }
    
    @Bean
	@ConfigurationProperties("facebook")
	public ClientResources facebook() {
		return new ClientResources();
	}
    
    @Bean
   	@ConfigurationProperties("google")
   	public ClientResources google() {
   		return new ClientResources();
   	}
    
    private Filter ssoFilter() {
		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();
		filters.add(ssoFilter(facebook(), "/login/facebook"));
		filters.add(ssoFilter(google(), "/login/google"));
		filter.setFilters(filters);
		return filter;
	}
    
    private Filter ssoFilter(ClientResources client, String path) {
		OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
		OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
		filter.setRestTemplate(template);
//		template.s
		
		System.out.println("path: " + path);
		System.out.println("getUserInfoUri: " + client.getResource().getUserInfoUri());
		System.out.println("getClientId: " +  client.getClient().getClientId());
//		System.out.println(": " + );
		
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId());
		tokenServices.setRestTemplate(template);
		filter.setTokenServices(tokenServices);
		return filter;
	}
    
    class ClientResources {

    	@NestedConfigurationProperty
    	private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

    	@NestedConfigurationProperty
    	private ResourceServerProperties resource = new ResourceServerProperties();

    	public AuthorizationCodeResourceDetails getClient() {
    		return client;
    	}

    	public ResourceServerProperties getResource() {
    		return resource;
    	}
    }
//    
}

//class ClientResources {
//
//	@NestedConfigurationProperty
//	private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();
//
//	@NestedConfigurationProperty
//	private ResourceServerProperties resource = new ResourceServerProperties();
//
//	public AuthorizationCodeResourceDetails getClient() {
//		return client;
//	}
//
//	public ResourceServerProperties getResource() {
//		return resource;
//	}
//}
