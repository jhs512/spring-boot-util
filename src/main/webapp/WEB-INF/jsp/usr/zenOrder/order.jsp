<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../part/head.jspf"%>

<!-- iamport.payment.js -->
<script type="text/javascript"
	src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
<script>
	const IMP = window.IMP; // 생략해도 괜찮습니다.
	IMP.init("imp56355768"); // "imp00000000" 대신 발급받은 "가맹점 식별코드"를 사용합니다.

	function startPay() {
		// IMP.request_pay(param, callback) 호출
		IMP.request_pay({ // param
			pg: "kakaopay",
			pay_method: "kakaopay",
			erchant_uid: "ORD20180131-0000011",
			name: "노르웨이 회전 의자",
	    	amount: 1000,
	    	buyer_email: "gildong@gmail.com",
	    	buyer_name: "홍길동",
	    	buyer_tel: "010-4242-4242",
	    	buyer_addr: "서울특별시 강남구 신사동",
	    	buyer_postcode: "01181"
		}, rsp => { // callback
			if (rsp.success) {
				
	        } else {
	        	
			}
		});
	}
</script>

<h1>주문</h1>

<button onclick="startPay();">결제시작</button>

<%@ include file="../part/foot.jspf"%>