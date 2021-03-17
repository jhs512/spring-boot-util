<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../part/head.jspf"%>

<div class="flex justify-center items-center">
	<form class="border-2 p-2">
		<input class="border-2" type="text" name="loginId"
			placeholder="로그인아이디를 입력해주세요." />
		<input class="border-2" type="text" name="loginPw"
			placeholder="로그인비번을 입력해주세요." />
		<input class="border-2" type="submit" value="로그인" placeholder="로그인" />
	</form>
</div>

<%@ include file="../part/foot.jspf"%>