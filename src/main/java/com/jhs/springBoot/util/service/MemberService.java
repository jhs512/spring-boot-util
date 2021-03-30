package com.jhs.springBoot.util.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhs.springBoot.util.dao.MemberDao;
import com.jhs.springBoot.util.dto.Attr;
import com.jhs.springBoot.util.dto.Member;
import com.jhs.springBoot.util.dto.ResultData;
import com.jhs.springBoot.util.dto.api.KapiKakaoCom__v2_user_me__ResponseBody;
import com.jhs.springBoot.util.dto.api.KauthKakaoCom__oauth_token__ResponseBody;
import com.jhs.springBoot.util.util.Util;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private AttrService attrService;
	@Autowired
	private KakaoRestService kakaoRestService;
	
	public enum AttrKey__Type2Code {
		kauthKakaoCom__oauth_token__access_token,
		kauthKakaoCom__oauth_token__refresh_token,
		kauthKakaoCom__oauth_token__scope
	}

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

	public void updateExtraData(int actorId, String type2Code, String value) {
		updateExtraData(actorId, type2Code, value, null);
	}
	
	public void updateExtraData(int actorId, String type2Code, String value, String expireDate) {
		attrService.setValue("member", actorId, "extra", type2Code, value, expireDate);
	}

	public void updateToken(int actorId, MemberService.AttrKey__Type2Code tokenName, String token) {
		updateToken(actorId, tokenName, token, null);
	}
	
	public void updateToken(int actorId, MemberService.AttrKey__Type2Code tokenName, String token, String expireDate) {
		updateExtraData(actorId, tokenName.toString(), token, expireDate);
	}

	public String getToken(int actorId, MemberService.AttrKey__Type2Code tokenName) {
		return getTokenAttr(actorId, tokenName).getValue();
	}
	
	public Attr getTokenAttr(int actorId, MemberService.AttrKey__Type2Code tokenName) {
		return attrService.get("member", actorId, "extra", tokenName.toString());
	}

	public String getToken___kauthKakaoCom__oauth_token__access_token(int actorId) {
		Attr tokenAttr = getTokenAttr(actorId, MemberService.AttrKey__Type2Code.kauthKakaoCom__oauth_token__access_token);
		
		if ( tokenAttr.isTimeToRefresh() ) {
			refreshToken___kauthKakaoCom__oauth_token__access_token(actorId);
			
			tokenAttr = getTokenAttr(actorId, MemberService.AttrKey__Type2Code.kauthKakaoCom__oauth_token__access_token);
		}
		
		return tokenAttr.getValue();
	}

	private boolean refreshToken___kauthKakaoCom__oauth_token__access_token(int actorId) {
		String refreshToken = getToken(actorId, MemberService.AttrKey__Type2Code.kauthKakaoCom__oauth_token__refresh_token);
		
		KauthKakaoCom__oauth_token__ResponseBody rb = kakaoRestService.refreshToken___kauthKakaoCom__oauth_token__access_token(refreshToken);
		
		if ( Util.isEmpty(rb.access_token) ) {
			return false;
		}
		
		updateKakaoRestAccessToken(actorId, rb);
		
		return true;
	}

	public void updateKakaoRestAccessToken(int actorId, KauthKakaoCom__oauth_token__ResponseBody rb) {
		String scope = rb.scope;
		String accessToken = rb.access_token;
		String refreshToken = rb.refresh_token;
		String accessTokenExpireDate = Util.getDateStrAfterSeconds(rb.expires_in);
		String refreshTokenExpireDate = Util.getDateStrAfterSeconds(rb.refresh_token_expires_in);
		
		if ( scope != null ) {
			updateExtraData(actorId, MemberService.AttrKey__Type2Code.kauthKakaoCom__oauth_token__scope.toString(), scope);			
		}
		
		if ( Util.isExists(accessToken) ) {
			updateToken(actorId, MemberService.AttrKey__Type2Code.kauthKakaoCom__oauth_token__access_token, accessToken, accessTokenExpireDate);			
		}
		
		if ( Util.isExists(refreshToken) ) {
			updateToken(actorId, MemberService.AttrKey__Type2Code.kauthKakaoCom__oauth_token__refresh_token, refreshToken, refreshTokenExpireDate);			
		}
	}
}
