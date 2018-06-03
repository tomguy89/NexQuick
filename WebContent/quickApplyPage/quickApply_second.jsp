<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NexQuick :: 퀵 신청하기 - 상세정보 설정</title>

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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/momentjs/2.14.1/moment.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

<script type="text/javascript">


	$(function() {
/* 		document.domain = "70.12.109.171"; */
		/* document.domain = "http://70.12.109.171/NexQuick/quickApplyPage/quickApply_second.jsp"; */
		
		
		/* 단계별 이미지박스 설정.(2단계 : 세번째 그림만 회색으로) */
    	$("#right_Img_2").attr("src", "<%= request.getContextPath() %>/image/quickApply_img/right_grey.png");
    	$("#completeImg").attr("src", "<%= request.getContextPath() %>/image/quickApply_img/complete_grey.png");
    	$("#completeImgText").css("color", "#d1d1d1");
		
    	$('.carousel').carousel({
    	    interval: false
    	}); 
    	
    	var dataIndex = 1;
    	var trIndex = 0;
    	var countIndex = 1;
    	
    	/* 오더버튼 추가 */
    	$("#addOrderBtn").on("click", function() {
    		
    		countIndex++;
    		
    		$("div.carousel-inner > div.carousel-item").removeClass("active");
    		

    		/* 목록 추가 */
    		$(".carousel-inner")
    		.append(
    				$("<div class = carousel-item/>").attr("id", "car-item"+dataIndex)
    				.addClass("active")
    				.append(
						$("<div class=row>")
						.append(
							$("<div class=col-md-7>")
							.append(
									/* 주문번호 박스 붙임 */
		    						$("<div class=callForm>")
		    						.append(
		    							$("<div class = row>")
		    							.append(
		    								$("<div class = col-md-6>")
		    								.append(
		    									$("<button type = button class = ColorBorder><i class='fas fa-download'></i>&nbsp;불러오기</button>").attr("id", "callFavorite"+dataIndex)
		    								)
		    							).append(
		    								$("<div class = col-md-6>")
		    								.append(
		    									$("<label class='label font_1em paddingZero'>")
		    									.append(
		    										$("<input class=label__checkbox type=checkbox />").attr("id", "saveFavorite"+dataIndex)
		    									).append(
		    										$("<span class=label__text>")
		    										.append(
		    											$("<span class=label__check>")
		    											.append(
		    												$("<i class='fa fa-check icon font_1em'></i>")	
		    											)
		    										)
		    									)
		    								).append(
		    										$("<label style = 'line-height: 35px; color: #34495e; height: 35px; margin-bottom: 0px!important;'>즐겨찾기에 추가</label>").attr("for", "saveFavorite"+dataIndex)
		    								)
		    							)
		    						).append(
		    							$("<span style='display: none;'>").append(
		    								$("<span class='ml-5'>주문번호 : </span>")		
		    							).append(
		    								$("<label class='field field_animated field_a2 page__field call'>")
		    								.append(
	    										$("<input type=text class='orderNumberForm field__input' placeholder='도착정보를 저장하세요.' readonly>").attr("id", "callId"+dataIndex)
		    								).append(
	   											$("<span class='field__label-wrap'>")
	    										.append(
	   												$("<span class='field__label text-conceptColor'>저장 시 생성</span>")
	    										)
		    								)
			    						)
		    						)
		    				).append(
		    						$("<div class = 'centerBox mt-0'/>")
		    						.append(
	    								$("<img class='mr-3' src = <%= request.getContextPath() %>/image/quickApply_img/map.png width = 40 height = 40 />")
		    						).append(
	    								$("<label class='field field_animated field_a2 page__field add'>")
	    								.append(
	    									$("<input type=text class='addressForm field__input' placeholder='클릭해서 주소를 검색하세요.' onclick='goPopup(this);' name = 'address' readonly>").attr("id", "address" + dataIndex)		
	    								).append(
	    									$("<span class='field__label-wrap'>")
	    									.append("<span class='field__label text-conceptColor'>주소</span>")
	    								)
		    						)
								/* 첫번째 centerbox까지 붙임(주소) */    				
		    				)
		    				.append(
		    						$("<div class = centerBox/>")
		    						.append(
	    								$("<img class = 'mr-3' src = <%= request.getContextPath() %>/image/quickApply_img/addressDetail.png width = 40 height = 40 />")
		    						).append(
	    								$("<label class='field field_animated field_a2 page__field addD'>")
	    								.append(
	    									$("<input type=text class='addressForm field__input' placeholder='ex) 멀티캠퍼스 9층 904호' name = 'addressDetail'>").attr("id", "addressDetail" + dataIndex)		
	    								).append(
	    									$("<span class='field__label-wrap'>")
	    									.append("<span class='field__label text-conceptColor'>상세주소</span>")
	    								)
		    						)
		    					/* 두번째 centerbox까지 붙임(상세주소) */
		    				)
		    				.append(
		    						$("<div class = centerBox/>")
		    						.append(
	    								$("<img class = 'mr-3' src = <%= request.getContextPath() %>/image/quickApply_img/name.png width = 40 height = 40 />")
		    						).append(
	    								$("<label class='field field_animated field_a2 page__field name'>")
	    								.append(
    										$("<input type=text class='addressFormSub field__input' placeholder='ex) 김민규' name = userNameApply'>").attr("id", "userNameApply" + dataIndex)
	    								).append(
    										$("<span class='field__label-wrap'>")
    										.append("<span class='field__label text-conceptColor'>수령인</span>")
	    								)
		    						).append(
	    								$("<img class = 'mr-3'  src = <%= request.getContextPath() %>/image/quickApply_img/phone.png width = 40 height = 40 style = 'margin-left : 1.5em;'/>")
		    						).append(
	    								$("<label class='field field_animated field_a2 page__field phone'>")
	    								.append(
    										$("<input type=text class='addressFormSub field__input' placeholder='ex) 01049408292' name = phone'>").attr("id", "phone" + dataIndex)
	    								).append(
    										$("<span class='field__label-wrap'>")
    										.append("<span class='field__label text-conceptColor'>수령인 연락처</span>")
	    								)
		    						)
		    					/* 이름, 연락처 */
		    				).append(
									
/* 									<button class = "ColorBorder mt-5" id = "saveOrder0" type = "button" disabled = "disabled"><i class="far fa-save" id = "saveIcon0"></i><span id = "saveText0">&nbsp;도착지 저장하기</span></button>
									<button class = "ColorBorder mt-5" id = "saveFavorite0" type = "button" disabled = "disabled"><i class="far fa-star" id = "favIcon0"></i><span id = "favoriteText0">&nbsp;입력하세요.</span></button> */
		    						$("<div class = centerBox  style = 'width: 100%; height: 100px;'>")
		    						.append(
		    							$("<button class = 'dangerBorder mt-5' type = button/>")
		    							.append(
		    								$("<i class='far fa-times-circle'></i>")		
		    							).append(
		    								$("<span>&nbsp;도착지 삭제하기</span>")
		    							).attr("id", "deleteOrder"+dataIndex).on("click", function() {
		    								/* 오더 삭제 비동기통신 넣을 자리 */
		    								$.ajax({
		    									
		    								});
		    								
		    								/* 해당 오더 삭제하는 기능 */
		    								var id = $(this).attr("id").substring(11);
		    								var $car_Item = "#car-item"+id;
		    								var $li = "#li"+id;
		    								var result = confirm("해당 배송지를 삭제하시겠습니까?");
		    								if(result) {
			    								$($car_Item).remove();
			    								$($li).remove();
			    							    countIndex--;
			    							    console.log(countIndex);
			    								var listLocation = "div.carousel-inner > div:nth-child("+(countIndex)+")";
			    								var liLocation = "ol.carousel-indicators > li:nth-child("+(countIndex)+")";
			    							    console.log(listLocation);
			    								$(listLocation).addClass("active");
			    								$(liLocation).addClass("active");
		    								}
		    							})
		    						).append(
		    							$("<button class = 'ColorBorder mt-5' type = button disabled = disabled>")
		    							.append(
		    								$("<i class='far fa-save'></i>").attr("id", "saveIcon"+dataIndex)
		    							).append(
		    								$("<span>&nbsp;도착지 저장하기</span>").attr("id", "saveText"+dataIndex)
		    							).attr("id", "saveOrder"+dataIndex)
		    						)
		    				)
		    				/* 오른쪽 리스트 붙이기 시작 */
						).append(
							$("<div class='col-md-5' style = 'display : none;'>").attr("id", "list"+dataIndex)
							.append(
    						$("<div class = 'row mb-5'>")
    						.append(
    								$("<div class = 'col-md-4'>")
    								.append(
    										$("<select>").attr("id", "item"+dataIndex).attr("name", "item"+dataIndex)
    										.append(
    												$("<option>서류</option>").attr("value", "document"+dataIndex)	
    										).append(
    												$("<option>박스(소, 1~5kg)</option>").attr("value", "smallBox"+dataIndex)
    										).append(
    												$("<option>박스(중, 5~15kg)</option>").attr("value", "middleBox"+dataIndex)
    										).append(
    												$("<option>박스(대, 15kg 이상)</option>").attr("value", "bigbox"+dataIndex)
    										)
    								)
    						).append(
    								$("<link/>")
    									.attr("rel", "stylesheet")
    									.attr("type", "text/css")
    									.attr("href", "<%= request.getContextPath() %>/css/selectionBoxStyle.css")
    						).append(
    								$("<script/>")
    									.attr("type", "text/javascript")
    									.attr("src", "<%=request.getContextPath()%>/js/selectionBoxStyle.js")
    						).append(
    								$("<div class = 'col-md-8 my-auto'>")
    								.append(
    										$("<span>개수 : </span>")
    								).append(
    										$("<input type = number min=1 value=1 style = 'width: 3em;'/>").attr("id", "count"+dataIndex)
    								).append(
										$("<img src = <%= request.getContextPath() %>/image/quickApply_img/plusBtn.png width = 20 height = 20/>").attr("id", "saveFreight"+dataIndex).on("click", function() {
											var id = $(this).attr("id").charAt($(this).attr("id").length-1);
											var $selectId = "#item"+id;
											var $countId = "#count"+id;
											var tableIndex = "table#table"+id+" > tbody";
											var freight_type = $($selectId).val();
											var freight_name;
											if(freight_type == "document"+dataIndex) {
												freight_name = "서류";
											} else if (freight_type == "smallBox"+dataIndex) {
												freight_name = "박스(소)";
											} else if (freight_type == "middleBox"+dataIndex) {
												freight_name = "박스(중)";
											} else if (freight_type == "bigBox"+dataIndex) {
												freight_name = "박스(대)";
											}
											/* 화물 추가 비동기통신 */
											$.ajax({
												
											});
											
											/* 옆에 리스트에 추가 */
											$(tableIndex).append(
												$("<tr>").attr("id", "tr"+trIndex)
												.append(
														$("<td>").text(freight_name)
												).append(
														$("<td>").text($($countId).val()+"개")
												).append(
														$("<td>").append(
															$("<img src = <%=request.getContextPath()%>/image/quickApply_img/minusBtn.png width=20 height=20 />").attr("id", "minusBtn"+trIndex).on("click", function() {
																var trId = $(this).attr("id").charAt($(this).attr("id").length-1);
																var $trId = "#tr"+trId;
																/* 화물 삭제 비동기통신 */
																$.ajax({
																	
																});
																$($trId).remove();
															})
														)
												)
											)
											trIndex++;
										})
    								)
	    						)
	    				).append(
							$("<table class='table table-bordered'>").attr("id", "table"+dataIndex)
							.append(
								$("<tbody>")
							)
						)
					) 
   				)
    				/* carousel-item에 붙이는것 끝나는 부분 */		
    		);
    		/* 목록 폼을 붙임 */
    		
    		/* 목록 밑에 있는 인덱스 부분 붙이기 */
    		var $listIndex = $("<li data-target=#carouselExampleIndicators></li>")
    		$("ol.carousel-indicators > li").removeClass("active");
    		$(".carousel-indicators").append(
    				$listIndex.attr("data-slide-to", dataIndex).addClass("active").attr("id", "li"+dataIndex)
    		);
    		
    		/* 새로운 인덱스로 화면이동 */
			$('.carousel-item').carousel(dataIndex);
			
			$(".select").css("display", "flex").css("margin-left", "auto").css("margin-right", "auto");
						
			
			var id = $(this).attr("id").charAt($(this).attr("id").length-1);
			/* 값 변경시 저장버튼 활성화 */
			var addressDetail = "#addressDetail"+dataIndex;
			var userNameApply = "#userNameApply"+dataIndex;
			var phone = "#phone"+dataIndex;
			var saveOrder = "#saveOrder" + dataIndex;
			var saveText = "#saveText" + dataIndex;
			var saveIcon = "#saveIcon" + dataIndex;
			var saveFavorite = "#saveFavorite" + dataIndex;
			var favIcon = "#favIcon" + dataIndex;
			var favoriteText = "#favoriteText" + dataIndex;
			var callFavorite = "#callFavorite" + dataIndex;
			var list = "#list"+dataIndex;
			
	 		$(addressDetail).keyup(function() {
	  			if(/* $("#address0").val().trim().length != 0 && */ $(addressDetail).val() != ""
						&& $(userNameApply).val() != "" && $(phone).val() != "") {
		  				$(saveOrder).removeAttr("disabled");
			  			$(saveText).text(" 도착지 저장하기");
			  			$(saveIcon).removeClass("fas").addClass("far");
	  			 } else {
	  				$(saveOrder).attr("disabled", "disabled");
	  			 }
	  		});
	  		$(userNameApply).keyup(function() {
	  			if(/* $("#address0").val().trim().length != 0 && */ $(addressDetail).val() != ""
					&& $(userNameApply).val() != "" && $(phone).val() != "") {
	  				$(saveOrder).removeAttr("disabled");
		  			$(saveText).text(" 도착지 저장하기");
		  			$(saveIcon).removeClass("fas").addClass("far");
  			 } else {
  				$(saveOrder).attr("disabled", "disabled");
  			 }
	  		});
	  		$(phone).keyup(function() {
	  			if(/* $("#address0").val().trim().length != 0 && */ $(addressDetail).val() != ""
					&& $(userNameApply).val() != "" && $(phone).val() != "") {
	  				$(saveOrder).removeAttr("disabled");
		  			$(saveText).text(" 도착지 저장하기");
		  			$(saveIcon).removeClass("fas").addClass("far");
	  			 } else {
	  				$(saveOrder).attr("disabled", "disabled");
	  			 }
	  		});
			
	  		$(saveOrder).on("click", function() {
	  			alert(this.id + "번째 도착지 정보가 저장되었습니다. 물품 정보를 설정하세요.");
				$(list).slideDown(2000);
				$(saveOrder).attr("disabled", "disabled");
				$(saveText).text(" 도착지 정보 저장됨");
				$(saveIcon).removeClass("far").addClass("fas");
				/* 비동기통신. 오더 저장 */
				$.ajax({
					
				});
				
	  		});
	  		
	  		$(saveFavorite).on("click", function() {
	  			console.log(this.id + "번째가 즐겨찾기에 저장됨");
	  			$.ajax({
	  				
	  			});
	  			
	  		});
	  		
	  		$(callFavorite).on("click", function() {
	  			/* 즐겨찾기 불러오기 비동기통신 */
	  			$.ajax({
	  				
	  			});
	  			
	  		});
	  		
	  		/* 입력란 마우스오버 이벤트 */
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
	  	 	$(".call").mouseenter(function() {
	    		$(".call").focus();
	    	});
	  		
	  	 	var id;
	  	 	var real_id;
	  	 	var addressId;
	  	 	var addressDetailId;
			$("input[name=address]").on("click", function() {
				id = this.id;
				real_id = id.substring(7);
				addressId = "#address"+real_id;
				addressDetailId = "#address"+real_id;
				
				goPopup();
			});
	
	  	 	
	  	 	
	  	 	
	  	 	
	  	 	
	  	 	
			dataIndex++;
    	}); /* 추가 이벤트처리 끝 */
    	
    	
    	/* 입력란 마우스오버 이벤트 */
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
  	 	$(".call").mouseenter(function() {
    		$(".call").focus();
    	});
  		
    	
    	
    	
    	
    	$(".select").css("display", "flex").css("margin-left", "auto").css("margin-right", "auto");
  		
  		$("#addressDetail0").keyup(function() {
  			console.log("ad");
  			if(/* $("#address0").val().trim().length != 0 && */ $("#addressDetail0").val() != ""
				&& $("#userNameApply0").val() != "" && $("#phone0").val() != "") {
		  			$("#saveOrder0").removeAttr("disabled");
		  			$("#saveText0").text(" 도착지 저장하기");
		  			$("#saveIcon0").removeClass("fas").addClass("far");
			 } else {
				$("#saveOrder0").attr("disabled", "disabled");
			 }
  		});
  		$("#userNameApply0").keyup(function() {
  			if(/* $("#address0").val().trim().length != 0 && */ $("#addressDetail0").val() != ""
				&& $("#userNameApply0").val() != "" && $("#phone0").val() != "") {
		  			$("#saveOrder0").removeAttr("disabled");
		  			$("#saveText0").text(" 도착지 저장하기");
		  			$("#saveIcon0").removeClass("fas").addClass("far");
			 } else {
				$("#saveOrder0").attr("disabled", "disabled");
			 }
  		});
  		$("#phone0").keyup(function() {
  			if(/* $("#address0").val().trim().length != 0 && */ $("#addressDetail0").val() != ""
				&& $("#userNameApply0").val() != "" && $("#phone0").val() != "") {
		  			$("#saveOrder0").removeAttr("disabled");
		  			$("#saveText0").text(" 도착지 저장하기");
		  			$("#saveIcon0").removeClass("fas").addClass("far");
			 } else {
				$("#saveOrder0").attr("disabled", "disabled");
			 }
  		});
  		
  		/* 첫번째 즐겨찾기 */
  		$("#saveFavorite0").on("click", function() {
  			console.log("즐겨찾기에 저장됨");
  			/* 즐겨찾기 데이터 보내서 DB에 저장하는 비동기통신 */
  			$.ajax({
  				
  			});
  			
  		});
  		
  		
  		
  		/* 첫번째 오더의 주문 저장. */
    	$("#saveOrder0").on("click", function() {

			/* 오더 저장 비동기통신 */
    		$.ajax({
   				url : "<%= request.getContextPath() %>/call/addOrder.do",
   				data : {
   					receiverName : $("#userNameApply0").val(),
   					receiverAddress : $("#address0").val() + " " + $("#addressDetail0").val(),
   					receiverPhone : $("#phone0").val(),
   					memo : "wow"
   				},
   				dataType : "json",
   				method : "POST",
   				success : addOrder
    		});	
			
    	});
    	
  		
  		var id;
  		var $selectId;
  		var $countId;
  		var tableIndex;
  		var item;
  		var freightType;
  		var btnId;
  		var trId;
  		var trRemove;
  		function addOrder(JSONDocument) {
  			alert("도착지 정보가 저장되었습니다. 물품 정보를 설정하세요.");
			$("#list0").slideDown(2000);
			$("#saveOrder0").attr("disabled", "disabled");
			$("#saveIcon0").removeClass("far").addClass("fas");
			$("#saveText0").text(" 도착지 정보 저장됨");
			
			
			/* 첫 버튼 기능등록 */
	    	$("#saveFreight0").on("click", function() {
				btnId = $(this).attr("id").substring(11);
				$selectId = "#item0";
				$countId = "#count0";
				tableIndex = "table#table"+btnId+" > tbody";
				switch($($selectId).val().substring(0,1)) {
				case 'd':
					item = "서류";
					freightType = 1;
					break;
				case 's':
					item = "박스(소)";
					freightType = 2;
					break;
				case 'm':
					item = "박스(중)";
					freightType = 3;
					break;
				case 'b':
					item = "박스(대)";
					freightType = 4;
					break;
				}
				
				alert(JSONDocument.orderNum);
				
				/* 화물 추가 비동기통신 */
				$.ajax({
	   				url : "<%= request.getContextPath() %>/call/addFreight.do",
	   				data : {
	   					orderNum : JSONDocument.orderNum,
	   					freightType : freightType,
	   					freightQuant : $($countId).val(),
	   					freightDetail : "화물상세"
	   				},
	   				dataType : "json",
	   				method : "POST",
	   				success : addFreight
					
				});
			});
  		};
  		
  		function addFreight(JSONDocument) {
			$(tableIndex).append(
				$("<tr>").attr("id", "tr"+trIndex)
				.append(
					$("<td>").text(item)
				).append(
					$("<td>").text($($countId).val()+"개")
				).append(
					$("<td>").append(
						$("<img src = <%=request.getContextPath()%>/image/quickApply_img/minusBtn.png width=20 height=20 />").attr("id", "minusBtn"+trIndex).on("click", function() {
							trId = $(this).attr("id").substring(8);
							trRemove = "#tr"+trId;
							/* 화물 삭제 비동기통신 */
							$.ajax({
								url : "<%= request.getContextPath() %>/call/delFreight.do",
				   				data : {
				   					freightNum : JSONDocument.freightNum
				   				},
				   				dataType : "json",
				   				method : "POST",
				   				success : delFreight
							});
						})
					)
				)
			)
				trIndex++;
  		}  		
  		
  		function delFreight(JSONDocument) {
  			var result = confirm("정말 삭제하시겠습니까?");
  			if(result) {
	  			$(trRemove).remove();
  			}
  		}
  		
  		
  		$("#callFavorite0").on("click", function() {
  			/* 즐겨찾기 불러오기 비동기통신 */
  			$.ajax({
  				
  			});
  			
  		});
  		
    	 
   		$('#deleteOrder0').popover();
   		
    	$("#deleteOrder0").mouseenter(function() {
    		$('#deleteOrder0').trigger("click");
    	});
    	
    	$("#deleteOrder0").mouseout(function() {
    		$('#deleteOrder0').trigger("click");
    	});
    	
    	
    	
    	
    	/* 주소 입력 API! */
    	
    	
    	
    	
    	
    	
    	
	});
	
	
	var btnId;
	var btn_real;
   	function goPopup(buttonId){
   		// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
   	    var pop = window.open("<%=request.getContextPath()%>/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes");
   		btnId = $(buttonId).attr("id");
   		btn_real = btnId.substring(7);
   		// 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
   	    //var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes"); 
   	}
   	/** API 서비스 제공항목 확대 (2017.02) **/
   	function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn
   							, detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo){
   		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
   		var address = "#address" + btn_real;
   		var addressDetail = "#addressDetail" + btn_real;
   		$(address).val(roadAddrPart1);
   		$(addressDetail).val(addrDetail);
   		/* $(addressDetailId).val(roadAddrPart2); */
   		/* document.form.roadAddrPart1.value = roadAddrPart1;
   		document.form.roadAddrPart2.value = roadAddrPart2;
   		document.form.addrDetail.value = addrDetail;
   		document.form.zipNo.value = zipNo; */
   	} 
    	 	
	
	
	
	

