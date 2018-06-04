<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--===============================================================================================-->	
<script src="../Table_Highlight_Vertical_Horizontal/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
<script src="../Table_Highlight_Vertical_Horizontal/vendor/bootstrap/js/popper.js"></script>
<script src="../Table_Highlight_Vertical_Horizontal/vendor/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="../Table_Highlight_Vertical_Horizontal/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../Table_Highlight_Vertical_Horizontal/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/indexStyle.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/indexStyle_radio.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/checkBoxstyle.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/InputBoxStyle.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/hrStyle.css">


<script>
 	$(function () {
    	$('#datetimepicker1').datetimepicker();
    	
    	setTimeout(function(){
	        $('.trans--grow').addClass('grow');
	    }, 275);
    	
    	$("button[title='Toggle Period']").removeClass("btn").removeClass("btn-primary").addClass("ColorBorder").addClass("text-conceptColor").css("line-height", "0!important");
    	$("#reserveDelivery").on("click", function() {
    		 $("#reserveBox").slideToggle();
    		 if($("#reserveDelivery").is(":checked")) {
    			 $("#timeInput").attr("required", "required");
    		 } else {
    			 $("#timeInput").removeAttr("required");
    		 }
    	});
    	
    	$("#saveTime").on("click", function() {
    		var day = $(".table-condensed > tbody > tr > td.active").attr("data-day");
    		var hour = $(".timepicker-hour").text();
    		var time = $(".timepicker-minute").text();
    		$("#timeInput").val(day+" "+hour+":"+time);
    		
    	});
    	
    	
    	
    	/* 달력에 시간, 분 현재시간으로 설정 */
    	var d = new Date();
    	var currHour = d.getHours();
    	var currMin = d.getMinutes();
    	if(currHour >= 13) {
    		currHour = currHour - 12;
    	}
    	$(".timepicker-hour").text(currHour);
    	$(".timepicker-minute").text(currMin);
    	if(currHour >= 12) {
    		$("#datetimepicker12 > div > div > div.timepicker.col-md-6 > div.timepicker-picker > table > tbody > tr:nth-child(2) > td:nth-child(4) > button").text("PM");
    	} else {
    		$("#datetimepicker12 > div > div > div.timepicker.col-md-6 > div.timepicker-picker > table > tbody > tr:nth-child(2) > td:nth-child(4) > button").text("AM");
    	}
	
    	/* 배송수단 : 그림 눌러도 라디오버튼 활성화 */
    	$("#motorImg").on("click", function() {
    		$("#motorcycle").trigger("click");
    	});
    	$("#damasImg").on("click", function() {
    		$("#damas").trigger("click");
    	});
    	$("#laboImg").on("click", function() {
    		$("#labo").trigger("click");
    	});
    	$("#truckImg").on("click", function() {
    		$("#truck").trigger("click");
    	});
    	
    	/* 타임피커 가운데로 */
    	$("body > div.row.mt-5 > div:nth-child(2) > div > div > div").css("justify-content", "center");
    	
    	/* 단계알림 이미지박스 페이지별로 설정  (1단계 : 첫번째 그림 말고 모두 회색으로) */
    	$("#right_Img_1").attr("src", "<%= request.getContextPath() %>/image/quickApply_img/right_grey.png");
    	$("#right_Img_2").attr("src", "<%= request.getContextPath() %>/image/quickApply_img/right_grey.png");
    	$("#applyDetailImg").attr("src", "<%= request.getContextPath() %>/image/quickApply_img/applyDetail_grey.png");
    	$("#completeImg").attr("src", "<%= request.getContextPath() %>/image/quickApply_img/complete_grey.png");
    	$("#applyDetailImgText").css("color", "#d1d1d1");
    	$("#completeImgText").css("color", "#d1d1d1");
    	
    	
  	 	$(".add").mouseenter(function() {
    		$(".add").focus();
    	});
  	 	
  	 	$(".addD").mouseenter(function() {
    		$(".addD").focus();
    	});
  	 	$(".name").mouseenter(function() {
    		$(".name").focus();
    	});
  	 	$(".phone").mouseenter(function() {
    		$(".phone").focus();
    	});
  	 	
  	 	$(".time").mouseenter(function() {
    		$(".time").focus();
    	});
  	 	  	 	
  	 	$("#address").on("click", function() {
  	 		console.log("주소칸");
  	 	});
  	 	
  	 	

    	
    	
    	/* 다음 페이지으로 이동 */
    	$("#goNextBtn").on("click", function() {
    		/* 긴급배송 여부를 숫자값으로 표현하기 위해. */
    		var urgent_value;
    		var group_value;
    		var reserve_value;
    		if($('input:checkbox[id=urgentBox]').is(":checked")) {
    			urgent_value = 1;
    		} else {
    			urgent_value = 0;
    		}
    		if($('input:checkbox[id=groupDelivery]').is(":checked")) {
    			group_value = 1;
    		} else {
    			group_value = 0;
    		}
    		if($('input:checkbox[id=reserveDelivery]').is(":checked")) {
    			reserve_value = 1;
    		} else {
    			reserve_value = 0;
    		}
    		
    		
    		/* 선택한 날짜, 시간 값 가져오기 */
    		console.log($(".table-condensed > tbody > tr > td.active").attr("data-day"));
    		console.log($(".timepicker-hour").text());
    		console.log($(".timepicker-minute").text());
    		
    		 $.ajax({ 
    			url : "<%= request.getContextPath() %>/call/newCall.do",
				data : {
					senderName : $("#userNameApply").val(),
					senderAddress : $("#address").val() + " " + $("#addressDetail").val(),
					senderPhone : $("#phone").val(),
					vehicleType : $("input:radio[name=radio-group]").val(),
					urgent : urgent_value,
					series : group_value,
					reserved : reserve_value, /* 예약배송 여부는 사용 안할것같음 */
					reservationTime : $("#timeInput")+":00" /* 날짜데이터 어떻게 넣을지 확인해야 함 */
				},
				dataType : "json",
				method : "POST",
				success : gotoNextPage,
				error : function() {
					alert("퀵 신청에 오류가 발생했습니다. 다시 작성해주세요.");
				}
    		}) 
    	});
    	
    	
 	});
 	
 
 	
 	
 	function gotoNextPage(JSONDocument) {
 		console.log("비동기통신 완료... 다음 페이지로 이동");
 		location.href = "./quickApply_second.jsp";
 		/* 객체를 비동기로 바로 생성하고 시작하면 location.href로 아무것도 가져가지 말고 페이지 이동하면 됨
 			아니라면 데이터 모두 갖고 이동 */
 	}
 	
 	
   	function goPopup(buttonId){
   		// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
   	    var pop = window.open("<%=request.getContextPath()%>/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes");
   		// 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
   	    //var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes"); 
   	}
   	/** API 서비스 제공항목 확대 (2017.02) **/
   	function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn
   							, detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo){
   		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
   		$("#address").val(roadAddrPart1);
   		$("#addressDetail").val(addrDetail);
   		/* $(addressDetailId).val(roadAddrPart2); */
   		/* document.form.roadAddrPart1.value = roadAddrPart1;
   		document.form.roadAddrPart2.value = roadAddrPart2;
   		document.form.addrDetail.value = addrDetail;
   		document.form.zipNo.value = zipNo; */
   	} 
    	 	
 	
 	
 	
