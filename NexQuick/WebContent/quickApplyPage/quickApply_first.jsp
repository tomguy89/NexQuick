<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--===============================================================================================-->	
<script src="../Table_Fixed_Header/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
<script src="../Table_Fixed_Header/vendor/bootstrap/js/popper.js"></script>
<script src="../Table_Fixed_Header/vendor/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="../Table_Fixed_Header/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../Table_Fixed_Header/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/indexStyle.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/indexStyle_radio.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/checkBoxstyle.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/InputBoxStyle.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/hrStyle.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/momentjs/2.14.1/moment.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/datepicker.min.css">
<script src="<%=request.getContextPath() %>/js/datepicker.min.js"></script>
<script src="<%=request.getContextPath() %>/js/datepicker.en.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<%@ include file = "../navigation.jsp" %>

<script>

var currentCall = 0;
var callNum;
 	$(function () {
 		
 		
 		<% if(csInfo.getCsGrade() == 0) { %>
 			$.confirm({
			    title: '가입 대기중 고객',
			    content: '현재 가입 대기중입니다. 관리자에게 문의해주세요.',
			    type: 'red',
			  	columnClass: 'centerBox',
			    theme: 'modern',
			    typeAnimated: true,
			    buttons: {
			        '확인': {
			            action: function(){
	 						location.replace("<%= request.getContextPath() %>/mainPage/main_index.jsp");
			            }
			        }
			    }
			});
 		
 		<% } %>
 		
 		$.ajax({
 			url : "<%= request.getContextPath() %>/call/currentCall.do",
			data : {
				csId : "<%= csInfo.getCsId() %>"
			},
			dataType : "json",
			method : "POST",
			success : getCurrentCall,
			error : function() {
				console.log("진행중이었던 신청이 없습니다.");
			}
 		});
 		
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
 						    title: '로그인 세션 만료',
 						    content: '로그아웃 되었습니다. 다시 로그인 해주세요.',
 						    type: 'red',
 							closeIcon: true,
 						  	columnClass: 'centerBox',
 						    theme: 'modern',
 						    typeAnimated: true,
 						    buttons: {
 						        '확인': {
 						            action: function(){
 				 						location.replace("<%= request.getContextPath() %>/index.jsp");
 						            }
 						        }
 						    }
 						});

 					}
 				}
 			})
 		}, 5000);
 		
 		$(".jconfirm-box-container").css("margin-left", "auto").css("margin-right", "auto");
 		
 		$("#urgentText").attr("data-container", "body");
		$("#urgentText").attr("data-placement", "left");
		$("#urgentText").attr("data-content", "긴급배송은 다른 경유지를 거치지 않고 직배송하는 대신, 요금이 1.2배가 됩니다.");
		$("#urgentText").popover({
			trigger : 'hover',
			delay: { 
		       hide: "1000"
		    }
		});
 		
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
    	
    	$('#timeInput').datepicker({
    	    dateFormat: 'mm/dd/yyyy',
    	    timeFormat: 'hh:ii'
    	})
    	
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
    	

    	
    	
    	/* 다음 페이지으로 이동 */
    	$("#goNextBtn").on("click", function() {
    		/* 긴급배송 여부를 숫자값으로 표현하기 위해. */
    		if($("#address").val() == "" || $("#address").val().length == 0) {
				$.confirm({
				    title: '주소설정 오류',
				    content: '주소를 입력해주세요.',
				    type: 'red',
				    closeIcon: true,
				    typeAnimated: true,
				    columnClass: 'centerBox',
				    theme: 'modern',
				    buttons: {
				        '확인': {
				            
				        }
				    }
				});
				return;
    		}
    		if($("#addressDetail").val() == "" || $("#addressDetail").val().length == 0) {
				$.confirm({
				    title: '상세주소설정 오류',
				    content: '상세주소를 입력해주세요.',
				    type: 'red',
				    closeIcon: true,
				    typeAnimated: true,
				    columnClass: 'centerBox',
				    theme: 'modern',
				    buttons: {
				        '확인': {
				            
				        }
				    }
				});
				return;
    		}
    		if($("#userNameApply").val() == "" || $("#userNameApply").val().length == 0) {
				$.confirm({
				    title: '발송인설정 오류',
				    content: '발송인 이름을 입력해주세요.',
				    type: 'red',
				    closeIcon: true,
				    typeAnimated: true,
				    columnClass: 'centerBox',
				    theme: 'modern',
				    buttons: {
				        '확인': {
				            
				        }
				    }
				});
				return;
    		}
    		if($("#phone").val() == "" || $("#phone").val().length == 0) {
				$.confirm({
				    title: '발송인 연락처 설정 오류',
				    content: '발송인 연락처를 입력해주세요.',
				    type: 'red',
				    closeIcon: true,
				    typeAnimated: true,
				    columnClass: 'centerBox',
				    theme: 'modern',
				    buttons: {
				        '확인': {
				            
				        }
				    }
				});
				return;
    		}
    		
    		var urgent_value;
    		var group_value;
    		var reserve_value;
    		if($('input:checkbox[id=urgentBox]').is(":checked")) {
    			urgent_value = 1;
    		} else {
    			urgent_value = 0;
    		}
    		if($('input:checkbox[id= groupDelivery]').is(":checked")) {
    			group_value = 1;
    		} else {
    			group_value = 0;
    		}
    		if($('input:checkbox[id=reserveDelivery]').is(":checked")) {
    			reserve_value = 1;
    			if($("#timeInput").val() == "" || $("#timeInput").val().length == 0) {
					$.confirm({
					    title: '시간설정 오류',
					    content: '시간을 설정해주세요.',
					    type: 'red',
					    closeIcon: true,
					    typeAnimated: true,
					    columnClass: 'centerBox',
					    theme: 'modern',
					    buttons: {
					        '확인': {
					            
					        }
					    }
					});
    				return;
    			}
    		} else {
    			reserve_value = 0;
    		}
    		
			console.log(callNum);
    		
    		if(currentCall == 0) {
	    		 $.ajax({ 
	    			url : "<%= request.getContextPath() %>/call/newCall.do",
					data : {
						senderName : $("#userNameApply").val(),
						senderAddress : $("#address").val(),
						senderAddressDetail : $("#addressDetail").val(),
						senderPhone : $("#phone").val(),
						vehicleType : $("input:radio[name=radio-group]:checked").val(),
						urgent : urgent_value,
						series : group_value,
						reserved : reserve_value, /* 예약배송 여부는 사용 안할것같음 */
						reservationTime : $("#timeInput").val() /* 날짜데이터 어떻게 넣을지 확인해야 함 */
					},
					dataType : "json",
					method : "POST",
					success : gotoNextPage,
					error : function() {
						$.confirm({
						    title: '퀵 신청 오류',
						    columnClass: 'centerBox',
						    content: '퀵 신청에 오류가 발생했습니다. 다시 작성해주세요.',
						    type: 'red',
						    closeIcon: true,
						    typeAnimated: true,
						    theme: 'modern',
						    buttons: {
						        '확인': {
						            
						        }
						    }
						});
						
						
					}
	    		}); 
    		} else if (currentCall == 1) {
	    		 $.ajax({ 
	    			url : "<%= request.getContextPath() %>/call/updateCall.do",
					data : {
						'callNum' : callNum,
						'senderName' : $("#userNameApply").val(),
						'senderAddress' : $("#address").val(),
						'senderAddressDetail' : $("#addressDetail").val(),
						'senderPhone' : $("#phone").val(),
						'vehicleType' : $("input:radio[name=radio-group]:checked").val(),
						'urgent' : urgent_value,
						'series' : group_value,
						'reserved' : reserve_value, /* 예약배송 여부는 사용 안할것같음 */
						'reservationTime' : $("#timeInput").val() /* 날짜데이터 어떻게 넣을지 확인해야 함 */
					},
					dataType : "json",
					method : "POST",
					success : gotoNextPage,
					error : function() {
						$.confirm({
						    title: '퀵 신청 오류',
						    columnClass: 'centerBox',
						    content: '퀵 신청에 오류가 발생했습니다. 다시 작성해주세요.',
						    type: 'red',
						    closeIcon: true,
						    typeAnimated: true,
						    theme: 'modern',
						    buttons: {
						        '확인': {
						            
						        }
						    }
						});
					}
	    		});     			
    		} /* if문 끝 */
    		
    	});
    	

    	
    	$("#getStartFavorite").on("click", function() {
	    	if($("#getStartFavorite").is(":checked")) {
	    		$.ajax({
	    			url : "<%= request.getContextPath() %>/call/getFavoriteDeparture.do",
					dataType : "json",
					method : "POST",
					success : setDepartureAddress,
					error : function() {
						$.confirm({
						    title: '즐겨찾기 불러오기 오류',
						    content: '즐겨찾기에 저장된 출발지가 없습니다. 지금 입력할 출발지를 즐겨찾기로 저장하시겠습니까?',
						    type: 'red',
						    columnClass: 'centerBox',
						    closeIcon: true,
						    typeAnimated: true,
						    theme: 'modern',
						    buttons: {
						        '즐겨찾기로 저장': {
						            btnClass: 'btn-green',
						        	action : function() {
						        		if($("#saveStartFavorite").is(":checked")) {
										
										} else {
											$("#saveStartFavorite").trigger("click");
										}
						        	}
						        },
						        '취소': {
						            
						        }
						    }
						});
						
					}
	    		});
	    	}
    		
    	});
    	
    	
    	
    	
 	});
 	
 
 	
 	function setDepartureAddress (JSONDocument) {
 		console.log(JSONDocument[0]);
 		$("#userNameApply").val(JSONDocument[0].receiverName);
		$("#address").val(JSONDocument[0].address);
		$("#addressDetail").val(JSONDocument[0].addrDetail);
		$("#phone").val(JSONDocument[0].receiverPhone);
 	}
 	
	
 	
 	
 	function gotoNextPage(JSONDocument) {
 		console.log("비동기통신 완료... 다음 페이지로 이동");
    	if($("#saveStartFavorite").is(":checked")) {
     		$.ajax({
     			url : "<%= request.getContextPath() %>/call/saveFavoriteDeparture.do",
				dataType : "json",
				method : "POST",
				data : {
   					addressType : 1,
   					address : $("#address").val(),
   					addrDetail : $("#addressDetail").val(),
   					receiverName : $("#userNameApply").val(),
   					receiverPhone : $("#phone").val()
				},
				success : saveDepartureAddress,
				error : function(JSONDocument) {
					console.log(JSONDocument);
				}
     		});

    	}
 		
 		location.href = "./quickApply_second.jsp";
 		/* 객체를 비동기로 바로 생성하고 시작하면 location.href로 아무것도 가져가지 말고 페이지 이동하면 됨
 			아니라면 데이터 모두 갖고 이동 */
 	}
 	
 	function saveDepartureAddress(JSONDocument) {
 		console.log(JSONDocument);
 	}
 	
 	
 	
   	function goPopup(buttonId){
   	    new daum.Postcode({
   	        oncomplete: function(data) {
   	        	$("#address").val(data.address + " (" + data.bname + ")");
   	    		/* $("#addressDetail").val(addrDetail); */
   	        }
   	    }).open();
   	}
    	 	
 	