</script>



</head>
<body>
<%@ include file = "../navigation.jsp" %>
<%@ include file = "../quickApplyPage/quickApply_going_box.jsp" %>


<div class= "row mt-5">
	<div class = "col-md-12">
		<div class = "row" style = "width: 100%;">
			<div class = "col-md-7">
				<h2 class = "centerBox quickFirstTitle mb-5 text-conceptColor">
					도착지 정보
				</h2>
			</div>
			<div class = "col-md-3">
				<h2 class = "centerBox quickFirstTitle mb-5 text-conceptColor">
					물품 정보
				</h2>
			</div>
		</div>
		<div id="carouselExampleIndicators" class="carousel slide" data-interval="false">
		  <ol class="carousel-indicators mt-5 mb-5">
		    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active" id = "li0"></li>
		  </ol>
		  
		  
		  
		  <!--  -->
		<form action = "/" >
		  
		  
		  <!-- 배송지 목록 -->
			<div class="carousel-inner mb-5" style="overflow: visible;">
			  
			  <div class="carousel-item active" id = "car-item0">
			  
		  		<div class = "row">
		  			<div class = "col-md-7">
		  			
				  		<div class = "callForm">
				  			
				  			<div class = "row">
				  				<div class = "col-md-6">
				  					<button type = "button" id = "callFavorite0" class = "ColorBorder"><i class="fas fa-download"></i>&nbsp;불러오기</button>
				  				</div>
								<div class = "col-md-6">
								  	<label class="label font_1em paddingZero">
									    <input class="label__checkbox" type="checkbox" id = "saveFavorite0" />
									    <span class="label__text">
									      <span class="label__check">
									        <i class="fa fa-check icon font_1em"></i>
									      </span>
									    </span>
								  	</label>
							  		<label for="saveFavorite0" style = "line-height: 35px; color: #34495e; height: 35px; margin-bottom: 0px!important;">즐겨찾기에 추가</label>
								</div>
				  			
				  			</div>
				  			
				  			
				  			<span style = "display: none;">
					  			<span class = "ml-5">주문번호 : </span>
					  			
					  			 <label class="field field_animated field_a2 page__field call">
								     <input type="text" class="orderNumberForm field__input" placeholder="도착정보를 저장하세요." name = "callId" id="callId0" readonly>
								     <span class="field__label-wrap">
								     	<span class="field__label text-conceptColor">저장 시 생성</span>
								     </span>
								 </label>   
				  			</span>
				  		</div>
				  		
						<div class = "centerBox mt-0">
							<img class = "mr-3" src = "<%= request.getContextPath() %>/image/quickApply_img/map.png" width = "40" height = "40" />
							<label class="field field_animated field_a2 page__field add">
						    	<input type="text" class="addressForm field__input" placeholder="클릭해서 주소를 검색하세요." name = "address" id="address0" onclick = "goPopup(this);" readonly>
						    	<span class="field__label-wrap">
						    		<span class="field__label text-conceptColor">주소</span>
						    	</span>
						    </label>   
							
						</div>
						<div class = "centerBox">
							<img class = "mr-3" src = "<%= request.getContextPath() %>/image/quickApply_img/addressDetail.png" width = "40" height = "40" />
							
							<label class="field field_animated field_a2 page__field addD">
						    	<input type="text" class="addressForm field__input" placeholder="ex) 멀티캠퍼스 9층 904호" name = "addressDetail" id="addressDetail0">
						    	<span class="field__label-wrap">
						    		<span class="field__label text-conceptColor">상세주소</span>
						    	</span>
						    </label>   
							
						</div>
						<!-- 이름, 연락처 -->
						<div class = "centerBox">
							<img class = "mr-3" src = "<%= request.getContextPath() %>/image/quickApply_img/name.png" width = "40" height = "40" />
							<label class="field field_animated field_a2 page__field name">
						      <input type="text" class="addressFormSub field__input" placeholder="ex) 김민규" name = "userNameApply" id="userNameApply0">
						      <span class="field__label-wrap">
						        <span class="field__label text-conceptColor">수령인</span>
						      </span>
						    </label>   
						    
							<img class = "mr-3" src = "<%= request.getContextPath() %>/image/quickApply_img/phone.png" width = "40" height = "40" style = "margin-left : 1.5em;"/>
							<label class="field field_animated field_a2 page__field phone">
						      <input type="text" class="addressFormSub field__input" placeholder="ex) 01049408292" name = "phone" id="phone0">
						      <span class="field__label-wrap">
						        <span class="field__label text-conceptColor">수령인 연락처</span>
						      </span>
						    </label>   
						</div>
						
						<div class = "centerBox" style = "width: 100%; height: 100px;">
							<button class = "dangerBorder mt-5" id = "deleteOrder0" type = "button" data-container="body" data-toggle="popover" data-placement="bottom" data-content="첫번째 주문 정보는 삭제가 불가합니다." readonly><i class="far fa-times-circle"></i>&nbsp;도착지 삭제하기</button>
							<button class = "ColorBorder mt-5" id = "saveOrder0" type = "button" disabled = "disabled"><i class="far fa-save" id = "saveIcon0"></i><span id = "saveText0">&nbsp;도착지 저장하기</span></button>
						</div>
					</div>
					
						<!-- 리스트 목록 나옴 -->
						<div class = "col-md-5" style = "display : none;" id = "list0">
							
							<!-- selectBox -->
							<div class = "row mb-5">
								<div class = "col-md-4">
									<select id="item0" name = "item0">
									  <option value="document0">서류</option>
									  <option value="smallBox0">박스(소, 1~5kg)</option>
									  <option value="middleBox0">박스(중, 5~10kg)</option>
									  <option value="bigBox0">박스(대, 15kg 이상)</option>
									</select>
								</div>
								<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/selectionBoxStyle.css"/>
								<script type="text/javascript" src="<%=request.getContextPath()%>/js/selectionBoxStyle.js"></script>
								<!-- 개수확인 -->
								<div class = "col-md-8 my-auto">
									<span>개수 : </span>
									<input type = "number" min="1" value="1" style = "width: 3em;" id = "count0"/>
									<img src = "<%= request.getContextPath() %>/image/quickApply_img/plusBtn.png" id= "saveFreight0" width = "20" height = "20"/>
								</div>
								<!-- 저장버튼 -->
							</div>
							
							<!-- 빈테이블 아님. 오른쪽 리스트에 붙일 위치 -->							
							<table class="table table-bordered" id = "table0">
							  <tbody>
							  </tbody>
							</table>
						</div>
						
					</div>	
				</div>  <!-- carousel Item 끝 -->
				
			</div>
			
			<div class = "centerBox mt-5">
				<!-- <input type = "submit" class = "centerBox"/> -->
				<button id = "orderNow" type = "button" class = "ColorBorder">주문하기</button>
				<button id = "addOrderBtn" type = "button" class = "ColorBorder">배송지 추가하기</button>
			</div>
			
		</form>
		  
		  <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev"  id="leftBtn">
		    <img src = "<%= request.getContextPath() %>/image/quickApply_img/left_form.png" width = "30" height= "30"/>
		    <span class="sr-only">Previous</span>
		  </a>
		  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next"  id = "rightBtn">
		    <img src = "<%= request.getContextPath() %>/image/quickApply_img/right_form.png" width = "30" height= "30"/>
		    <span class="sr-only">Next</span>
		  </a>
		  
		</div>
	</div>
	
	
	
	<!-- 결제버튼 들어갈곳 -->