</script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/momentjs/2.14.1/moment.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">



<title>NexQuick :: 퀵 신청하기 - 기본정보 설정</title>
</head>
<body>
<%@ include file = "../navigation.jsp" %>
<%@ include file = "../quickApplyPage/quickApply_going_box.jsp" %>


<div class = "row">
	<div class = "col-md-6 text-conceptColor">
		<h2 class = "centerBox quickFirstTitle">
			출발지
		</h2>
		<div class = "centerBox">
			<img class = "mr-5" src = "<%= request.getContextPath() %>/image/quickApply_img/map.png" width = "40" height = "40" />

		    <label class="field field_animated field_a2 page__field add">
		      <input type="text" class="addressForm field__input" placeholder="클릭해서 주소를 입력하세요" name = "address" id="address" onclick="goPopup(this);" readonly>
		      <span class="field__label-wrap">
		        <span class="field__label text-conceptColor">주소</span>
		      </span>
		    </label>   

			
		</div>
		<div class = "centerBox">
			<img class = "mr-5" src = "<%= request.getContextPath() %>/image/quickApply_img/addressDetail.png" width = "40" height = "40" />
			
			<label class="field field_animated field_a2 page__field addD">
		      <input type="text" class="addressForm field__input" placeholder="ex) 멀티캠퍼스 9층 904호" name = "addressDetail" id="addressDetail">
		      <span class="field__label-wrap">
		        <span class="field__label text-conceptColor">상세주소</span>
		      </span>
		    </label>   
			
		</div>
		<!-- 이름, 연락처 -->
		<div class = "centerBox">
			<img class = "mr-5" src = "<%= request.getContextPath() %>/image/quickApply_img/name.png" width = "40" height = "40" />
			
			<label class="field field_animated field_a2 page__field name">
		      <input type="text" class="addressFormSub field__input" placeholder="ex) 김민규" name = "userNameApply" id="userNameApply">
		      <span class="field__label-wrap">
		        <span class="field__label text-conceptColor">발송인</span>
		      </span>
		    </label>   
			
			
			<img class = "mr-5" src = "<%= request.getContextPath() %>/image/quickApply_img/phone.png" width = "40" height = "40" style = "margin-left : 1.5em;"/>
			
			<label class="field field_animated field_a2 page__field phone">
		      <input type="text" class="addressFormSub field__input" placeholder="ex) 01049408292" name = "phone" id="phone">
		      <span class="field__label-wrap">
		        <span class="field__label text-conceptColor">발송인 연락처</span>
		      </span>
		    </label>   
		</div>
		
		    
 
		
		
		
		
	</div>
	
	
	
	
	<div class = "col-md-6">
		<h2 class = "centerBox quickFirstTitle mb-5 text-conceptColor">
			배송 방법
		</h2>
		
		<div class = "urgentBox">
			<label class="label font_1em paddingZero">
			    <input  class="label__checkbox" type="checkbox" id = "urgentBox" />
			    <span class="label__text ">
			      <span class="label__check">
			        <i class="fa fa-check icon font_1em"></i>
			      </span>
			    </span>
		  	</label>
		  	<label for="urgentBox" style = "line-height: 28px; color: #34495e">긴급배송</label>
		  	
		  	<label class="label font_1em paddingZero">
			    <input  class="label__checkbox" type="checkbox" id = "groupDelivery" />
			    <span class="label__text">
			      <span class="label__check ml-5">
			        <i class="fa fa-check icon font_1em"></i>
			      </span>
			    </span>
		  	</label>
		  	<label for="groupDelivery" style = "line-height: 28px; color: #34495e">일괄배송</label>
		  	
		  	<label class="label font_1em paddingZero">
			    <input  class="label__checkbox" type="checkbox" id = "reserveDelivery" />
			    <span class="label__text">
			      <span class="label__check ml-5">
			        <i class="fa fa-check icon font_1em"></i>
			      </span>
			    </span>
		  	</label>
		  	<label for="reserveDelivery" style = "line-height: 28px; color: #34495e">예약배송</label>
					  	
		</div>			  	
		  	
			<div id = "reserveBox" class = "centerBox" style = "display: none;">
				<span>예약시간 : </span>
				<label class="field field_animated field_a2 page__field time">
			      <input type="text" class="addressFormSub field__input" placeholder="클릭해서 시간 설정" name = "time" id="timeInput" readonly = "readonly" data-toggle="modal" data-target="#modal1" style = "width: 12.5em!important;">
			      <span class="field__label-wrap">
			        <span class="field__label text-conceptColor">시간 설정</span>
			      </span>
			    </label>
			</div>
		  	
		  	
		
		<div class = "row mt-5 mb-3">
			<div class = "col-md-3 centerBox">
				<img src = "<%= request.getContextPath() %>/image/quickApply_img/motorcycle.png" width = "64" height = "64" id = "motorImg"/>			
			</div>
			<div class = "col-md-3 centerBox">
				<img src = "<%= request.getContextPath() %>/image/quickApply_img/damas.png" width = "64" height = "64" id = "damasImg"/>
			</div>
			<div class = "col-md-3 centerBox">
				<img src = "<%= request.getContextPath() %>/image/quickApply_img/labo.png" width = "64" height = "64" id = "laboImg"/>
			</div>
			<div class = "col-md-3 centerBox">
				<img src = "<%= request.getContextPath() %>/image/quickApply_img/truck.png" width = "64" height = "64" id = "truckImg"/>
			</div>
		</div>
		<!-- radio버튼  -->
      	<div class = "row mt-4 mb-3">
      		<div class = "col-md-3 centerBox">
      		  	<input type="radio" id="motorcycle" name="radio-group" value="1" checked>
			    <label for="motorcycle">오토바이</label>
      		</div>
      		<div class = "col-md-3 centerBox">
      		 	<input type="radio" id="damas" name="radio-group" value="2">
			    <label for="damas">다마스</label>
      		</div>
      		<div class = "col-md-3 centerBox">
      		  	<input type="radio" id="labo" name="radio-group" value="3">
			    <label for="labo">라보</label>
      		</div>
      		<div class = "col-md-3 centerBox">
      		  	<input type="radio" id="truck" name="radio-group" value="4">
			    <label for="truck">트럭</label>
      		</div>
      	</div>
		
	</div>
	
