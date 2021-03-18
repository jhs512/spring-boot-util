package com.jhs.springBoot.util.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jhs.springBoot.util.dto.api.KapiKakaoCom__v2_user_me__ResponseBody;
import com.jhs.springBoot.util.dto.api.KauthKakaoCom__oauth_token__ResponseBody;
import com.jhs.springBoot.util.util.Util;

@Service
public class KakaoRestLoginService {
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

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

		return getKakaoUserByAccessToken(respoonseBodyRs.access_token);
	}

	public KapiKakaoCom__v2_user_me__ResponseBody getKakaoUserByAccessToken(String accessToken) {
		RestTemplate restTemplate = restTemplateBuilder.build();

		Map<String, String> headerParams = new HashMap<>();
		headerParams.put("Authorization", "Bearer " + accessToken);

		KapiKakaoCom__v2_user_me__ResponseBody respoonseBody = Util
				.getHttpPostResponseBody(new ParameterizedTypeReference<KapiKakaoCom__v2_user_me__ResponseBody>() {
				}, restTemplate, "https://kapi.kakao.com/v2/user/me", null, headerParams);

		return respoonseBody;
	}
}