<%-- 	<div class = "col-md-5">
		<!-- 카드 + 선불 / 카드 + 후불 -->
		<div class = "row">
			<button>
				<img alt="" src="<%= request.getContextPath() %>/image/quickApply_img/credit-card_black.png" width = "50" height = "50"/>
				<img alt="" src="<%= request.getContextPath() %>/image/quickApply_img/login_black.png" width = "50" height = "50"/>
			</button>
			<button>
				<img alt="" src="<%= request.getContextPath() %>/image/quickApply_img/credit-card_black.png" width = "50" height = "50"/>
				<img alt="" src="<%= request.getContextPath() %>/image/quickApply_img/logout_black.png" width = "50" height = "50"/>
			</button>
		</div>
		<!-- 현금 + 선불 / 현금 + 후불 -->
		<div class = "row">
			<button>
				<img alt="" src="<%= request.getContextPath() %>/image/quickApply_img/coins-black.png" width = "50" height = "50"/>
				<img alt="" src="<%= request.getContextPath() %>/image/quickApply_img/login_black.png" width = "50" height = "50"/>
			</button>
			<button>
				<img alt="" src="<%= request.getContextPath() %>/image/quickApply_img/coins-black.png" width = "50" height = "50"/>
				<img alt="" src="<%= request.getContextPath() %>/image/quickApply_img/logout_black.png" width = "50" height = "50"/>
			</button>
		</div>
		<!-- 입금 + 선불 / 기업 신용결제 -->
		<div class = "row">
			<button>
				<img alt="" src="<%= request.getContextPath() %>/image/quickApply_img/atm-machine_black.png" width = "50" height = "50"/>
				<img alt="" src="<%= request.getContextPath() %>/image/quickApply_img/login_black.png" width = "50" height = "50"/>
			</button>
			<button>
				<img alt="" src="<%= request.getContextPath() %>/image/quickApply_img/building_black.png" width = "50" height = "50"/>
				<img alt="" src="<%= request.getContextPath() %>/image/quickApply_img/hand-shake_black.png" width = "50" height = "50"/>
			</button>
		</div>
		
	</div> --%>
</div>

<%@ include file = "../footer.jsp" %>




<div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h2 class="modal-title centerBox" id="exampleModalCenterTitle">주소 찾기</h2>
      </div>
      <div class="modal-body">
		
       
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