package com.jhs.springBoot.util.dto.api;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class KakaoUser {
	private Map<String, Object> properties;
	private Map<String, Object> kakao_account;
	private boolean email_needs_agreement;
	private String email;
	
	private String nickname;
	private String profile_image;
	private String thumbnail_image;
}