/*  최신 콜 받아오기.  */
   	function getCurrentCall(JSONDocument) {
   		console.log(JSONDocument);
   		callNum = JSONDocument.callNum;
   		console.log("IF 전의 콜넘 " + callNum);
   		
   		$.confirm({
		    title: '작성중인 신청 불러오기',
		    content: '현재 작성중인 퀵이 있습니다. 이어서 작성하시겠습니까?',
		    type: 'red',
		    columnClass: 'centerBox',
		    closeIcon: true,
		    typeAnimated: true,
		    theme: 'modern',
		    buttons: {
		        '이어서 작성': {
		            btnClass: 'btn-green',
		        	action : function() {
		       			currentCall = 1;
		    			$("#userNameApply").val(JSONDocument.senderName);
		    			$("#address").val(JSONDocument.senderAddress);
		    			$("#addressDetail").val(JSONDocument.senderAddressDetail);
		    			$("#phone").val(JSONDocument.senderPhone);
		    			switch(JSONDocument.vehicleType) {
		    			case 1:
		    				$("#motorcycle").attr("checked", "checked");
		    				break;
		    			case 2:
		    				$("#damas").attr("checked", "checked");
		    				break;
		    			case 3:
		    				$("#labo").attr("checked", "checked");
		    				break;
		    			case 4:
		    				$("#truck").attr("checked", "checked");
		    				break;
		    			}
		    			if(JSONDocument.urgent == 1) {
		    				$('input:checkbox[id=urgentBox]').attr("checked", "checked");
		    			}
		    			
		    			if(JSONDocument.series == 1) {
		    				$("#groupDelivery").attr("checked", "checked");
		    			}
		    			
		    			if(JSONDocument.reserved == 1) {
		    				$("#reserveDelivery").attr("checked", "checked");
		    	    		$("#reserveBox").slideDown();
		    	    		$("#timeInput").val(JSONDocument.reservationTime);
		    			}
		    			
		    			$.ajax({
		    				url : "<%= request.getContextPath() %>/call/cancelOrders.do",
		       				data : {
		       					'callNum' : callNum
		       				},
		    				dataType : "json",
		    				method : "POST",
		    				success : function(JSONDocument) {
		    					if(JSONDocument) {
		    						console.log("모든오더 삭제");
		    					}
		    				},
		    				error : function() {
		    					$.confirm({
		    					    title: '신청 삭제 오류',
		    					    columnClass: 'centerBox',
		    					    content: '신청 삭제에 실패하였습니다. 처음부터 작성합니다.',
		    					    type: 'red',
		    					    closeIcon: true,
		    					    typeAnimated: true,
		    					    theme: 'modern',
		    					    buttons: {
		    					        '확인': {
		    					        }
		    					    }
		    					});
		    					
		    				}
		    			});
		    			
		    			console.log("IF 안의 콜넘 " + callNum);
		    			console.log(callNum);
		        	}
		        },
		        '처음부터 작성': function () {
		  			console.log("else IF 안의 콜넘 " + callNum);
		   			$.ajax({
		   				url : "<%= request.getContextPath() %>/call/cancelCall.do",
		   				data : {
		   					'callNum' : callNum
		   				},
						dataType : "json",
						method : "POST",
						success : function(JSONDocument) {
							if(JSONDocument) {
								console.log("처음부터 작성");
							}
						},
						error : function() {
							$.confirm({
							    title: '신청 삭제 오류',
							    columnClass: 'centerBox',
							    content: '신청 삭제에 실패하였습니다. 처음부터 작성합니다.',
							    type: 'red',
							    closeIcon: true,
							    typeAnimated: true,
							    theme: 'modern',
							    buttons: {
							        '확인': {
							        }
							    }
							});
						}
		   			});
		        }
		    }
		});
   		
   		
   	}
 	
