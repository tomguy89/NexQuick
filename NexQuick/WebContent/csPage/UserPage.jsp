<%@ page import = "java.util.*" %>
<%@ page import = "com.nexquick.model.vo.CallInfo" %>
<%@ page import = "com.nexquick.model.vo.OnDelivery" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<% List<CallInfo> list = (List<CallInfo>) request.getSession().getAttribute("callListByCsId"); %>
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

<script type="text/javascript">
$(function() {
	
	
});

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
    for (var i in JSONDocument) {
    	$("#orderListTable").append(
    		$("<tr class='row100 body'>")
    		.append(
    			$("<td class='cell100 column1 centerBox'>").text(JSONDocument[i].orderNum)
    		).append(
    			$("<td class='cell100 column2 centerBox'>").text(JSONDocument[i].receiverName)
    		).append(
    			$("<td class='cell100 column3 centerBox'>").text(JSONDocument[i].receiverAddress)
    		).append(
    			$("<td class='cell100 column4 centerBox'>").text(JSONDocument[i].memo)
    		).append(
    			$("<td class='cell100 column5 centerBox'>").text(JSONDocument[i].orderPrice)
    		).append(
    			$("<td class='cell100 column6 centerBox'>").text(JSONDocument[i].isGet)
    		)
    	);
    }
	$("#orderList").modal("show");
    
}



</script>

<title>NexQuick :: 전체 신청목록</title>
</head>
<body>
<%@ include file = "../navigation.jsp" %>

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
							<tbody>
							<% if(list != null) { 
								int countNumber = 1;
								for(CallInfo ci : list) {
							%>
								<tr class="row100 body">
									<td class="cell100 column1 centerBox"><a id = "callNum<%=ci.getCallNum()%>" onclick = "getOrders(this)"><%= ci.getCallNum() %></a></td>
									<td class="cell100 column2 centerBox"><%= ci.getSenderName() %></td>
									<td class="cell100 column3 centerBox"><%= ci.getSenderAddress() %></td>
									<td class="cell100 column4 centerBox"><%= ci.getReservationTime() %></td>
									<td class="cell100 column5 centerBox"><%= ci.getTotalPrice() %></td>
									<td class="cell100 column6 centerBox"><%= ci.getDeliveryStatus() %></td>
								</tr>
								<% countNumber++; } %>
							<% } %>
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
	  <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
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
					<div class="table100-body js-pscroll">
						<table class = "table1000">
							<tbody id = "orderListTable">
						<%-- 	<% if(list != null) { 
								int countNumber = 1;
								for(CallInfo ci : list) {
							%>
								<tr class="row100 body">
									<td class="cell100 column1 centerBox"><a id = "callNum<%=ci.getCallNum()%>" onclick = "getOrders(this)"><%= ci.getCallNum() %></a></td>
									<td class="cell100 column2 centerBox"><%= ci.getSenderName() %></td>
									<td class="cell100 column3 centerBox"><%= ci.getSenderAddress() %></td>
									<td class="cell100 column4 centerBox"><%= ci.getReservationTime() %></td>
									<td class="cell100 column5 centerBox"><%= ci.getTotalPrice() %></td>
									<td class="cell100 column6 centerBox"><%= ci.getDeliveryStatus() %></td>
								</tr>
								<% countNumber++; } %>
							<% } %> --%>
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

	      
	      
	      
	      </div>
          <div class="modal-footer">
	        <button type="button" class="dangerBorder" data-dismiss="modal"><i class = "xi-close-circle-o"></i> 취소</button>
	        <button type="button" class="ColorBorder" id = "submitBtn" data-dismiss="modal"><i class = "xi-user-plus"></i> 수정하기</button>
	      </div>
		      
	    </div>
	  </div>
	</div>

		
	



	
</body>
</html>