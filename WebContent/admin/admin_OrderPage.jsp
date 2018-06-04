<%@ page import = "com.nexquick.model.vo.OrderInfo" %>
<%@ page import = "java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="<%=request.getContextPath() %>/Table_Highlight_Vertical_Horizontal/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->	
<!--===============================================================================================-->	
	<link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/Table_Highlight_Vertical_Horizontal/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/Table_Highlight_Vertical_Horizontal/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/Table_Highlight_Vertical_Horizontal/vendor/animate/animate.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/Table_Highlight_Vertical_Horizontal/vendor/select2/select2.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/Table_Highlight_Vertical_Horizontal/vendor/perfect-scrollbar/perfect-scrollbar.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/Table_Highlight_Vertical_Horizontal/css/util.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/Table_Highlight_Vertical_Horizontal/css/main.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/indexStyle.css">

	<% List<OrderInfo> oiList = (List<OrderInfo>) request.getSession().getAttribute("AllOrderForAdmin"); %>

<title>NexQuick :: 관리자 페이지 :: 전체 신청관리</title>
<script type="text/javascript">
	$(function() {
		
	});
</script>
</head>
<body>
<%@ include file = "../navigation.jsp" %>

	<h2 class = "centerBox quickFirstTitle mb-5 text-conceptColor">
		전체 신청관리
	</h2>
	<div class="limiter">
		<div class="container-table100" style = "top:0em!important;">
			<div class="wrap-table100">
				<div class="table100 ver3 m-b-110">
					<table data-vertable="ver3">
						<thead>
							<tr class="row100 head">
								<th class="column100 column1" data-column="column1">ID</th>
								<th class="column100 column2" data-column="column2">이름</th>
								<th class="column100 column3" data-column="column3">전화번호</th>
								<th class="column100 column4" data-column="column4">차종</th>
								<th class="column100 column5" data-column="column5">면허번호</th>
								<th class="column100 column6" data-column="column6">등록현황</th>
								<th class="column100 column7" data-column="column7">현재상황</th>
								<th class="column100 column8" data-column="column8">평점</th>
							</tr>
						</thead>
						<tbody>
						<% if(oiList != null) { 
							int countNumber = 1;
							for(OrderInfo oi : oiList) {
						%>
							<tr class="row100">
								<td class="column100 column1" data-column="column1"><%= oi.getCallNum() %></td>
								<td class="column100 column2" data-column="column2"><%= oi.getOrderNum() %></td>
								<td class="column100 column3" data-column="column3"><%= oi.getReceiverName() %></td>
								<td class="column100 column4" data-column="column4"><%= oi.getReceiverPhone() %></td>
								<td class="column100 column5" data-column="column5"><%= oi.getReceiverAddress() %></td>
								<td class="column100 column6" data-column="column6"><%= oi.getDistance() %></td>
								<td class="column100 column7" data-column="column7"><%= oi.getMemo() %></td>
								<td class="column100 column8" data-column="column8">100</td>
							</tr>
							<% countNumber++; } %>
						<% } %>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

<!-- javascript가 여기에 들어가야 작동됨 -->
<!--===============================================================================================-->	
	<script src="<%=request.getContextPath() %>/Table_Highlight_Vertical_Horizontal/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
	<script src="<%=request.getContextPath() %>/Table_Highlight_Vertical_Horizontal/vendor/bootstrap/js/popper.js"></script>
	<script src="<%=request.getContextPath() %>/Table_Highlight_Vertical_Horizontal/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="<%=request.getContextPath() %>/Table_Highlight_Vertical_Horizontal/vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
	<script src="<%=request.getContextPath() %>/Table_Highlight_Vertical_Horizontal/js/main.js"></script>
<!-- 왜인지는 모르겠음 -->
	


<%@ include file = "../footer.jsp" %>
</body>
</html>