package com.jhs.springBoot.util.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jhs.springBoot.util.dto.ResultData;
import com.jhs.springBoot.util.dto.api.KapiKakaoCom__v2_api_talk_memo_default_send__ResponseBody;
import com.jhs.springBoot.util.dto.api.KapiKakaoCom__v2_user_me__ResponseBody;
import com.jhs.springBoot.util.dto.api.KauthKakaoCom__oauth_token__ResponseBody;
import com.jhs.springBoot.util.util.Util;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KakaoRestService {
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;
	@Autowired
	private MemberService memberService;

	@Value("${custom.kakaoRest.apiKey}")
	private String kakaoRestApiKey;
	@Value("${custom.kakaoRest.redirectUrl}")
	private String kakaoRestRedirectUrl;

	public void getAccessToken(String kakaoAppKeyRestApi) {
	}

	public String getKakaoLoginPageUrl() {
		StringBuilder sb = new StringBuilder();
		sb.append("https://kauth.kakao.com/oauth/authorize");
		sb.append("?client_id=" + kakaoRestApiKey);
		sb.append("&redirect_uri=" + Util.getUrlEncoded(kakaoRestRedirectUrl));
		sb.append("&response_type=code");

		return sb.toString();
	}

	public KapiKakaoCom__v2_user_me__ResponseBody getKakaoUserByAuthorizeCode(String authorizeCode) {
		RestTemplate restTemplate = restTemplateBuilder.build();

		Map<String, String> params = Util.getNewMapStringString();
		params.put("grant_type", "authorization_code");
		params.put("client_id", kakaoRestApiKey);
		params.put("redirect_uri", kakaoRestRedirectUrl);
		params.put("code", authorizeCode);

		KauthKakaoCom__oauth_token__ResponseBody respoonseBodyRs = Util
				.getHttpPostResponseBody(new ParameterizedTypeReference<KauthKakaoCom__oauth_token__ResponseBody>() {
				}, restTemplate, "https://kauth.kakao.com/oauth/token", params, null);

		return getKakaoUserByAccessToken(respoonseBodyRs);
	}

	public KapiKakaoCom__v2_user_me__ResponseBody getKakaoUserByAccessToken(
			KauthKakaoCom__oauth_token__ResponseBody kauthKakaoCom__oauth_token__ResponseBody) {
		RestTemplate restTemplate = restTemplateBuilder.build();

		Map<String, String> headerParams = new HashMap<>();
		headerParams.put("Authorization", "Bearer " + kauthKakaoCom__oauth_token__ResponseBody.access_token);

		KapiKakaoCom__v2_user_me__ResponseBody respoonseBody = Util
				.getHttpPostResponseBody(new ParameterizedTypeReference<KapiKakaoCom__v2_user_me__ResponseBody>() {
				}, restTemplate, "https://kapi.kakao.com/v2/user/me", null, headerParams);

		respoonseBody.kauthKakaoCom__oauth_token__ResponseBody = kauthKakaoCom__oauth_token__ResponseBody;

		return respoonseBody;
	}

	// 나에게 메시지 보내기
	public ResultData doSendSelfKakaoMessage(int actorId, String msg, String linkBtnName, String webLink,
			String mobileLink) {
		String accessToken = memberService.getToken(actorId, "kauthKakaoCom__oauth_token__access_token");

		// 마찬가지로 access_Token값을 가져와 access_Token값을 통해 로그인되어있는 사용자를 확인합니다.
		String reqURL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

		Map<String, String> headerParams = new HashMap<>();
		headerParams.put("Authorization", "Bearer " + accessToken);

		Map<String, Object> templateObjectParam = Util.mapOf();
		templateObjectParam.put("object_type", "text");
		templateObjectParam.put("text", msg);

		if (linkBtnName != null) {
			templateObjectParam.put("button_title", linkBtnName);
		}

		Map<String, Object> params__link = Util.mapOf();
		params__link.put("web_url", webLink);
		params__link.put("mobile_web_url", mobileLink);

		templateObjectParam.put("link", params__link);

		String templateObjectParamString = Util.getJsonStringFromObject(templateObjectParam);

		Map<String, String> params = new HashMap<String, String>();
		params.put("template_object", templateObjectParamString);

		RestTemplate restTemplate = restTemplateBuilder.build();
		KapiKakaoCom__v2_api_talk_memo_default_send__ResponseBody kapiKakaoCom__v2_api_talk_memo_default_send__ResponseBody = Util
				.getHttpPostResponseBody(
						new ParameterizedTypeReference<KapiKakaoCom__v2_api_talk_memo_default_send__ResponseBody>() {
						}, restTemplate, reqURL, params, headerParams);

		return new ResultData("S-1", "성공하였습니다.", "kapiKakaoCom__v2_api_talk_memo_default_send__ResponseBody", kapiKakaoCom__v2_api_talk_memo_default_send__ResponseBody);
	}

}
