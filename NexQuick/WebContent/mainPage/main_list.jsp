<%@ page import = "com.nexquick.model.vo.OnDelivery" %>
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
<title>NexQuick :: 진행중인 퀵</title>

<script type="text/javascript">
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
	
	$.ajax({
		url : "<%= request.getContextPath() %>/list/userCallList.do",
		dataType : "json",
		method : "POST",
		success : setCallList
	});
	
});

function setCallList(JSONDocument) {
	console.log(JSONDocument);
	$("#tableBody").empty();
	for(var i in JSONDocument) {
		$("#tableBody").append(
			$("<tr class='row100 body'>")
			.append(
				$("<td class='cell100 column1 centerBox'>").text(JSONDocument[i].orderNum)
			).append(
				$("<td class='cell100 column2 centerBox'>").text(JSONDocument[i].callTime)
			).append(
				$("<td class='cell100 column3 centerBox'>").text(JSONDocument[i].receiverAddress)
			).append(
				$("<td class='cell100 column4 centerBox'>").text("물품들")
			).append(
				$("<td class='cell100 column5 centerBox'>").text(JSONDocument[i].deliveryStatus)
			).append(
				$("<td class='cell100 column6 centerBox'>").text("배송기사")
			)
		)
		
	}
	
}

</script>
<!-- 배송중인 배달이 있다면. -->
<!-- 구글맵 API (기사님 위치 다 찍어야됨) -->

<!-- 없다면? 그냥 -->
</head>
<body>
	<!-- 내비게이션 임포트 -->
	<%@ include file = "../navigation.jsp" %>  
	
	<div class = "row">
		<div class = "col-md-4">
			<h2 class = "centerBox mainListTitle text-conceptColor mb-3">
				현재 배송 중입니다.
			
			</h2>
			
			<div id="map" style="width:500px;height:400px;"></div>
			<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=fb36d3fbdea0b0b3d5ac3c3bb1c0532d"></script>
			<script type="text/javascript">
			var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
			var options = { //지도를 생성할 때 필요한 기본 옵션
				center: new daum.maps.LatLng(37.501225,  127.039188), //지도의 중심좌표.
				level: 3 //지도의 레벨(확대, 축소 정도)
			};
			
			var map = new daum.maps.Map(container, options); //지도 생성 및 객체 리턴
			
			</script>
		</div>
		<div class = "col-md-8">
			<h2 class = "centerBox mainListTitle text-conceptColor mb-3">
				주문 이력
			</h2>
			
			<div class="limiter">
				<div class="container-table100" style = "top:0em!important;">
					<div class = "table1000">
						<div class="table100 ver1" style = "max-height: 400px;">
							<div class="table100-head">
						
								<table class = "table1000">
									<thead>
										<tr class="row100 head">
											<th class="column100 column1 centerBox">주문번호</th>
											<th class="column100 column2 centerBox">날짜</th>
											<th class="column100 column3 centerBox">목적지</th>
											<th class="column100 column4 centerBox">물품</th>
											<th class="column100 column5 centerBox">배송 현황</th>
											<th class="column100 column6 centerBox">배송 기사</th>
										</tr>
									</thead>
								</table>
							</div>
							<div class="table100-body js-pscroll">
								<table class = "table1000">
									<tbody id = "tableBody">
									</tbody>
								</table>
							</div>
							<div class = "centerBox text-conceptColor mt-5">
								<h6> 콜 번호를 클릭하면 해당 콜에 대한 상세 정보를 조회할 수 있습니다. </h6>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			
			
			
			
			<!-- javascript가 여기에 들어가야 작동됨 -->
			<script src="<%=request.getContextPath() %>/Table_Fixed_Header/vendor/bootstrap/js/popper.js"></script>
			<script src="<%=request.getContextPath() %>/Table_Fixed_Header/vendor/bootstrap/js/bootstrap.min.js"></script>
			<script src="<%=request.getContextPath() %>/Table_Fixed_Header/vendor/select2/select2.min.js"></script>
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
			<script src="<%=request.getContextPath() %>/Table_Fixed_Header/js/main.js"></script>
			
			
		</div>
	</div>
	
	<%@ include file = "../footer.jsp" %>
</body>
</html>