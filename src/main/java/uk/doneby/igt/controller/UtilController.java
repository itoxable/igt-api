package uk.doneby.igt.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.doneby.igt.service.StorageService;

@Controller
@RequestMapping("/api/util")
public class UtilController {
	
	final static Logger LOG = LoggerFactory.getLogger(UtilController.class);
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private StorageService storageService;
	
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/email")
	public ResponseEntity<String> email() throws Exception {
        // Send a message with a POJO - the template reuse the message converter
        System.out.println("Sending an email message.");
        jmsTemplate.convertAndSend("mailbox", "Hello");
		return new ResponseEntity<String>("EMAIL", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/image/{imageName}", method = RequestMethod.GET)
	@ResponseBody
	public void getFile(@PathVariable("imageName") String imageName,  HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uri = request.getRequestURI();
		imageName = uri.substring(uri.indexOf(imageName));
		File file = storageService.load(imageName).toFile();
	    InputStream inputStream = new FileInputStream(file);
	    
	    String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        if(mimeType==null){
            mimeType = "application/octet-stream";
        }
        
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
        response.setContentLength((int)file.length());
	    response.flushBuffer();
        FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
}
