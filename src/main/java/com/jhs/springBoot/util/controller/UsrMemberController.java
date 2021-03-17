package com.jhs.springBoot.util.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhs.springBoot.util.dto.Member;
import com.jhs.springBoot.util.dto.ResultData;
import com.jhs.springBoot.util.dto.api.KapiKakaoCom__v2_user_me__ResponseBody;
import com.jhs.springBoot.util.service.KakaoRestLoginService;
import com.jhs.springBoot.util.service.MemberService;

@Controller
public class UsrMemberController {
	@Value("${custom.kakaoRest.apiKey}")
	private String kakaoRestApiKey;

	@Autowired
	private MemberService memberService;

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
	public ResultData doLoginByKakoRest(HttpSession session, String code) {
		KapiKakaoCom__v2_user_me__ResponseBody kakaoUser = kakaoRestLoginService.getKakaoUserByAuthorizeCode(code);

		Member member = memberService.getMemberByOnLoginProviderMemberId("kakaoRest", kakaoUser.id);
		
		ResultData rd = null;

		if (member != null) {
			rd = memberService.updateMember(member, kakaoUser);
		}
		else {
			rd = memberService.join(kakaoUser);
		}
		
		int id = (int)rd.getBody().get("id");
		
		session.setAttribute("loginedMemberId", id);
		
		return new ResultData("S-1", "로그인 되었습니다.", "id", id);
	}
}
