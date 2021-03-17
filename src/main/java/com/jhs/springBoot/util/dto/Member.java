package com.jhs.springBoot.util.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Member {
	private int id;
	private String regDate;
	private String updateDate;
	private String loginId;;
	private String loginPw;
	private String authKey;
	private String name;
	private String nickname;
	private String email;
	private String cellphoneNo;
	private String loginProviderTypeCode;
	private int authLevel;
}
