package uk.doneby.igt.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/util")
public class UtilController {
	
	final static Logger LOG = LoggerFactory.getLogger(UtilController.class);
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@RequestMapping(method = RequestMethod.GET, value = "/password-encode", produces="application/json")
	@ResponseBody
	public ResponseEntity<String> encodePassword(@RequestParam(required = true) String password) throws Exception {
		
		LOG.info("Encoding Password: " + password);
		
		return new ResponseEntity<String>(passwordEncoder.encode(password), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/test", produces="application/json")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<String> test() throws Exception {
		return new ResponseEntity<String>("Test", HttpStatus.OK);
	}
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@RequestMapping(method = RequestMethod.GET, value = "/email", produces="application/json")
	public ResponseEntity<String> email() throws Exception {
        // Send a message with a POJO - the template reuse the message converter
        System.out.println("Sending an email message.");
        jmsTemplate.convertAndSend("mailbox", "Hello");
		return new ResponseEntity<String>("EMAIL", HttpStatus.OK);
	}
	
}
