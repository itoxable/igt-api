package uk.doneby.igt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.doneby.igt.auth.TokenUtils;
import uk.doneby.igt.model.User;
import uk.doneby.igt.repository.ProductRepository;
import uk.doneby.igt.repository.UserRepository;


@RestController
@RequestMapping("/api/user")
public class UserController {
	
	final static Logger LOG = LoggerFactory.getLogger(UtilController.class);

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private ProductRepository productRepository;
	
	@Autowired
    private TokenUtils tokenUtils;
	
	@Value("${auth.token_prefix}")
	private String tokenPrefix;
	
	@Value("${auth.header_string}")
	private String headerString;	
    
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    
    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    
    @PutMapping("/change-password")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public boolean changePassword(@RequestBody String oldPassword, @RequestBody String password) throws Exception {
	    	password = bCryptPasswordEncoder.encode(password);
	    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	user = userRepository.findOne(user.getId());
	    	if (bCryptPasswordEncoder.encode(oldPassword).equals(user.getPassword())) {
	    		user.setPassword(bCryptPasswordEncoder.encode(password));
	        userRepository.save(user);
	        return true;
	    	} else {
	    		throw new Exception("Invalid Password");
	    	}
    }
    
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public User updateUser(@RequestBody User user) throws Exception {
	    	User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	if (currentUser.getId() != user.getId()) {
	    		throw new AuthorizationServiceException("Invalid User");
	    	}
	    	return userRepository.save(user);
    }
    
    @PostMapping("/me")
	@PreAuthorize("hasAuthority('ROLE_USER')")
    public User getUser() {
    		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LOG.info(user.toString());
		return user;
    }
    
    @GetMapping("/me")
   	@PreAuthorize("hasAuthority('ROLE_USER')")
    public User saveUser() {
   		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
   		LOG.info(user.toString());
   		return user;
    }
    
//    @GetMapping("/me")
//   	@PreAuthorize("hasAuthority('ROLE_USER')")
//    public User getProducts() {
//    	productRepository.findAll()
//   		return user;
//    }
    

}
