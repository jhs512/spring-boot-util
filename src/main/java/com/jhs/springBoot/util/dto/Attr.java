package com.jhs.springBoot.util.dto;

import java.util.Date;

import com.jhs.springBoot.util.util.Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Attr {
	private int id;
	private String regDate;
	private String updateDate;
	private String expireDate;
	private String relTypeCode;
	private int relId;
	private String typeCode;
	private String type2Code;
	private String value;

	public int getExpireRestSeconds() {
		Date nowDate = new Date();
		Date expireDate = Util.getDateFromStr(this.expireDate);

		return (int) ((expireDate.getTime() - nowDate.getTime()) / 1000);
	}

	public boolean isTimeToRefresh() {
		int expireRestSeconds = getExpireRestSeconds();
		
		return expireRestSeconds < 60 * 60 * 1;
	}
}