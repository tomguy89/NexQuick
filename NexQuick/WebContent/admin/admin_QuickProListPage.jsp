<%@ page import = "java.util.*" %>
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
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.css">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>
	
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
					$.confirm({
					    title: '로그아웃',
					    content: '로그아웃 되었습니다. 다시 로그인 해주세요.',
					    type: 'red',
					    columnClass: 'centerBox',
					    typeAnimated: true,
					    theme: 'modern',
					    buttons: {
					        '확인' : {
					        	action : function() {
									location.replace("<%= request.getContextPath() %>/index.jsp");
					        	}
					        }
					    }
					});

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
		var tdId = "#qp"+JSONDocument[i].qpId;
		switch(JSONDocument[i].qpVehicleType) {
		case 1:
			vehicleType = "오토바이";
			break;
		case 2:
			vehicleType = "다마스";
			break;
		case 3:
			vehicleType = "라보";
			break;
		case 4:
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
				$("<td class = 'cell100 column5 c5 centerBox' onclick='getModal(this)'>").text("상세보기").attr("id", JSONDocument[i].qpLicense).css("color", "#55B296").css("text-decoration", "underline").css("cursor", "pointer")
			).append(
				$("<td class = 'cell100 column6 c6 centerBox'>")
				.append(
					$("<select name = 'item0' class=theadCsGrade_change onchange = 'saveQPInfo(this)'>").attr("id", "qp"+JSONDocument[i].qpId)
					.append(
						$("<option value='가입 대기'>가입 대기</option>")
					).append(
						$("<option value='가입 승인'>가입 승인</option>")
					).append(
						$("<option value='가입 거부'>가입 거부</option>")
					)		
				)
			).append(
				$("<td class = 'cell100 column7 c7 centerBox'>").text(JSONDocument[i].qpDeposit)
			).append(
				$("<td class = 'cell100 column8 c8 centerBox'>").text("QP 은행")
			).append(
				$("<td class = 'cell100 column9 c9 centerBox'>").text("QP 계좌")
			)
		)
		
		
		if(JSONDocument[i].qpProfile == '가입 대기') {
			$(tdId).val("가입 대기");
		} else if (JSONDocument[i].qpProfile == '가입 승인') { // 법인관리자
			$(tdId).val("가입 승인");
		} else if (JSONDocument[i].qpProfile == '가입 거부') {
			$(tdId).val("가입 거부");
		}
		
		
		
		
	}
	
}


function saveQPInfo(qpProfile) {
	var qpId = qpProfile.id.substring(2);
	$.ajax({
		url : "<%= request.getContextPath() %>/qpAccount/qpUpdateProfileOnly.do",
		dataType : "json",
		method : "POST",
		data : {
			qpId : qpId,
			qpProfile : $(qpProfile).val()
		},
		success : function() {
			$.confirm({
			    title: '퀵프로 정보 수정 완료',
			    content: '퀵프로 정보가 정상적으로 수정되었습니다.',
			    type: 'green',
			    columnClass: 'centerBox',
			    closeIcon: true,
			    typeAnimated: true,
			    theme: 'modern',
			    buttons: {
			        '확인' : {
			            btnClass: 'btn-blue'
			        }
			    }
			});
		}, error : function () {
			$.confirm({
			    title: '퀵프로 정보 수정 실패',
			    content: '퀵프로 정보 수정에 에러가 생겨 수정되지 않았습니다.',
			    type: 'red',
			    columnClass: 'centerBox',
			    closeIcon: true,
			    typeAnimated: true,
			    theme: 'modern',
			    buttons: {
			        '확인' : {
			            btnClass: 'btn-red'
			        }
			    }
			});
		}
	});
	
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

function getModal(Btn) {
	var qpLicenseName = $(Btn).attr("id");
	console.log(qpLicenseName);
	$("#modal_body_qp").empty();
	$("#modal_body_qp").append(
		$("<img>").attr("src", "http://70.12.109.173:9090/NexQuick/uploadPicture/"+qpLicenseName).attr("width", "350").attr("height", "350")
	);
	$("#qpLicense").modal('show');
	
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
			    <label class = "labelDesignForDay"><i class = "xi-motorcycle"></i>배송기사 이름검색</label>
			</div>
		</div>
		<div class = "col-md-6">
			<button class = "ColorBorder" style = "height: 47px!important ; line-height: 47px!important;" id = "searchBtn">
			<i class="fas fa-search"></i>
			검색하기
			</button>
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



			
	<div class="modal fade bd-example-modal-lg" id="qpLicense" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered modal-lg modal_resize" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Quick Pro 면허증</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body centerBox" id = "modal_body_qp">


	      
	      </div>
          <div class="modal-footer centerBox">
	        <button type="button" class="ColorBorder" id = "submitBtn" data-dismiss="modal"> 
	        <i class="far fa-times-circle"></i>
	        	창 닫기 
	        </button>
	      </div>
		      
	    </div>
	  </div>
	</div>



</body>
</html>