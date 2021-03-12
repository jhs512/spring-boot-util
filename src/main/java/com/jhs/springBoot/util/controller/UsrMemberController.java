package com.jhs.springBoot.util.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhs.springBoot.util.dto.api.KapiKakaoCom__v2_user_me__ResponseBody;
import com.jhs.springBoot.util.service.KakaoRestLoginService;

@Controller
public class UsrMemberController {
	@Value("${custom.kakaoRest.apiKey}")
	private String kakaoRestApiKey;

	@Autowired
	private KakaoRestLoginService kakaoRestLoginService;

	@GetMapping("/usr/member/login")
	public String showLogin() {
		return "usr/member/login";
	}

	@GetMapping("/usr/member/goKakaoLoginPage")
	public String goKakaoLoginPage() {

		String url = kakaoRestLoginService.getKakaoLoginPageUrl();

		return "redirect:" + url;
	}

	@GetMapping("/usr/member/doLoginByKakoRest")
	@ResponseBody
	public KapiKakaoCom__v2_user_me__ResponseBody doLoginByKakoRest(String code) {

		KapiKakaoCom__v2_user_me__ResponseBody kakaoUser = kakaoRestLoginService.getKakaoUserByAuthorizeCode(code);

		return kakaoUser;
	}
}
