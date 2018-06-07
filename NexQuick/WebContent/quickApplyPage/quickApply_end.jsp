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
<script src="../Table_Highlight_Vertical_Horizontal/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
<script src="../Table_Highlight_Vertical_Horizontal/vendor/bootstrap/js/popper.js"></script>
<script src="../Table_Highlight_Vertical_Horizontal/vendor/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="../Table_Highlight_Vertical_Horizontal/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../Table_Highlight_Vertical_Horizontal/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
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
%>


<script type="text/javascript">
$(function() {
	$.ajax({
		url : "<%= request.getContextPath() %>/call/registCall.do",
			data : {
				payType : 0,
				payStatus : 1
			},
		dataType : "json",
		method : "POST",
		success : registCall
	});
	
	setTimeout(function(){
        $('.trans--grow').addClass('grow');
    }, 275);
	
	
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
<%-- <%@ include file = "../navigation.jsp" %> --%>
<%@ include file = "../quickApplyPage/quickApply_going_box.jsp" %>

	<!-- 신청한 내용 보여주기? -->
			
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
			  	<th class = "col_100 centerBox"><%= callInfo.getCallNum() %></th>
			  	<th class = "col_100 centerBox"><%= callInfo.getSenderName() %></th>
			  	<th class = "col_100"><%= callInfo.getSenderAddress() %></th>
			  	<th class = "col_100 centerBox"><%= callInfo.getVehicleType() %></th>
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
			  		<% } %>
			  	<% } %>
			  </tbody>
			</table>
	
	
<%@ include file = "../footer.jsp" %>
</body>
</html>