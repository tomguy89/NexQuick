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
	
	<% List<CSInfo> csList = (List<CSInfo>) request.getSession().getAttribute("csList"); %>
	
<title>NexQuick :: 관리자 페이지 :: 사용자 관리</title>
<script type="text/javascript">

function onopen(buttonId)
{
	var url =
	"http://www.ftc.go.kr/bizCommPop.do?wrkr_no="+$(buttonId).text();
	window.open(url, "bizCommPop", "width=750, height=700;");
}

$(function() {
	
});
</script>
</head>
<body>
<%@ include file = "../navigation.jsp" %>

	<h2 class = "centerBox quickFirstTitle mb-5 text-conceptColor">
		사용자 관리
	</h2>
	<div class="limiter">
		<div class="container-table100" style = "top:0em!important;">
			<div class="wrap-table100">
				<div class="table100 ver3 m-b-110">
					<table data-vertable="ver3">
						<thead>
							<tr class="row100 head">
								<th class="column100 column1" data-column="column1">고객ID</th>
								<th class="column100 column2" data-column="column2">이름</th>
								<th class="column100 column3" data-column="column3">전화번호</th>
								<th class="column100 column4" data-column="column4">고객분류</th>
								<th class="column100 column5" data-column="column5">회원등급</th>
								<th class="column100 column6" data-column="column6">마일리지</th>
								<th class="column100 column7" data-column="column7">사업자등록번호</th>
								<th class="column100 column8" data-column="column8">법인명</th>
								<th class="column100 column9" data-column="column9">부서명</th>
							</tr>
						</thead>
						<tbody>
						<!-- 고객ID 누르면 주문관리 페이지로 넘어가서 주문한 리스트 보여주게끔(자동검색). -->
						<% if(csList != null) { 
							int countNumber = 1;
							for(CSInfo ci : csList) {
						%>
							<tr class="row100">
								<td class="column100 column1" data-column="column1"><%= ci.getCsId() %></td>
								<td class="column100 column2" data-column="column2"><%= ci.getCsName() %></td>
								<td class="column100 column3" data-column="column3"><%= ci.getCsPhone() %></td>
								<td class="column100 column4" data-column="column4"><%= ci.getCsType() %></td>
								<td class="column100 column5" data-column="column5"><%= ci.getCsGrade() %></td>
								<td class="column100 column6" data-column="column6"><%= ci.getCsMilege() %></td>
								<td class="column100 column7" data-column="column7">
								<% if(ci.getCsBusinessNumber() != null) { %>
									<button id = "btn<%= countNumber %>" onclick = "onopen(this);"><%= ci.getCsBusinessNumber() %></button>
								<%} %>
								</td>
								<td class="column100 column8" data-column="column8">
								<% if(ci.getCsBusinessName() != null) { %>
									<%= ci.getCsBusinessName() %>
								<%} %>
								</td>
								<td class="column100 column9" data-column="column9">
								<% if(ci.getCsDepartment() != null) { %>
									<%= ci.getCsDepartment() %>
								<%} %>
								</td>
							</tr>
							<% countNumber++; } %>
						<% } %>
							<!-- 여기까지 -->
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