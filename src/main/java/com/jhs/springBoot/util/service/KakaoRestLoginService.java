package com.jhs.springBoot.util.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jhs.springBoot.util.Util;
import com.jhs.springBoot.util.dto.api.KakaoUser;

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

	public KakaoUser getKakaoUserByAuthorizeCode(String authorizeCode) {
		RestTemplate restTemplate = restTemplateBuilder.build();

		Map<String, Object> respoonseBodyRs = Util
				.getHttpPostResponseBody(new ParameterizedTypeReference<Map<String, Object>>() {
				}, restTemplate, "https://kauth.kakao.com/oauth/token", "grant_type", "authorization_code", "client_id",
						kakaoRestApiKey, "redirect_uri", kakaoRestRedirectUrl, "code", authorizeCode);

		String accessToken = (String) respoonseBodyRs.get("access_token");

		return getKakaoUserByAccessToken(accessToken);
	}

	public KakaoUser getKakaoUserByAccessToken(String accessToken) {
		RestTemplate restTemplate = restTemplateBuilder.build();

		Map<String, String> headerParams = new HashMap<>();
		headerParams.put("Authorization", "Bearer " + accessToken);

		KakaoUser kakaoUser = Util.getHttpPostResponseBody(new ParameterizedTypeReference<KakaoUser>() {
		}, restTemplate, "https://kapi.kakao.com/v2/user/me", headerParams);

		return kakaoUser;
	}
}