</div>


	
<div class = "row mt-5 centerBox justify_center">	
	<div class = "col-md-2">
		<button class = "dangerBorder mt-4" id = "cancelBtn">주문취소</button>
	</div>
	<div class = "col-md-2">
		<button class = "ColorBorder mt-4" id = "goNextBtn">다음으로</button>
	</div>
</div>




<%@ include file = "../footer.jsp" %>


<!-- 시간 들어갈 modal 팝업창 -->

<!-- Modal -->
<div class="modal fade bd-example-modal-lg" id = "modal1" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h2 class="modal-title centerBox" id="exampleModalCenterTitle">예약배송 시간설정</h2>
      </div>
      <div class="modal-body">
		
		
		<!-- 시간 들어가는 부분 -->
		<div style="overflow:hidden;">
		    <div class="form-group text-conceptColor">
		        <div class="row">
		            <div class="col-md-12">
		                <div id="datetimepicker12"></div>
		            </div>
		        </div>
		    </div>
		    <script type="text/javascript">
		        $(function () {
		            $('#datetimepicker12').datetimepicker({
		                inline: true,
		                sideBySide: true
		            });
		        });
		    </script>
		</div>       
       
       
       
       
      </div>
      <div class="modal-footer">
        <button type="button" class="dangerBorder" data-dismiss="modal">취소</button>
        <button type="button" class="ColorBorder" id = "saveTime" data-dismiss="modal">확인</button>
      </div>
    </div>
  </div>
</div>





</body>
</html>