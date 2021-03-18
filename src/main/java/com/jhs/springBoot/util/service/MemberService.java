package com.jhs.springBoot.util.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhs.springBoot.util.dao.MemberDao;
import com.jhs.springBoot.util.dto.Member;
import com.jhs.springBoot.util.dto.ResultData;
import com.jhs.springBoot.util.dto.api.KapiKakaoCom__v2_user_me__ResponseBody;
import com.jhs.springBoot.util.util.Util;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;

	public Member getMemberByOnLoginProviderMemberId(String loginProviderTypeCode, int onLoginProviderMemberId) {
		return memberDao.getMemberByOnLoginProviderMemberId(loginProviderTypeCode, onLoginProviderMemberId);
	}

	public ResultData updateMember(Member member, KapiKakaoCom__v2_user_me__ResponseBody kakaoUser) {
		Map<String, Object> param = Util.mapOf("id", member.getId());

		param.put("nickname", kakaoUser.kakao_account.profile.nickname);

		if (kakaoUser.kakao_account.email != null && kakaoUser.kakao_account.email.length() != 0) {
			param.put("email", kakaoUser.kakao_account.email);
		}

		memberDao.modify(param);

		return new ResultData("S-1", "회원정보가 수정되었습니다.", "id", member.getId());
	}

	public ResultData join(KapiKakaoCom__v2_user_me__ResponseBody kakaoUser) {
		String loginProviderTypeCode = "kakaoRest";
		int onLoginProviderMemberId = kakaoUser.id;

		Map<String, Object> param = Util.mapOf("loginProviderTypeCode", loginProviderTypeCode);
		param.put("onLoginProviderMemberId", onLoginProviderMemberId);

		String loginId = loginProviderTypeCode + "___" + onLoginProviderMemberId;

		param.put("loginId", loginId);
		param.put("loginPw", Util.getUUIDStr());

		param.put("nickname", kakaoUser.kakao_account.profile.nickname);
		param.put("name", kakaoUser.kakao_account.profile.nickname);
		param.put("email", kakaoUser.kakao_account.email);

		memberDao.join(param);

		int id = Util.getAsInt(param.get("id"), 0);

		return new ResultData("S-1", "가입에 성공하였습니다.", "id", id);
	}

	public Member getMemberByAuthKey(String authKey) {
		return memberDao.getMemberByAuthKey(authKey);
	}

	public Member getMember(int id) {
		return memberDao.getMember(id);
	}

	public boolean isAdmin(Member actor) {
		return actor.getAuthLevel() == 7;
	}
}
