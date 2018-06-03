<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="<%=request.getContextPath() %>/Table_Highlight_Vertical_Horizontal/vendor/jquery/jquery-3.2.1.min.js"></script>
<!-- 임시 테이블 -->
<link rel="stylesheet" type="text/css" href="../Table_Highlight_Vertical_Horizontal/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="../Table_Highlight_Vertical_Horizontal/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="../Table_Highlight_Vertical_Horizontal/vendor/animate/animate.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="../Table_Highlight_Vertical_Horizontal/vendor/select2/select2.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="../Table_Highlight_Vertical_Horizontal/vendor/perfect-scrollbar/perfect-scrollbar.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="../Table_Highlight_Vertical_Horizontal/css/util.css">
<link rel="stylesheet" type="text/css" href="../Table_Highlight_Vertical_Horizontal/css/main.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/indexStyle.css">
<!-- 임시 링크 -->


<title>NexQuick</title>
<!-- 배송중인 배달이 있다면. -->
<!-- 구글맵 API (기사님 위치 다 찍어야됨) -->

<!-- 없다면? 그냥 -->
</head>
<body>
	<!-- 내비게이션 임포트 -->
	<%@ include file = "../navigation.jsp" %>  
	
	<div class = "row">
		<div class = "col-md-4">
			<h2 class = "centerBox mainListTitle text-conceptColor">
				현재 배송 중입니다.
			</h2>
		</div>
		<div class = "col-md-8">
			<h2 class = "centerBox mainListTitle text-conceptColor">
				주문 이력
			</h2>
			<div class="limiter">
				<div class="container-table100">
					<div class="wrap-table100">
						<div class="table100 ver3 m-b-110">
					<table data-vertable="ver3">
						<thead>
							<tr class="row100 head">
								<th class="column100 column1" data-column="column1">주문번호</th>
								<th class="column100 column2" data-column="column2">날짜</th>
								<th class="column100 column3" data-column="column3">보낸 물품</th>
								<th class="column100 column4" data-column="column4">목적지</th>
								<th class="column100 column5" data-column="column5">배송 현황</th>
								<th class="column100 column6" data-column="column6">배송기사</th>
							</tr>
						</thead>
						<tbody>
						<!-- 반복문 넣을 예정 -->
							<tr class="row100">
								<td class="column100 column1" data-column="column1">Lawrence Scott</td>
								<td class="column100 column2" data-column="column2">8:00 AM</td>
								<td class="column100 column3" data-column="column3">--</td>
								<td class="column100 column4" data-column="column4">--</td>
								<td class="column100 column5" data-column="column5">8:00 AM</td>
								<td class="column100 column6" data-column="column6">--</td>
							</tr>
						<!-- 여기까지 -->
							
						</tbody>
					</table>
				</div>
					</div>
				</div>
			</div>
			
			<!-- javascript가 여기에 들어가야 작동됨 -->
			
			<!--===============================================================================================-->	
			<script src="../Table_Highlight_Vertical_Horizontal/vendor/jquery/jquery-3.2.1.min.js"></script>
			<!--===============================================================================================-->
			<script src="../Table_Highlight_Vertical_Horizontal/vendor/bootstrap/js/popper.js"></script>
			<script src="../Table_Highlight_Vertical_Horizontal/vendor/bootstrap/js/bootstrap.min.js"></script>
			<!--===============================================================================================-->
			<script src="../Table_Highlight_Vertical_Horizontal/vendor/select2/select2.min.js"></script>
			<!--===============================================================================================-->
			<script src="../Table_Highlight_Vertical_Horizontal/js/main.js"></script>
			
			<!-- 왜인지는 모르겠음 -->
			
		</div>
	</div>
	
	<%@ include file = "../footer.jsp" %>
</body>
</html>