<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.0.3/tailwind.min.css" />
</head>
<body>
	<div class="flex justify-center">
		<form class="border-2 p-2">
			<input class="border-2" type="text" name="loginId" placeholder="로그인아이디를 입력해주세요." />
			<input class="border-2" type="text" name="loginPw" placeholder="로그인비번을 입력해주세요." />
			<input class="border-2" type="submit" value="로그인" placeholder="로그인" />
			<a href="goKakaoLoginPage" class="border-2">카카오톡으로 로그인하기</a>
		</form>
	</div>
</body>
</html>