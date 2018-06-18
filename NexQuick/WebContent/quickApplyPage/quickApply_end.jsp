<%@ page import = "com.nexquick.model.vo.CallInfo" %>
<%@ page import = "com.nexquick.model.vo.OrderInfo" %>
<%@ page import = "java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NexQuick :: 퀵 신청하기 - 완료</title>
<!--===============================================================================================-->	
<script src="<%=request.getContextPath() %>/Table_Fixed_Header/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
<script src="../Table_Fixed_Header/vendor/bootstrap/js/popper.js"></script>
<script src="../Table_Fixed_Header/vendor/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="../Table_Fixed_Header/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../Table_Fixed_Header/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/indexStyle.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/indexStyle_radio.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/checkBoxstyle.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/tableStyle.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/momentjs/2.14.1/moment.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<% List<OrderInfo> orderList = (List<OrderInfo>)request.getSession().getAttribute("ordersByCall");
	CallInfo callInfo = (CallInfo) request.getSession().getAttribute("getCallByCallNum");
	int payType = (int) request.getSession().getAttribute("payType");
	int payStatus = (int) request.getSession().getAttribute("payStatus");
	int totalPrice = 0;
	String vehicleType = null; 
%>


<script type="text/javascript">
$(function() {
	$.ajax({
		url : "<%= request.getContextPath() %>/call/registCall.do",
			data : {
				payType : <%= payType %>,
				payStatus : <%= payStatus %>,
				totalPrice : $("#totalPrice").val()
			},
		dataType : "json",
		method : "POST",
		success : registCall
	});
	
	setTimeout(function(){
        $('.trans--grow').addClass('grow');
    }, 275);
	
	
	setInterval(function() {
		$.ajax({
			url : "<%= request.getContextPath() %>/account/sessionCheck.do",
			dataType : "json",
			method : "POST",
			success : function(JSONDocument) {
				if(JSONDocument) {
					console.log("로그인 중");
				} else if (!JSONDocument) {
					alert("로그아웃 되었습니다. 다시 로그인 해주세요.");
					location.replace("<%= request.getContextPath() %>/index.jsp");
				}
			}
		})
	}, 5000);
	
	
	
});

function registCall(JSONDocument) {
	if(JSONDocument) {
		console.log("저장됨");
	} else {
		console.log("저장안됨");
	}
}	
	
</script>
</head>
<body>
<%@ include file = "../navigation.jsp" %>
<%@ include file = "../quickApplyPage/quickApply_going_box.jsp" %>

	<!-- 신청한 내용 보여주기? -->
			<h2 class = "centerBox">
				<% if(payType == 0 || payType == 1) { %>
					입금이 완료되었습니다. 퀵 신청내역을 확인하세요.
				<% } else if (payType == 2) {%>
					현장결제(선불) - 카드 신청이 완료되었습니다. 퀵 신청내역을 확인하세요.				
				<% } else if (payType == 3) {%>
					현장결제(선불) - 현금 신청이 완료되었습니다. 퀵 신청내역을 확인하세요.				
				<% } else if (payType == 4) {%>
					현장결제(후불) - 카드 신청이 완료되었습니다. 퀵 신청내역을 확인하세요.
				<% } else if (payType == 5) {%>
					현장결제(후불) - 현금 신청이 완료되었습니다. 퀵 신청내역을 확인하세요.
				<% } else if (payType == 6) {%>
					신용결제(법인회원 전용) 신청이 완료되었습니다. 퀵 신청내역을 확인하세요.
				<% } %>				
			</h2>
			<div class = "centerBox mt-5">
				<h1>배송 정보</h1>
			</div>
			<table class="table100 mb-5">
			  <thead>
			    <tr>
			      <th scope="col" class = "col_100 centerBox">콜 번호</th>
			      <th scope="col" class = "col_100 centerBox">보내는 사람</th>
			      <th scope="col" class = "col_100 centerBox">보내는 사람 주소</th>
			      <th scope="col" class = "col_100 centerBox">배송수단</th>
			    </tr>
			  </thead>
			  <tbody>
			  <tr>
			  <% if(callInfo != null) { %>
			  
			  <% if(callInfo.getVehicleType()==0) { vehicleType = "오토바이";%>
			  <% } else if (callInfo.getVehicleType()==1) { vehicleType = "다마스"; %>
			  <% } else if (callInfo.getVehicleType()==2) { vehicleType = "라보"; %>
			  <% } else if (callInfo.getVehicleType()==3) { vehicleType = "트럭"; %>
			  <% } else if (callInfo.getVehicleType()==4) { vehicleType = "기타"; %>
			  <% } %>
			  	<th class = "col_100 centerBox"><%= callInfo.getCallNum() %></th>
			  	<th class = "col_100 centerBox"><%= callInfo.getSenderName() %></th>
			  	<th class = "col_100"><%= callInfo.getSenderAddress() %></th>
			  	<th class = "col_100 centerBox"><%= vehicleType %></th>
			  <% } %>
			  </tr>
  			  <tr>
			  	
			  </tr>
			  </tbody>
			</table>
			
			
			<div class = "centerBox mt-5">
				<h1>상세 정보</h1>
			</div>
			<table class="table100">
			  <thead>
			    <tr>
			      <th scope="col" class = "col_100 centerBox">오더 번호</th>
			      <th scope="col" class = "col_100 centerBox">받는 사람</th>
			      <th scope="col" class = "col_100 centerBox">받는 사람 주소</th>
			      <th scope="col" class = "col_100 centerBox">요금</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<% if(orderList != null) {
					for(OrderInfo oi : orderList) {			  		
			  	%>
			  	<tr>
			  		<th class = "col_100 centerBox"><%= oi.getOrderNum() %></th>
				  	<th class = "col_100 centerBox"><%= oi.getReceiverName() %></th>
				  	<th class = "col_100"><%= oi.getReceiverAddress() %></th>
				  	<th class = "col_100 centerBox"><%= oi.getOrderPrice() %></th>
			  	</tr>
			  		
			  		<% totalPrice += oi.getOrderPrice(); } %>
			  	<% } %>
			  </tbody>
			</table>
		<input type = "number" id = "totalPrice" style = "display: none;" value = "<%= totalPrice %>" />
	
<%@ include file = "../footer.jsp" %>
</body>
</html>