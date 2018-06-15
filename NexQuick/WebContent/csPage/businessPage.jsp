<%@ page import = "java.util.*" %>
<%@ page import = "com.nexquick.model.vo.CallInfo" %>
<%@ page import = "com.nexquick.model.vo.OnDelivery" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="<%=request.getContextPath() %>/Table_Fixed_Header/vendor/jquery/jquery-3.2.1.min.js"></script>
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
<script type="text/javascript">
var callPrice;
$(function() {
	
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
	
	
	getBusinessOrders();
	$("#searchBtn").on("click", getBusinessOrders);
	
	
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
	
	
});

function getBusinessOrders() {
	$.ajax({
		url : "<%= request.getContextPath() %>/list/getBusinessOrder.do",
		dataType : "json",
		method : "POST",
		data : {
			csName : $("#csName").val(),
			callTime : $("#callTime").val(),
			csDepartment : $("#csDepartment").val(), 
			payType : $("#payType").val()
		},
		success : setCallList
			
	});
	
	
}


function setCallList(JSONDocument) {
	console.log(JSONDocument);
	$("#tableBody").empty();
	var totalPrice = 0;
	var payType;
	for(var i in JSONDocument) {
		switch(JSONDocument[i].payType) {
		case 0:
			payType = "카드(웹)";
			break;
		case 1:
			payType = "입금(웹)";
			break;
		case 2:
			payType = "카드(현장선불)";
			break;
		case 3:
			payType = "현금(현장선불)";
			break;
		case 4:
			payType = "카드(현장후불)";
			break;
		case 5:
			payType = "현금(현장후불)";
			break;
		case 6:
			payType = "신용결제";
			break;
		}

		$("#tableBody").append(
			$("<tr class='row100 body'>")
			.append(
				$("<td class='cell100 column1 centerBox'>").css("width", "12.5%")
				.append(
					$("<a onclick='getOrders(this)'>").attr("id", "orderNum" + JSONDocument[i].orderNum).css("color", "#55B296").css("text-decoration", "underline").css("cursor","pointer").text(JSONDocument[i].orderNum)
				)
			).append(
				$("<td class='cell100 column2 centerBox'>").css("width", "12.5%").text(JSONDocument[i].csName)
			).append(
				$("<td class='cell100 column3 centerBox'>").css("width", "12.5%").text(JSONDocument[i].csBusinessName)
			).append(
				$("<td class='cell100 column4 centerBox'>").css("width", "12.5%").text(JSONDocument[i].csDepartment)
			).append(
				$("<td class='cell100 column5 centerBox'>").css("width", "12.5%").text(JSONDocument[i].senderName)
			).append(
				$("<td class='cell100 column6 centerBox'>").css("width", "12.5%").text(JSONDocument[i].senderAddress + " " + JSONDocument[i].senderAddressDetail)
			).append(
				$("<td class='cell100 column7 centerBox'>").css("width", "12.5%").text(payType)
			).append(
				$("<td class='cell100 column7 centerBox'>").css("width", "12.5%").text(JSONDocument[i].callTime)
			)
		)
	}
	
	console.log(JSONDocument);
}



function getOrders(orderNum) {
	console.log(orderNum.text);
	$.ajax({
		url : "<%=request.getContextPath()%>/call/getOrder.do",
		data : {
			"orderNum" : orderNum.text
		},
		dataType : "json",
		method : "POST",
		success : orderList
	});
}


function orderList(JSONDocument) {
	console.log(JSONDocument);
	$("#orderListTable").empty();
	var totalPrice = 0;
	var isGet;
    	console.log(JSONDocument.isGet);
		switch(JSONDocument.isGet) {
		case 0:
			isGet = "미수령";
			break;
		case 1:
			isGet = "수령완료";
			break;
		}
    	
    	
    	
    	$("#orderListTable").append(
    		$("<tr class='row100 body'>")
    		.append(
    			$("<td class='cell100 column1 centerBox'>").text(JSONDocument.orderNum)
    		).append(
    			$("<td class='cell100 column2 centerBox'>").text(JSONDocument.receiverName)
    		).append(
    			$("<td class='cell100 column3 centerBox'>").text(JSONDocument.receiverAddress + " " + JSONDocument.receiverAddressDetail)
    		).append(
    			$("<td class='cell100 column4 centerBox'>").text(JSONDocument.memo)
    		).append(
    			$("<td class='cell100 column5 centerBox'>").text(JSONDocument.orderPrice)
    		).append(
    			$("<td class='cell100 column6 centerBox'>").text(isGet)
    		)
    	);
    	
    	$.ajax({
    		
    		
    	});
    	
    	
	$("#orderList").modal("show");
}



</script>

