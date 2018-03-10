package uk.doneby.igt.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.doneby.igt.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
	private String secret;
	
	private int expirationTime;
	
	private String tokenPrefix;
	
	private String headerString;	
	
	private AuthenticationManager authenticationManager;
	
	private TokenUtils tokenUtils;
	    
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, TokenUtils tokenUtils) {
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
        this.expirationTime = tokenUtils.getExpirationTime();
        this.tokenPrefix = tokenUtils.getTokenPrefix();
        this.headerString = tokenUtils.getHeaderString();
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
    	System.out.println("***Authentication***");
        try {
            User user = new ObjectMapper() .readValue(req.getInputStream(), User.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                    		user.getUsername(),
                    		user.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
    		User user = (User) auth.getPrincipal();
        String token = tokenUtils.createToken(user);
        System.out.println(auth);
        ObjectMapper objectMapper = new ObjectMapper();
        String usr = objectMapper.writeValueAsString(user);
        res.getWriter().append(usr);
        res.addHeader("Access-Control-Expose-Headers", "Authorization, X-Custom");
        res.addHeader(headerString, tokenPrefix + token);
    }
}
