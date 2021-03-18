package com.jhs.springBoot.util.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhs.springBoot.util.dto.ResultData;
import com.jhs.springBoot.util.dto.api.Aligo__send__ResponseBody;
import com.jhs.springBoot.util.util.Util;

@Controller
public class UsrHomeController extends BaseController {
	@GetMapping("/usr/home/main")
	public String showMain() {
		return "usr/home/main";
	}

	@GetMapping("/usr/home/doSendSms")
	@ResponseBody
	public ResultData doSendSms(String from, String to, String msg) {
		Aligo__send__ResponseBody rb = Util.sendSms(from, to, msg);

		return new ResultData("S-1", "발송되었습니다.", "from", from, "to", to, "msg", msg, "rb", rb);
	}
}
