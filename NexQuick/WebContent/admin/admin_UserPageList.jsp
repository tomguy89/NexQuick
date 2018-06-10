<%@ page import = "java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="<%=request.getContextPath() %>/Table_Highlight_Vertical_Horizontal/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->	
	<link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/Table_Fixed_Header/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/Table_Fixed_Header/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/Table_Fixed_Header/vendor/animate/animate.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/Table_Fixed_Header/vendor/select2/select2.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/Table_Fixed_Header/vendor/perfect-scrollbar/perfect-scrollbar.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/Table_Fixed_Header/css/util.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/Table_Fixed_Header/css/main.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/indexStyle.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/InputBoxStyle.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/datepicker.min.css">
	<script src="<%=request.getContextPath() %>/js/datepicker.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/datepicker.en.js"></script>

	
<title>NexQuick :: 관리자 페이지 :: 사용자 관리</title>
<script type="text/javascript">

function onopen(buttonId)
{
	var url =
	"http://www.ftc.go.kr/bizCommPop.do?wrkr_no="+$(buttonId).text();
	window.open(url, "bizCommPop", "width=750, height=700;");
}

$(function() {
	setInterval(function() {
		$.ajax({
			url : "<%= request.getContextPath() %>/call/delPastCall.do",
			dataType : "json",
			method : "POST",
			success : function(JSONDocument) {
				if(JSONDocument) {
					console.log("하루 지난 미완료 콜 삭제 완료");
				}
			}
		})
	}, 60*60*1000);
	
	
	
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
	
	
	
	$.ajax({
		url : "<%= request.getContextPath() %>/admin/allCsByName.do",
		dataType : "json",
		method : "POST",
		data : {
			csName : $("#csName").val()
		},
		success : setCsListTable 
	})
	
	$("#searchBtn").on("click", function() {
		$.ajax({
			url : "<%= request.getContextPath() %>/admin/allCsByName.do",
			dataType : "json",
			method : "POST",
			data : {
				csName : $("#csName").val()
			},
			success : setCsListTable 
		})
	});
	
	
	
});

function setCsListTable(JSONDocument) {
	console.log(JSONDocument);
	$("#tableBody").empty();
	var count = 0;
	var BusinessNumber;
	var BusinessName;
	var Department;
	var csType;
	for(var i in JSONDocument) {
		switch(JSONDocument[i].csType) {
		case 0:
			csType = "관리자";
			break;
		case 1:
			csType = "법인 고객";
			break;
		case 2:
			csType = "자영업 고객";
			break;
		case 3:
			csType = "개인 고객";
			break;
		}
		$("#tableBody").append(
			$("<tr class = 'row100 body'>")
			.append(
				$("<td class = 'cell100 column1 c1 centerBox'>").text(JSONDocument[i].csId)
			).append(
				$("<td class = 'cell100 column2 c2 centerBox'>").text(JSONDocument[i].csName)
			).append(
				$("<td class = 'cell100 column3 c3 centerBox'>").text(JSONDocument[i].csPhone)
			).append(
				$("<td class = 'cell100 column4 c4 centerBox'>").text(csType)
			).append(
				$("<td class = 'cell100 column5 c5 centerBox'>").text(JSONDocument[i].csGrade)
			).append(
				$("<td class = 'cell100 column6 c6 centerBox'>").text(JSONDocument[i].csMilege)
			).append(
				$("<td class = 'cell100 column7 c7 centerBox'>")
				.append(
					$("<button onclick = 'onopen(this)'>").attr("id", "btn"+count).text(JSONDocument[i].csBusinessNumber)
				)
			).append(
				$("<td class = 'cell100 column8 c8 centerBox'>").text(JSONDocument[i].csBusinessName)
			).append(
				$("<td class = 'cell100 column9 c9 centerBox'>").text(JSONDocument[i].csDepartment)
			)
		)
		
	}
	
}


</script>
</head>
<body>
<%@ include file = "../navigation.jsp" %>

	<h2 class = "centerBox quickFirstTitle mb-5 text-conceptColor">
		사용자 관리
	</h2>
	
	
	<div class = "row">
		<div class = "col-md-6">
			<div class="group centerBox" style = "width: 20%; float : right;">      
			    <input class = "inputDesignForDay" type="text" id = "csName"  data-language='en' placeholder = "이름을 입력하세요">
			    <span class="highlight"></span>
			    <span class="bar"></span>
			    <label class = "labelDesignForDay"><i class = "fas fa-street-view"></i>사용자 이름검색</label>
			</div>
		</div>
		<div class = "col-md-6">
			<button class = "ColorBorder" style = "height: 47px!important ; line-height: 47px!important;" id = "searchBtn">검색하기</button>
		</div>
	</div>
	
	
	<div class="limiter">
		<div class="container-table100" style = "top:0em!important;">
			<div class = "table1000">
				<div class="table100 ver1">
					<div class="table100-head">
				
						<table class = "table1000">
							<thead>
								<tr class="row100 head">
									<th class="column100 column1 c1 centerBox">고객ID</th>
									<th class="column100 column2 c2 centerBox">이름</th>
									<th class="column100 column3 c3 centerBox">전화번호</th>
									<th class="column100 column4 c4 centerBox">고객분류</th>
									<th class="column100 column5 c5 centerBox">회원등급</th>
									<th class="column100 column6 c6 centerBox">마일리지</th>
									<th class="column100 column7 c7 centerBox">사업자등록번호</th>
									<th class="column100 column8 c8 centerBox">법인명</th>
									<th class="column100 column9 c9 centerBox">부서명</th>
								</tr>
							</thead>
						</table>
					</div>
					<div class="table100-body js-pscroll">
						<table class = "table1000">
							<tbody id = "tableBody">
							<%-- 	<tr class="row100 body">
									<td class="cell100 column1 centerBox"><a id = "callNum<%=ci.getCallNum()%>" onclick = "getOrders(this)"><%= ci.getCallNum() %></a></td>
									<td class="cell100 column2 centerBox"><%= ci.getSenderName() %></td>
									<td class="cell100 column3 centerBox"><%= ci.getSenderAddress() %></td>
									<td class="cell100 column4 centerBox"><%= ci.getReservationTime() %></td>
									<td class="cell100 column5 centerBox"><%= ci.getTotalPrice() %></td>
									<td class="cell100 column6 centerBox"><%= ci.getDeliveryStatus() %></td>
								</tr> --%>
							</tbody>
						</table>
					</div>
					<div class = "centerBox text-conceptColor mt-5">
						<h6> 사업자등록번호를 클릭하여 등록된 사업장인지 확인할 수 있습니다. </h6>
					</div>
				</div>
			</div>
		</div>
	</div>
<!-- javascript가 여기에 들어가야 작동됨 -->
<!--===============================================================================================-->
	<script src="<%=request.getContextPath() %>/Table_Fixed_Header/vendor/bootstrap/js/popper.js"></script>
	<script src="<%=request.getContextPath() %>/Table_Fixed_Header/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="<%=request.getContextPath() %>/Table_Fixed_Header/vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
	<script src="<%=request.getContextPath() %>/Table_Fixed_Header/vendor/perfect-scrollbar/perfect-scrollbar.min.js"></script>
<!-- 왜인지는 모르겠음 -->
		<script>
		$('.js-pscroll').each(function(){
			var ps = new PerfectScrollbar(this);

			$(window).on('resize', function(){
				ps.update();
			})
		});

		
	</script>
<!--===============================================================================================-->
	<script src="<%=request.getContextPath() %>/Table_Fixed_Header/js/main.js"></script>


<%@ include file = "../footer.jsp" %>
</body>
</html>