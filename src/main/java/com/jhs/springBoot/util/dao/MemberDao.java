package com.jhs.springBoot.util.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jhs.springBoot.util.dto.Member;

@Mapper
public interface MemberDao {
	Member getMemberByOnLoginProviderMemberId(@Param("loginProviderTypeCode") String loginProviderTypeCode,
			@Param("onLoginProviderMemberId") int onLoginProviderMemberId);

	void modify(Map<String, Object> param);

	void join(Map<String, Object> param);

	Member getMemberByAuthKey(@Param("authKey") String authKey);

	Member getMember(@Param("id") int id);
	
	Member getMemberByLoginId(@Param("loginId") String loginId);
}
