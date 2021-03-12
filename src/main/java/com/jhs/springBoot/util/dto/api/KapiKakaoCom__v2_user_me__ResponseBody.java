package com.jhs.springBoot.util.dto.api;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class KapiKakaoCom__v2_user_me__ResponseBody {
	public int id;
	public Map<String, Object> properties;
	public Map<String, Object> kakao_account;
	public boolean email_needs_agreement;
	public String email;

	public String nickname;
	public String profile_image;
	public String thumbnail_image;
}
