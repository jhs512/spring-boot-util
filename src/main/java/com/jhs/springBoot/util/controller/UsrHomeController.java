package com.jhs.springBoot.util.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsrHomeController extends BaseController {
	@GetMapping("/usr/home/main")
	public String showMain() {
		return "usr/home/main";
	}
}
