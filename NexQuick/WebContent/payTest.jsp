<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
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
			$("<tr style = 'background: transparent!important;'>")
			.append($("<td style = 'width: 50%;'>").text(""))
			.append(
				$("<td class = 'tdClassUser'>")
				.append(
					$("<div class = 'spanClassUser'>").text($("#chatbot").val())
				)
				
			)
		);
		$("#tableBodyDiv").scrollTop($("#tableBodyDiv")[0].scrollHeight);

		
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
		$("<tr>")
		.append(
				$("<td class = 'tdClassBot'>")
				.append(
					$("<div class = 'spanClassBot'>").text(JSONDocument.responseSet.result.result[0].message)
				)
		)
		.append(
			$("<td style = 'width: 50%;'>")
		)
	);
	$("#tableBodyDiv").scrollTop($("#tableBodyDiv")[0].scrollHeight);
	
	console.log(JSONDocument.responseSet.result.result[0].message);
}


</script>
</head>
<body style = "background-color: transparent;">
	
	<div class="limiter" style = "height: 430px!important;">
		<div class="container-table100" style = "top:0em!important;">
			<div class = "table1000">
				<div class="table100 ver1" style = "width:100%!important;">
					<div class="table100-head">
				
						<table class = "table1000">
							<thead>
								<tr class="row100 head">
									<th class="column100 column1 centerBox" style = "padding-top: 12px; padding-bottom: 12px;" colspan="2">
										<img src = "<%= request.getContextPath() %>/image/index_img/NexQuickLogo_Nav.png" height = "40" width = "250"/>
									</th>
								</tr>
							</thead>
						</table>
					</div>
					<div id = "tableBodyDiv" class="table100-body js-pscroll"  style = "max-height: 370px!important; height: 370px!important; background-color: rgba(0,0,0,0.2)!important;" >
						<table class = "table1000">
							<tbody id = "tableBody">
							
							
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class = "centerBox text-conceptColor row">
		<div style = "padding: 0px!important; width: 80%;">
			<textarea placeholder = "챗봇과 대화해보세요." id = "chatbot" type = "text" style = "border : none; width: 100%; resize: none; font-size: 14px; height: 32px;"></textarea>
		</div>
		<div style = "padding: 0px!important; width: 20%;">
			<button id = "sendMessage" type = "button" class = "ColorBorder" style = "width: 100%;height: 32px; line-height:32px;">전송</button>
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



</body>
</html>