<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
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

	
<title>NexQuick :: 관리자 페이지 :: 사용자 관리</title>
<script type="text/javascript" src = "./js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
var parameters = '';
var param_id = '';
var chatflow_id = '';
var ins_id = '';
var intent_id = '';
var node_id = '';
var param_id = '';
var session_id = ''; 

$(function() {

	
	
	
	
	$("#chatbot").keyup(function(e) {
		if(e.keyCode == 13) {
			$("#sendMessage").trigger("click");
		}
	});
	
	
	var objData_welcome = new Object();
	objData_welcome.chatbot_id = 'c2af4ed0-eeac-48b3-9a32-c76844927795';
	

	
	var jsonData_welcome = JSON.stringify(objData_welcome);

	$.ajax({
		"url" : "https://danbee.ai/chatflow/welcome.do",
		"dataType" : "json",
		"headers" : { "Content-Type" : "application/json;charset=UTF-8"},
		"method" : "POST",
		"data" : jsonData_welcome,
		"success" : setChatting,
		"error" : function(JSONDocument) {
			console.log("에러");
			console.log(JSONDocument);
		}
		
	});
	
	
	
	
	$("#sendMessage").on("click", function() {
	
		var objData = new Object();
		objData.chatbot_id = 'c2af4ed0-eeac-48b3-9a32-c76844927795';
		objData.input_sentence = $("#chatbot").val();
		objData.param_id = param_id;
		objData.chatflow_id = chatflow_id;
		objData.ins_id = ins_id;
		objData.intent_id = intent_id;
		objData.node_id = node_id;
		objData.param_id = param_id;
		objData.session_id = session_id;
		$("#tableBody").append(
			$("<tr style = 'background-color: rgba(0,0,0,0.2)!important;'>")
			.append($("<td style = 'width: 50%;'>").text(""))
			.append(
				$("<td style='text-align: right; font-size: 16px;'>")
				.append(
					$("<span style = 'background-color: #DAA520; color: #003358; margin-right: 35px; padding: 10px 20px; border-radius: 20px;'>").text($("#chatbot").val())
				)
				
			)
		);
		
		
		$("#chatbot").val("");
		$("#chatbot").focus();
		
		var jsonData = JSON.stringify(objData);
		
		$.ajax({
			"url" : "https://danbee.ai/chatflow/engine.do",
			"dataType" : "json",
			"headers" : { "Content-Type" : "application/json;charset=UTF-8"},
			"method" : "POST",
			"data" : jsonData,
			"success" : setChatting,
			"error" : function(JSONDocument) {
				console.log("에러");
				console.log(JSONDocument);
			}
			
		});
	});
});



function setChatting(JSONDocument) {
	parameters = JSONDocument.responseSet.result.parameters;
	param_id = JSONDocument.responseSet.result.param_id;
	chatflow_id = JSONDocument.responseSet.result.chatflow_id;
	ins_id = JSONDocument.responseSet.result.ins_id;
	intent_id = JSONDocument.responseSet.result.intent_id;
	node_id = JSONDocument.responseSet.result.node_id;
	param_id = JSONDocument.responseSet.result.param_id;
	session_id = JSONDocument.responseSet.result.session_id;
	
	
	
	console.log(JSONDocument);
	$("#tableBody").append(
		$("<tr style = 'background-color: rgba(0,0,0,0.2)!important;'>")
		.append(
				$("<td style = 'width: 50%; text-align: left; font-size: 16px;'>")
				.append(
					$("<span style = 'background-color: white; color: #003358; margin-left: 35px; padding: 10px 20px; border-radius: 20px;'>").text(JSONDocument.responseSet.result.result[0].message)
				)
		)
		.append(
			$("<td style = 'width: 50%;'>")
		)
	);
	
	
	console.log(JSONDocument.responseSet.result.result[0].message);
}


</script>
</head>
<body>

	<h2 class = "centerBox quickFirstTitle mb-5 text-conceptColor">
		사용자 관리
	</h2>
	
	
<!-- 	<div class = "row">
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
	</div> -->
	
	
	<div class="limiter">
		<div class="container-table100" style = "top:0em!important;">
			<div class = "table1000">
				<div class="table100 ver1">
					<div class="table100-head">
				
						<table class = "table1000">
							<thead>
								<tr class="row100 head">
									<th class="column100 column1 centerBox" style = "width: 50%">NexQuick 챗봇</th>
									<th class="column100 column2 centerBox" style = "width: 50%">사용자</th>
								</tr>
							</thead>
						</table>
					</div>
					<div class="table100-body js-pscroll">
						<table class = "table1000">
							<tbody id = "tableBody" >
							
							
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class = "centerBox text-conceptColor mt-5">
		<input id = "chatbot" type = "text" style = "border : 1px solid black!important;"/>
		<button id = "sendMessage" type = "button" class = "ColorBorder">전송</button>
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



</body>
</html>