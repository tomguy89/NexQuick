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

	
<title>NexQuick :: 관리자 페이지 :: Quick Pro 관리</title>
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
	
	
	searchAllQP();
	
	$("#searchBtn").on("click", searchAllQP);
	
	
	
});

function setQpListTable(JSONDocument) {
	console.log(JSONDocument);
	$("#tableBody").empty();
	var count = 0;
	var vehicleType;
	for(var i in JSONDocument) {
		switch(JSONDocument[i].qpVehicleType) {
		case 0:
			vehicleType = "오토바이";
			break;
		case 1:
			vehicleType = "다마스";
			break;
		case 2:
			vehicleType = "라보";
			break;
		case 3:
			vehicleType = "트럭";
			break;
		default:
			vehicleType = "기타";
			break;
		}
		
		
		$("#tableBody").append(
			$("<tr class = 'row100 body'>")
			.append(
				$("<td class = 'cell100 column1 c1 centerBox'>").text(JSONDocument[i].qpId)
			).append(
				$("<td class = 'cell100 column2 c2 centerBox'>").text(JSONDocument[i].qpName)
			).append(
				$("<td class = 'cell100 column3 c3 centerBox'>").text(JSONDocument[i].qpPhone)
			).append(
				$("<td class = 'cell100 column4 c4 centerBox'>").text(vehicleType)
			).append(
				$("<td class = 'cell100 column5 c5 centerBox'>").text(JSONDocument[i].qpLicense)
			).append(
				$("<td class = 'cell100 column6 c6 centerBox'>").text(JSONDocument[i].qpProfile)
			).append(
				$("<td class = 'cell100 column7 c7 centerBox'>").text(JSONDocument[i].qpDeposit)
			).append(
				$("<td class = 'cell100 column8 c8 centerBox'>").text("QP 은행")
			).append(
				$("<td class = 'cell100 column9 c9 centerBox'>").text("QP 계좌")
			)
		)
		
	}
	
}

function searchAllQP() {
	$.ajax({
		url : "<%= request.getContextPath() %>/admin/allQpSearch.do",
		dataType : "json",
		method : "POST",
		data : {
			qpName : $("#qpName").val(),
			qpVehicleType : $("#qpVehicleType").val()
		},
		success : setQpListTable 
	})
}

</script>
</head>
<body>
<%@ include file = "../navigation.jsp" %>

	<h2 class = "centerBox quickFirstTitle mb-5 text-conceptColor">
		Quick Pro 관리
	</h2>
	
	
	<div class = "row">
		<div class = "col-md-6">
			<div class="group centerBox" style = "width: 30%; float : right;">      
			    <input class = "inputDesignForDay" type="text" id = "qpName"  data-language='en' placeholder = "이름을 입력하세요">
			    <span class="highlight"></span>
			    <span class="bar"></span>
			    <label class = "labelDesignForDay"><i class = "fas fa-street-view"></i>배송기사 이름검색</label>
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
									<th class="column100 column1 c1 centerBox">QPID</th>
									<th class="column100 column2 c2 centerBox">이름</th>
									<th class="column100 column3 c3 centerBox">전화번호</th>
									<th class="column100 column4 c4 centerBox">
										<select class = "theadCsGrade" id = "qpVehicleType" onchange = "searchAllQP()">
											<option value='-5'>차종</option>
											<option value='0'>오토바이</option>
											<option value='1'>다마스</option>
											<option value='2'>라보</option>
											<option value='3'>트럭</option>
										</select>
									</th>
									<th class="column100 column5 c5 centerBox">면허번호</th>
									<th class="column100 column6 c6 centerBox">등록현황</th>
									<th class="column100 column7 c7 centerBox">예치금</th>
									<th class="column100 column8 c8 centerBox">은행</th>
									<th class="column100 column9 c9 centerBox">계좌번호</th>
								</tr>
							</thead>
						</table>
					</div>
					<div class="table100-body js-pscroll" style = "max-height:500px!important;">
						<table class = "table1000">
							<tbody id = "tableBody">
							</tbody>
						</table>
					</div>
					<div class = "centerBox text-conceptColor mt-5">
						<!-- <h6> 사업자등록번호를 클릭하여 등록된 사업장인지 확인할 수 있습니다. </h6> -->
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