<%@ page import = "java.util.*" %>
<%@ page import = "com.nexquick.model.vo.CallInfo" %>
<%@ page import = "com.nexquick.model.vo.OnDelivery" %>
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
<script type="text/javascript">
$(function() {
	
	$.ajax({
		url : "<%= request.getContextPath() %>/call/getCallsByNameAndDate.do",
		dataType : "json",
		method : "POST",
		data : {
			senderName : $("#senderName").val(),
			callTime : $("#callTime").val()
		},
		success : setCallList
			
	});
	
	$("#searchBtn").on("click", function() {
		$.ajax({
			url : "<%= request.getContextPath() %>/call/getCallsByNameAndDate.do",
			dataType : "json",
			method : "POST",
			data : {
				senderName : $("#senderName").val(),
				callTime : $("#callTime").val()
			},
			success : setCallList
				
		});
	});
	
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

function setCallList(JSONDocument) {
	
	$("#tableBody").empty();
	var totalPrice = 0;
	var deliveryStatus;
	for(var i in JSONDocument) {
		switch(JSONDocument[i].deliveryStatus) {
		case 0:
			deliveryStatus = "미신청";
			break;
		case 1:
			deliveryStatus = "신청완료";
			break;
		case 2:
			deliveryStatus = "배차완료";
			break;
		case 3:
			deliveryStatus = "배송중";
			break;
		case 4:
			deliveryStatus = "수령완료";
			break;
		}
		$("#tableBody").append(
			$("<tr class='row100 body'>")
			.append(
				$("<td class='cell100 column1 centerBox'>")
				.append(
					$("<a onclick='getOrders(this)'>").attr("id", "callNum" + JSONDocument[i].callNum).text(JSONDocument[i].callNum)
				)
			).append(
				$("<td class='cell100 column2 centerBox'>").text(JSONDocument[i].senderName)
			).append(
				$("<td class='cell100 column3 centerBox'>").text(JSONDocument[i].senderAddress + " " + JSONDocument[i].senderAddressDetail)
			).append(
				$("<td class='cell100 column4 centerBox'>").text(JSONDocument[i].callTime)
			).append(
				$("<td class='cell100 column5 centerBox'>").text(JSONDocument[i].totalPrice + "원")
			).append(
				$("<td class='cell100 column6 centerBox'>").text(deliveryStatus)
			)
		)
		totalPrice += JSONDocument[i].totalPrice;
	}
    $("#tableBody").append(
   		$("<tr class='row100 body'>").css("border-top", "1px solid #34495e")
   		.append(
   			$("<td class='cell100 column1 centerBox' colspan='4'>").text("총계")
   		).append(
   			$("<td class='cell100 column1 centerBox' colspan='2'>").text(totalPrice + "원")
   		)
    )
	
	console.log(JSONDocument);
}



function getOrders(callNum) {
	console.log(callNum.text);
	$.ajax({
		url : "<%=request.getContextPath()%>/call/getOrderList.do",
		data : {
			"callNum" : callNum.text
		},
		dataType : "json",
		method : "POST",
		success : orderList
	});
}


function orderList(JSONDocument) {
	$("#orderListTable").empty();
	var totalPrice = 0;
    for (var i in JSONDocument) {
    	$("#orderListTable").append(
    		$("<tr class='row100 body'>")
    		.append(
    			$("<td class='cell100 column1 centerBox'>").text(JSONDocument[i].orderNum)
    		).append(
    			$("<td class='cell100 column2 centerBox'>").text(JSONDocument[i].receiverName)
    		).append(
    			$("<td class='cell100 column3 centerBox'>").text(JSONDocument[i].receiverAddress + " " + JSONDocument[i].receiverAddressDetail)
    		).append(
    			$("<td class='cell100 column4 centerBox'>").text(JSONDocument[i].memo)
    		).append(
    			$("<td class='cell100 column5 centerBox'>").text(JSONDocument[i].orderPrice + "원")
    		).append(
    			$("<td class='cell100 column6 centerBox'>").text(JSONDocument[i].isGet)
    		)
    	);
    	totalPrice += JSONDocument[i].orderPrice;
    }
    $("#orderListTable").append(
		$("<tr class='row100 body'>").css("border-top", "1px solid #34495e")
		.append(
			$("<td class='cell100 column1 centerBox' colspan='4'>").text("총계")
		).append(
			$("<td class='cell100 column1 centerBox' colspan='2'>").text(totalPrice + "원")
		)
	)
    
	$("#orderList").modal("show");
    
}



</script>

<title>NexQuick :: 전체 신청목록</title>
</head>
<body>
<%@ include file = "../navigation.jsp" %>
	
	<h2 class = "centerBox quickFirstTitle mb-5 text-conceptColor">
		전체 신청목록 조회
	</h2>
	
	<div class = "row justify-content-start">
		<div class = "col-md-6">
			<div class="group" style = "width: 35%; margin-left: auto;">      
			    <input class = "inputDesignForDay" type="text" id = "senderName"  data-language='en' placeholder = "이름을 입력하세요">
			    <span class="highlight"></span>
			    <span class="bar"></span>
			    <label class = "labelDesignForDay"><i class = "fas fa-street-view"></i>신청자 검색</label>
			</div>
		</div>
		<div class = "col-md-3">
			<div class="group" style = "width: 100%;">      
			    <input class = "datepicker-here inputDesignForDay" type="text" id = "callTime"  data-language='en' placeholder = "날짜를 입력하세요">
			    <span class="highlight"></span>
			    <span class="bar"></span>
			    <label class = "labelDesignForDay"><i class = "fas fa-street-view"></i>해당 날짜 이후</label>
			</div>
		</div>
		<div class = "col-md-2">
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
									<th class="column100 column1 centerBox">콜 번호</th>
									<th class="column100 column2 centerBox">발송인</th>
									<th class="column100 column3 centerBox">발송지</th>
									<th class="column100 column4 centerBox">신청날짜</th>
									<th class="column100 column5 centerBox">요금</th>
									<th class="column100 column6 centerBox">배송상황</th>
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
	        <h5 class="modal-title" id="exampleModalLabel">개인정보 수정</h5>
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
					<div class="table100-body js-pscroll modalTable">
						<table class = "table1000">
							<tbody id = "orderListTable">
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