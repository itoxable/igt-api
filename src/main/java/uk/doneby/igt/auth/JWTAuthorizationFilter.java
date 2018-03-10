package uk.doneby.igt.auth;

import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import uk.doneby.igt.model.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
	
	@Value("${auth.secret}")
	private String secret;
	
	@Value("${auth.token_prefix}")
	private String tokenPrefix;
	
	@Value("${auth.header_string}")
	private String headerString;	
	
	@Autowired
    private TokenUtils tokenUtils;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
//    public JWTAuthorizationFilter(AuthenticationManager authManager, String secret, String tokenPrefix, String headerString) {
//        super(authManager);
//        this.secret = secret;
//        this.tokenPrefix = tokenPrefix;
//        this.headerString = headerString;
//    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(headerString);
        if (header == null || !header.startsWith(tokenPrefix)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
    
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(headerString);
        if (token != null && tokenUtils.isTokenValid(token.replace(tokenPrefix, ""))) {
            // parse the token.
            String username = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace(tokenPrefix, ""))
                    .getBody()
                    .getSubject();
            if (username != null) {
            		
            		//this.getAuthentication(request).getAuthorities()
            		User user = (User)userDetailsService.loadUserByUsername(username);
                return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            }
            return null;
        }
        return null;
    }
}
