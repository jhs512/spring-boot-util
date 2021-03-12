package com.jhs.springBoot.util.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsrMemberController {
	@GetMapping("/usr/member/login")
	public String showLogin() {
		return "usr/member/login";
	}
}
