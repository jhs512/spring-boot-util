package com.jhs.springBoot.util.dto.api;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jhs.springBoot.util.util.Util;

public class KapiKakaoCom__v2_api_talk_memo_default_send__RequestBody__object_type_text {
	@JsonIgnore
	public static final String API_URL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

	public static Map<String, String> make(String text, String button_title, String web_link, String mobile_web_link) {
		KapiKakaoCom__v2_api_talk_memo_default_send__RequestBody__object_type_text rb;

		rb = new KapiKakaoCom__v2_api_talk_memo_default_send__RequestBody__object_type_text(text, button_title,
				web_link, mobile_web_link);

		return Util.getMapFromObject(rb);
	}

	public String template_object;

	private KapiKakaoCom__v2_api_talk_memo_default_send__RequestBody__object_type_text(String text, String button_title,
			String web_link, String mobile_web_link) {
		TemplateObject a_template_object = new TemplateObject();

		a_template_object.object_type = "text";
		a_template_object.text = text;
		a_template_object.button_title = button_title;
		a_template_object.link = new TemplateObject.Link();
		a_template_object.link.web_lnik = web_link;
		a_template_object.link.mobile_web_lnik = mobile_web_link;

		template_object = Util.getJsonStringFromObject(a_template_object);
	}

	public static class TemplateObject {
		public String object_type;
		public String text;
		public String button_title;
		public Link link;

		public static class Link {
			public String web_lnik;
			public String mobile_web_lnik;
		}
	}
}
