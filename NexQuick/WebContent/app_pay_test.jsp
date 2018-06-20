<!-- 아임포트 자바스크립트는 jQuery 기반으로 개발되었습니다 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
<script type="text/javascript" src="https://service.iamport.kr/js/iamport.payment-1.1.5.js" ></script>
<% int totalPrice = (int) request.getSession().getAttribute("appTotalPrice"); %>

<script type="text/javascript">
var IMP = window.IMP; // 생략가능
IMP.init('imp94690506'); // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용

/* 중략 */
$(function() {
	
	//onclick, onload 등 원하는 이벤트에 호출합니다
	IMP.request_pay({
	    pg : 'inicis', // version 1.1.0부터 지원.
	    pay_method : 'card',
	    merchant_uid : 'merchant_' + new Date().getTime(),
	    name : 'NexQuick 결제',
	    amount : <%= totalPrice %>,
	    buyer_email : 'mmk8292@naver.com',
	    buyer_name : "김민규",
	    buyer_tel : "01049408292",
	    buyer_addr : $("#senderAddress").text(),
	    buyer_postcode : '000-000',
	    m_redirect_url :'<%= request.getContextPath() %>/pay_complete.jsp',
	    app_scheme : 'paying'
	}, function(rsp) {
	    if ( rsp.success ) {
	        var msg = '결제가 완료되었습니다.';
	        msg += '고유ID : ' + rsp.imp_uid;
	        msg += '상점 거래ID : ' + rsp.merchant_uid;
	        msg += '결제 금액 : ' + rsp.paid_amount;
	        msg += '카드 승인번호 : ' + rsp.apply_num;
	    } else {
	        var msg = '결제에 실패하였습니다.';
	        msg += '에러내용 : ' + rsp.error_msg;
	    }
	
	    alert(msg);
	});
});
</script>
</head>
<body>
결제창으로 이동합니당 기기 ~

</body>
</html>