<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../part/head.jspf"%>

<h1>메인</h1>

<script>
	function MemberDoSendSelfKakaoMessage__submitForm(form) {
		form.msg.value = form.msg.value.trim();
		if (form.msg.value.length == 0)
			return;

		$(form.btnSubmit).prop('disabled', true);

		$.post('../member/doSendSelfKakaoMessage', {
			msg : form.msg.value,
			linkButtonName:form.linkButtonName.value,
			webUrl:form.webUrl.value,
			mobileUrl:form.mobileUrl.value
		}, function(data) {
			$(form.btnSubmit).prop('disabled', false);
			form.msg.focus();
		}, 'json');

		form.msg.value = '';
	}
</script>

<div>
	<form
		onsubmit="MemberDoSendSelfKakaoMessage__submitForm(this); return false;">
		<div>
			<input name="msg" type="text" placeholder="본인 카카오톡으로 발송될 메세지를 입력해주세요." />
		</div>
		<div>
			<input name="linkButtonName" type="text" placeholder="링크버튼 이름" />
		</div>
		<div>
			<input name="webUrl" type="text" placeholder="webUrl" />
		</div>
		<div>
			<input name="mobileUrl" type="text" placeholder="mobileUrl" />
		</div>
		<div>
			<input name="btnSubmit" type="submit" value="나에게 카카오톡 메세지 보내기" />
		</div>
	</form>
</div>

<%@ include file="../part/foot.jspf"%>