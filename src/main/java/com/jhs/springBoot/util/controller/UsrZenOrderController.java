package com.jhs.springBoot.util.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsrZenOrderController extends BaseController {
	@GetMapping("/usr/zenOrder/order")
	public String showLogin() {
		return "usr/zenOrder/order";
	}
}
