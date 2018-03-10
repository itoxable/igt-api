package uk.doneby.igt.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/auth")
public class SocialController {
	@RequestMapping(value = "/facebook", method = RequestMethod.POST)
	public void facebook(Object data) {
		System.out.println(data);
	}
	
	@RequestMapping(value = "/google", method = RequestMethod.POST)
	public void google(Object data) {
		System.out.println(data);
	}	
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public String test() {
		return "{Yes}";
	}	
}