</script>





<title>NexQuick :: 퀵 신청하기 - 기본정보 설정</title>
</head>
<body>

<%@ include file = "../quickApplyPage/quickApply_going_box.jsp" %>


<div class = "row">
	<div class = "col-md-6 text-conceptColor">
		<h2 class = "centerBox quickFirstTitle">
			출발지
		</h2>
		
		<div class = "centerBox">
			<label class="label font_1em paddingZero">
			    <input  class="label__checkbox" type="checkbox" id = "saveStartFavorite" />
			    <span class="label__text">
			      <span class="label__check ml-5">
			        <i class="fa fa-check icon font_1em"></i>
			      </span>
			    </span>
		  	</label>
		  	<label for="saveStartFavorite" style = "line-height: 28px; color: #34495e">기본 출발지로 저장</label>
		  	
			<label class="label font_1em paddingZero">
			    <input  class="label__checkbox" type="checkbox" id = "getStartFavorite" />
			    <span class="label__text">
			      <span class="label__check ml-5">
			        <i class="fa fa-check icon font_1em"></i>
			      </span>
			    </span>
		  	</label>
		  	<label for="getStartFavorite" style = "line-height: 28px; color: #34495e">기본 출발지와 동일</label>
		</div>
		
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
			    <input  class="label__checkbox" type="checkbox" id = "urgentBox"/>
			    <span class="label__text ">
			      <span class="label__check" id = "urgentText">
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
		<div class = "row justify-content-center mt-5">
			<div class = "col-md-6 offset-md-3">
				<div id = "reserveBox" class = "centerBox mt-3" style = "display: none;">			  	
					<div class="group mb-0" style = "width: 100%;">      
					    <input class = "datepicker-here inputDesignForApply" type="text" id = "timeInput" style = "width: 100%;" data-language='en' data-timepicker="true">
					    <span class="highlight"></span>
					    <span class="barApply"></span>
					    <label class = "labelDesignForApply text-conceptColor"><i class="far fa-clock"></i> 예약시간 설정</label>
					</div>
		  		</div>
	  		</div>
	  	</div>
<!-- 
				<span>예약시간 : </span>
				<label class="field field_animated field_a2 page__field time">
			      <input type="text" class="addressFormSub field__input" placeholder="클릭해서 시간 설정" name = "time" id="timeInput" readonly = "readonly" data-toggle="modal" data-target="#modal1" style = "width: 12.5em!important;">
			      <span class="field__label-wrap">
			        <span class="field__label text-conceptColor">시간 설정</span>
			      </span>
			    </label>
			</div> -->
		  	
		  	
		
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
		<button class = "dangerBorder mt-4" id = "cancelBtn">
		<i class="far fa-times-circle"></i> 주문취소</button>
	</div>
	<div class = "col-md-2">
		<button class = "ColorBorder mt-4" id = "goNextBtn">
		<i class="fas fa-caret-right"></i> 다음으로</button>
	</div>
</div>




<%@ include file = "../footer.jsp" %>







</body>
</html>