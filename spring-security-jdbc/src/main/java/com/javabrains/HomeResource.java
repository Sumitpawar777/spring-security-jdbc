package com.javabrains;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {
//
	@GetMapping("/")
	public String home() {
		return "Welcome";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "Welcome admin";
	}
	
	@GetMapping("/user")
	public String user() {
		return "Welcome user";
	}
	
	
}
