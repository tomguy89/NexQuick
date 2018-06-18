<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=request.getContextPath() %>/Table_Fixed_Header/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->	
<!--===============================================================================================-->	
<link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
<!--===============================================================================================-->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
<!-- bootstrap js -->
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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/boxStyle.css">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
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
	
	
	
	$("#goToApply").bind({
		mouseover : function() {
			var list = $("#list_first");
			if(!list.is(":animated")) list.slideDown(400);
		}
	})
	
	$("#goToApply").bind({
		mouseleave : function() {
			var list = $("#list_first");
			if(!list.is(":animated")) list.slideUp(200);
		}
	})
	
	$("#goToList").bind({
		mouseover : function() {
			var list = $("#list_second");
			if(!list.is(":animated")) list.slideDown(400);
		}
	})	
	
	$("#goToList").bind({
		mouseleave : function() {
			var list = $("#list_second");
			if(!list.is(":animated")) list.slideUp(200);
		}
	})	
	
	$("#goToSetting").bind({
		mouseover : function() {
			var list = $("#list_third");
			if(!list.is(":animated")) list.slideDown(400);
		}
	})
	$("#goToSetting").bind({
		mouseleave : function() {
			var list = $("#list_third");
			if(!list.is(":animated")) list.slideUp(200);
		}
	})
	
	$("#goToApply").on("click", function() {
		location.href="<%= request.getContextPath() %>/quickApplyPage/quickApply_first.jsp";
	});
	
	$("#goToList").on("click", function() {
		location.href="<%= request.getContextPath() %>/mainPage/main_list.jsp";
	});
	
	$("#goToSetting").on("click", function() {
		location.href="<%= request.getContextPath() %>/csPage/UserPage.jsp";
	});

	
});
</script>



<title>NexQuick :: 메인 페이지</title>
</head>
<body>

		


<%@ include file = "../navigation.jsp" %>




<div class = "centerBox mb-5">
	<h6>이동하려면 영역의 아무 곳이나 클릭하세요.</h6>
</div>
<section class = "mt-5 section">
  <div class="container-fluid">
    <div class="container">
      <div class="row">
        <div class="col-sm-4">
        
          <div class="card text-center" id = "goToApply">
            <div class="title">
              <i class="fas fa-box fa" aria-hidden="true"></i>
              <h2 class = "mt-5">퀵 신청하기</h2>
            </div>
            <div class = "emptyBox mt-3">
            	
            </div>
            <div class="option">
              <ul id = "list_first" style = "display : none;">
              <li> <i class="fa fa-check" aria-hidden="true"></i> 일괄배송 </li>
              <li> <i class="fa fa-check" aria-hidden="true"></i> 예약배송 </li>
              <li> <i class="fa fa-check" aria-hidden="true"></i> 긴급배송 </li>
              <li> <i class="fa fa-check" aria-hidden="true"></i> 신용결제 </li>
              </ul>
            </div>
            <a><i class="fas fa-caret-right"></i> 이동하기</a>
          </div>
          
        </div>
        <!-- END Col one -->
        <div class="col-sm-4">
          <div class="card text-center" id = "goToList">
            <div class="title">
              <i class="fa fas fa-motorcycle" aria-hidden="true"></i>
              <h2 class = "mt-5">진행중인 퀵</h2>
            </div>
            <div class = "emptyBox mt-3">
            </div>
            <div class="option">
              <ul id = "list_second" style = "display : none;">
              <li> <i class="fa fa-check" aria-hidden="true"></i> 진행중인 신청내역 조회 </li>
              <li> <i class="fa fa-check" aria-hidden="true"></i> 배송기사 정보 조회 </li>
              <li> <i class="fa fa-check" aria-hidden="true"></i> 배송기사 위치 조회 </li>
              <li> <i class="fa fa-check" aria-hidden="true"></i> 배송 서비스 평가 </li>
              </ul>
            </div>
            <a><i class="fas fa-caret-right"></i> 이동하기</a>
          </div>
        </div>
        <!-- END Col two -->
        <div class="col-sm-4">
          <div class="card text-center" id = "goToSetting">
            <div class="title">
              <i class="fa fas fa-list-ul" aria-hidden="true"></i>
              <h2 class = "mt-5">신청내역 조회</h2>
            </div>
            <div class = "emptyBox mt-3">
            </div>
            <div class="option">
              <ul id = "list_third" style = "display : none;">
              <li> <i class="fa fa-check" aria-hidden="true"></i> 결제금액 조회 </li>
              <li> <i class="fa fa-check" aria-hidden="true"></i> 전체 신청내역 조회 </li>
              <li> <i class="fa fa-check" aria-hidden="true"></i> 날짜별 신청내역 조회 </li>
              <li> <i class="fa fa-check" aria-hidden="true"></i> 신청 상세보기 </li>
              </ul>
            </div>
            <a><i class="fas fa-caret-right"></i> 이동하기</a>
          </div>
        </div>
        <!-- END Col three -->
      </div>
    </div>
  </div>
</section>





<%@ include file = "../footer.jsp" %>


</body>
</html>