<title>NexQuick :: 법인 관리</title>
</head>
<body>
<%@ include file = "../navigation.jsp" %>
	
	<h2 class = "centerBox quickFirstTitle mb-5 text-conceptColor">
		전체 신청목록 조회
	</h2>
	
	<div class = "row">
		<div class = "col-md-3">
			<div class="group centerBox" style = "width: 50%; float : right;">      
			    <input class = "inputDesignForDay" type="text" id = "csName"  data-language='en' placeholder = "이름 검색">
			    <span class="highlight"></span>
			    <span class="bar"></span>
			    <label class = "labelDesignForDay"><i class = "fas fa-street-view"></i>사용자 검색</label>
			</div>
		</div>
		<div class = "col-md-3">
			<div class="group centerBox" style = "width: 50%; float : right;">      
			    <input class = "inputDesignForDay" type="text" id = "csDepartment"  data-language='en' placeholder = "부서명 검색">
			    <span class="highlight"></span>
			    <span class="bar"></span>
			    <label class = "labelDesignForDay"><i class = "fas fa-street-view"></i>부서명 검색</label>
			</div>
		</div>
		<div class = "col-md-3">
			<div class="group centerBox" style = "width: 50%; float : right;">      
			    <input class = "datepicker-here inputDesignForDay" type="text" id = "callTime"  data-language='en' placeholder = "날짜를 입력하세요">
			    <span class="highlight"></span>
			    <span class="bar"></span>
			    <label class = "labelDesignForDay"><i class = "fas fa-street-view"></i>해당 날짜 이후</label>
			</div>
		</div>
		<div class = "col-md-3">
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
									<th class="column100 column1 centerBox" style = "width: 12.5%;">신청 번호</th>
									<th class="column100 column2 centerBox" style = "width: 12.5%;">신청인</th>
									<th class="column100 column3 centerBox" style = "width: 12.5%;">법인명</th>
									<th class="column100 column4 centerBox" style = "width: 12.5%;">부서명</th>
									<th class="column100 column5 centerBox" style = "width: 12.5%;">발송인</th>
									<th class="column100 column6 centerBox" style = "width: 12.5%;">발송지</th>
									<th class="column100 column6 centerBox" style = "width: 12.5%;">
										<select class = "theadCsGrade" id = "payType" onchange = "getBusinessOrders()">
											<option value='-2'>결제유형</option>
											<option value='0'>카드(웹)</option>
											<option value='1'>입금(웹)</option>
											<option value='2'>카드(현장선불)</option>
											<option value='3'>현금(현장선불)</option>
											<option value='4'>카드(현장후불)</option>
											<option value='5'>현금(현장후불)</option>
											<option value='6'>신용결제</option>
										</select>
									</th>
									<th class="column100 column8 centerBox" style = "width: 12.5%;">신청날짜</th>
								</tr>
							</thead>
						</table>
					</div>
					<div class="table100-body js-pscroll" style = "max-height: 500px!important;"> 
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



			
	<div class="modal fade bd-example-modal-lg" id="orderList" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered modal-lg modal_resize" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">신청 정보</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">



			<div class="limiter">
				<div class="container-table100" style = "top:0em!important;">
					<div class = "table1000">
						<div class="table100 ver1">
							<div class="table100-head">
						
								<table class = "table1000">
									<thead>
										<tr class="row100 head">
											<th class="column100 column1 centerBox">오더 번호</th>
											<th class="column100 column2 centerBox">수령인</th>
											<th class="column100 column3 centerBox">수령지</th>
											<th class="column100 column4 centerBox">배송메모</th>
											<th class="column100 column5 centerBox">가격</th>
											<th class="column100 column6 centerBox">배송상태</th>
										</tr>
									</thead>
								</table>
							</div>
							<div class="table100-body js-pscroll modalTable" >
								<table class = "table1000">
									<tbody id = "orderListTable">
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<!-- 화물리스트 -->
			<div class="limiter">
				<div class="container-table100" style = "top:0em!important;">
					<div class = "table1000">
						<div class="table100 ver1">
							<div class="table100-head">
						
								<table class = "table1000">
									<thead>
										<tr class="row100 head">
											<th class="column100 column1 centerBox">물품 유형</th>
											<th class="column100 column2 centerBox">갯수</th>
											<th class="column100 column3 centerBox">물품 가격</th>
										</tr>
									</thead>
								</table>
							</div>
							<div class="table100-body js-pscroll modalTable" >
								<table class = "table1000">
									<tbody id = "FreightTable">
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
	      
	      
	      </div>
          <div class="modal-footer centerBox">
	        <button type="button" class="ColorBorder" id = "submitBtn" data-dismiss="modal"> 창 닫기 </button>
	      </div>
		      
	    </div>
	  </div>
	</div>

	
	
	



	
</body>
</html>