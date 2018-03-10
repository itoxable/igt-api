package uk.doneby.igt.auth;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import uk.doneby.igt.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtils implements ServletContextAware{
	
	@Autowired
    private ServletContext servletContext;
	
	@Value("${auth.secret}")
	private String secret;
	
	@Value("${auth.token_prefix}")
	private String tokenPrefix;
	
	@Value("${auth.header_string}")
	private String headerString;	
	
	@Value("${auth.expiration_time}")
	private int expirationTime;
	
	@Value("${auth.sign_up_url}")
	private String signUpUrl;
	
	@SuppressWarnings("unchecked")
	public boolean isTokenValid(String token) {
	    	Object obj = servletContext.getAttribute("tokenStore");
	    	if (obj != null) {
	    		Set<String> 	tokenStore = (Set<String>)obj;
	    		return tokenStore.contains(token);
	    	}
	    	return false;
    }
	
	public boolean invalidateToken(HttpServletRequest req) {
		String header = req.getHeader(headerString);
		String token = header.replace(tokenPrefix, "");
		return invalidateToken(token);
	} 
	
	@SuppressWarnings("unchecked")
	public boolean invalidateToken(String token) {
	    	Object obj = servletContext.getAttribute("tokenStore");
	    	if (obj != null) {
	    		Set<String> 	tokenStore = (Set<String>)obj;
	    		boolean removed = tokenStore.remove(token);
	    		servletContext.setAttribute("tokenStore", tokenStore);
	    		return removed;
	    	}
	    	return false;
	}
	
	@SuppressWarnings("unchecked")
	public String createToken(User user) {
		String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
		Object obj = servletContext.getAttribute("tokenStore");
		Set<String> 	tokenStore;
	    	if (obj != null) {
	    		tokenStore = (Set<String>)obj;
	    	} else {
	    		tokenStore = new HashSet<String>();
	    	}
	    	tokenStore.add(token);
	    	servletContext.setAttribute("tokenStore", tokenStore);
	    	System.out.println(tokenStore);
		return token;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public String getSecret() {
		return secret;
	}

	public String getTokenPrefix() {
		return tokenPrefix;
	}

	public String getHeaderString() {
		return headerString;
	}

	public int getExpirationTime() {
		return expirationTime;
	}

	public String getSignUpUrl() {
		return signUpUrl;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
