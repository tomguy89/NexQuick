<%@ page import = "com.nexquick.model.vo.FavoriteInfo" %>
<%@ page import = "java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NexQuick :: 퀵 신청하기 - 상세정보 설정</title>

<!--===============================================================================================-->	
<script src="../Table_Highlight_Vertical_Horizontal/vendor/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="https://service.iamport.kr/js/iamport.payment-1.1.5.js"></script>

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
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/boxStyle.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/momentjs/2.14.1/moment.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<%@ include file = "../navigation.jsp" %>
<script type="text/javascript">
var isUrgent = 1;
	$(function() {

		
		
    	setTimeout(function(){
	        $('.trans--grow').addClass('grow');
	    }, 275);
    	
		
		var callNum = <%= request.getSession().getAttribute("callNum")%>;
		/* 콜넘버 받아와서 그룹배송이 체크안되어있었으면 오더추가 막기 */
		$.ajax({
			url : "<%= request.getContextPath() %>/call/getCall.do",
			data : {
				callNum : callNum
			},
			dataType : "json",
			method : "POST",
			success : callInfomation
		});
		
		
		
		
		
		var totalPrice = 0;
		/* 단계별 이미지박스 설정.(2단계 : 세번째 그림만 회색으로) */
    	$("#placeholderImg").attr("src", "<%= request.getContextPath() %>/image/quickApply_img/placeholder_link.png");
    	$("#right_Img_2").attr("src", "<%= request.getContextPath() %>/image/quickApply_img/right_grey.png");
    	$("#completeImg").attr("src", "<%= request.getContextPath() %>/image/quickApply_img/complete_grey.png");
    	$("#completeImgText").css("color", "#d1d1d1");
    	$("#placeholderImgText").css("color", "#00B7FF");
    	
	    $('#placeholderImgText').popover();   
	
	
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
		    									$("<button type = button class = ColorBorder onclick = 'getSaveId(this)'><i class='fas fa-download'></i>&nbsp;불러오기</button>").attr("id", "callFavorite"+dataIndex)
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
		    							$("<span style='display: none;'>")
		    							.append(
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
		    					$("<div class = centerBox>")
		    					.append(
		    						$("<label class='field field_animated field_a2 page__field textArea'>")
		    						.append(
		    							$("<textarea type=text class='textAreaForm field__input' placeholder='ex) 부재시 경비실에 맡겨주세요' name = quickMemo ></textarea>").attr("id", "quickMemo"+dataIndex)		
		    						).append(
		    							$("<span class='field__label-wrap'>")
		    							.append(
		    								$("<span class='field__label text-conceptColor'>배송 유의사항</span>")
		    							)
		    						)
		    					)
		    				).append(
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
							$("<div class='col-md-5' style = 'display : none; overflow : auto; max-height: 350px;'>").attr("id", "list"+dataIndex)
							.append(
								$("<div class = 'row centerBox' style = 'font-size: 1.2em;'>")
								.append(
									$("<div class = 'col-md-4'>")
									.append(
										$("<div class = 'text-conceptColor'> 배송 거리 </div>")
									).append(
										$("<b class = 'text-linkColor'>")
										.append(
											$("<div>").attr("id", "distance" + dataIndex)
										)
									)
								).append(
									$("<div class = 'col-md-5'>")
									.append(
										$("<div class = 'text-conceptColor'> 거리 요금 </div>")
									).append(
										$("<b class = 'text-dangerColor'>")
										.append(
											$("<div>").attr("id", "distancePay" + dataIndex)
										)
									)	
								)
    						).append(
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
										$("<img src = <%= request.getContextPath() %>/image/quickApply_img/plusBtn.png width = 20 height = 20/>").attr("id", "saveFreight"+dataIndex)
									)
   								)
	    				).append(
							$("<table class='table table-bordered'>").attr("id", "table"+dataIndex)
							.append(
								$("<tbody>")
							)
						).append(
							$("<div class = 'centerBox' style = 'font-size : 1.2em;'>")
							.append(
								$("<span class = 'text-conceptColor'>").text("화물 요금 : ")
							).append(
								$("<b>")
								.append(
									$("<span class = 'text-dangerColor'>").attr("id", "freightPrice"+dataIndex)
								)
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
			var address = "#address" + dataIndex;
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
			var quickMemo = "#quickMemo"+dataIndex;
			var distance = "#distance"+dataIndex;
			var distancePay = "#distancePay"+dataIndex;
			var saveFreight;
			var freightPrice = "#freightPrice" + dataIndex;
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
			
	  		var realId;
	  		$(saveOrder).on("click", function() {
	  			realId = $(this).attr("id").substring(9);
				/* 비동기통신. 오더 저장 */
				$.ajax({
					url : "<%= request.getContextPath() %>/call/addOrder.do",
	   				data : {
	   					receiverName : $(userNameApply).val(),
	   					receiverAddress : $(address).val(),
	   					receiverAddressDetail : $(addressDetail).val(),
	   					receiverPhone : $(phone).val(),
	   					memo : $(quickMemo).val()
	   				},
	   				dataType : "json",
	   				method : "POST",
	   				success : addOrders
				});
		
				
	  		});
	  		
			var $selectId;
			var $countId;
			var tableIndex;
			var freightType;
			var item;
	  		function addOrders(JSONDocument) {
	  			var result = confirm("도착지 정보가 저장되었습니다. 즐겨찾기에 저장할까요?");
	  			if (result) {
	  				$.ajax({
		  				url : "<%= request.getContextPath() %>/call/saveFavorite.do",
		   				data : {
		   					addressType : 3,
		   					address : $(address).val(),
		   					addrDetail : $(addressDetail).val(),
		   					receiverName : $(userNameApply).val(),
		   					receiverPhone : $(phone).val()
		   				},
		   				dataType : "json",
		   				method : "POST",
		   				success : addFavorites
	  				});
	  			}
	  			callNum = JSONDocument.callNum;
	  			console.log(callNum);
				$(list).slideDown(2000);
				$(distance).text(JSONDocument.distance + "KM");
				$(saveOrder).attr("disabled", "disabled");
				$(saveText).text(" 도착지 정보 저장됨");
				$(saveIcon).removeClass("far").addClass("fas");
				saveFreight = "#saveFreight"+realId;
				totalPrice = totalPrice + JSONDocument.orderPrice;
				$(distancePay).text(JSONDocument.orderPrice + "원");
				$(saveFreight).on("click", function() {
					$selectId = "#item"+realId;
					$countId = "#count"+realId;
					tableIndex = "table#table"+realId+" > tbody";
					freight_type = $($selectId).val();
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
		   				success : addFreights
					});
				});
	  		}
	  		
	  		function addFavorites(JSONDocument) {
	  			if(JSONDocument) {
	  	   			$(saveFavorite).attr("checked", "checked").attr("disabled", "disabled");
	  	   		}	
	  		}
	  		
  			var trId;
  			var trRemove;
	  		function addFreights (JSONDocument) {
	  			/* 옆에 리스트에 추가 */
	  			alert("물품이 추가되었습니다. 이제 결제하실 수 있습니다. ");
	  			totalPrice += JSONDocument.freightPrice;
	  			$(freightPrice).text(Number($(freightPrice).text())+Number(JSONDocument.freightPrice));
				$(tableIndex).append(
					$("<tr>").attr("id", "tr"+trIndex)
					.append(
							$("<td>").text(item)
					).append(
							$("<td>").text($($countId).val()+"개")
					).append(
							$("<td>").text(JSONDocument.freightPrice+"원")
					).append(
							$("<td>").append(
								$("<img src = <%=request.getContextPath()%>/image/quickApply_img/minusBtn.png width=20 height=20 />").attr("id", "minusBtn"+trIndex).on("click", function() {
									trId = $(this).attr("id").substring(8);
									trRemove = "#tr"+trId;
									/* 화물 삭제 비동기통신 */
									$.ajax({
										url : "<%= request.getContextPath() %>/call/getFreight.do",
						   				data : {
						   					freightNum : JSONDocument.freightNum
						   				},
						   				dataType : "json",
						   				method : "POST",
						   				success : getFreight
									});
				
								})
							)
					)
				)
				trIndex++;
	  		}
	  		
	  		function getFreight(JSONDocument) {
	  			totalPrice -= JSONDocument.freightPrice;
	  			$(freightPrice).text(Number($(freightPrice).text())-Number(JSONDocument.freightPrice));
	  			var result = confirm("해당 화물을 삭제하시겠습니까?");
	  			if(result) {
	  				$.ajax({
	  					url : "<%= request.getContextPath() %>/call/delFreight.do",
		   				data : {
		   					freightNum : JSONDocument.freightNum
		   				},
		   				dataType : "json",
		   				method : "POST",
		   				success : delFreights
	  				});
					$(trRemove).remove();
	  			} else {
	  				console.log("삭제 취소");
	  			}
	  			
	  		}
	  		
	  		function delFreights(JSONDocument) {
	  			if(JSONDocument) {
	  				console.log("삭제 완료");
	  				
	  			}
	  		}
	  		
	  		
	  		$(saveFavorite).on("click", function() {
	  			console.log(this.id + "번째가 즐겨찾기에 저장됨");
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
				goPopup(this);
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
  		

  		
  		
  		/* 첫번째 오더의 주문 저장. */
    	$("#saveOrder0").on("click", function() {

			/* 오더 저장 비동기통신 */
    		$.ajax({
   				url : "<%= request.getContextPath() %>/call/addOrder.do",
   				data : {
   					receiverName : $("#userNameApply0").val(),
   					receiverAddress : $("#address0").val(),
   					receiverAddressDetail : $("#addressDetail0").val(),
   					receiverPhone : $("#phone0").val(),
   					memo : $("#quickMemo0").val()
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
  			var result = confirm("도착지 정보가 저장되었습니다. 즐겨찾기에 저장할까요?");
  			if (result) {
  				$.ajax({
	  				url : "<%= request.getContextPath() %>/call/saveFavorite.do",
	   				data : {
	   					addressType : 3,
	   					address : $("#address0").val(),
	   					addrDetail : $("#addressDetail0").val(),
	   					receiverName : $("#userNameApply0").val(),
	   					receiverPhone : $("#phone0").val()
	   				},
	   				dataType : "json",
	   				method : "POST",
	   				success : addFavorite
  				});
  			}
  			callNum = JSONDocument.callNum;
  			console.log(callNum);
  			$("#distance0").text(JSONDocument.distance + "KM");
			$("#list0").slideDown(2000);
			$("#saveOrder0").attr("disabled", "disabled");
			$("#saveIcon0").removeClass("far").addClass("fas");
			$("#saveText0").text(" 도착지 정보 저장됨");
			
			totalPrice = totalPrice + JSONDocument.orderPrice;
			
			$("#distancePay0").text(JSONDocument.orderPrice + "원");
			
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
				
				/* 화물 추가 비동기통신 */
				$.ajax({
	   				url : "<%= request.getContextPath() %>/call/addFreight.do",
	   				data : {
	   					orderNum : JSONDocument.orderNum,
	   					freightType : freightType,
	   					freightQuant : $($countId).val(),
	   					freightDetail : $("#quickMemo0").val()
	   				},
	   				dataType : "json",
	   				method : "POST",
	   				success : addFreight
					
				});
			});
  		};
  		
  		function addFreight(JSONDocument) {
  			$("#orderNow").attr("data-toggle", "modal").attr("data-target", "#payBox");
  			alert("물품이 추가되었습니다. 이제 결제하실 수 있습니다. ");
  			totalPrice += JSONDocument.freightPrice;
  			$("#freightPrice0").text(Number($("#freightPrice0").text())+Number(JSONDocument.freightPrice));
			$(tableIndex).append(
				$("<tr>").attr("id", "tr"+trIndex)
				.append(
					$("<td>").text(item)
				).append(
					$("<td>").text($($countId).val()+"개")
				).append(
					$("<td>").text(JSONDocument.freightPrice+"원")
				).append(
					$("<td>").append(
						$("<img src = <%=request.getContextPath()%>/image/quickApply_img/minusBtn.png width=20 height=20 />").attr("id", "minusBtn"+trIndex).on("click", function() {
							trId = $(this).attr("id").substring(8);
							trRemove = "#tr"+trId;
							/* 화물 삭제 비동기통신 */
							$.ajax({
								url : "<%= request.getContextPath() %>/call/getFreight.do",
				   				data : {
				   					freightNum : JSONDocument.freightNum
				   				},
				   				dataType : "json",
				   				method : "POST",
				   				success : getFreight
							});
						})
					)
				)
			)
			trIndex++;
  		}  		
  		
  		function getFreight(JSONDocument) {
  			totalPrice -= JSONDocument.freightPrice;
  			$("#freightPrice0").text(Number($("#freightPrice0").text())-Number(JSONDocument.freightPrice));
  			var result = confirm("해당 화물을 삭제하시겠습니까?");
  			if(result) {
  				$.ajax({
  					url : "<%= request.getContextPath() %>/call/delFreight.do",
	   				data : {
	   					freightNum : JSONDocument.freightNum
	   				},
	   				dataType : "json",
	   				method : "POST",
	   				success : delFreight
  				});
				$(trRemove).remove();
  			} else {
  				console.log("삭제 취소");
  			}
  		}
  		
  		function delFreight(JSONDocument) {
  			console.log(JSONDocument);
  			console.log("삭제 완료");
  		}
  		
  		

  		
  		
  		
  		
   		$('#deleteOrder0').popover();
   		
    	$("#deleteOrder0").mouseenter(function() {
    		$('#deleteOrder0').trigger("click");
    	});
    	
    	$("#deleteOrder0").mouseout(function() {
    		$('#deleteOrder0').trigger("click");
    	});
    	
    	
    	
    	$("#orderNow").on("click", function() {
    		$("#payment").text(totalPrice);
    	});
    	/* 주소 입력 API! */
    	
    	/* 계좌이체, 카드결제(웹) */
    	$(".goToPay").on("click", function() {
    		IMP.init('imp94690506');
    		IMP.request_pay({
    		    pg : 'inicis', // version 1.1.0부터 지원.
    		    pay_method : 'card',
    		    merchant_uid : 'merchant_' + new Date().getTime(),
    		    name : 'NexQuick 퀵 결제',
    		    amount : $("#payment").text(),
    		    buyer_email : 'mmk8292@naver.com',
    		    buyer_name : '김민규',
    		    buyer_tel : '010-4940-8292',
    		    buyer_addr : '서울특별시 강남구 강남강남',
    		    buyer_postcode : '123-456'
    		}, function(rsp) {
    		    if ( rsp.success ) {
    		        var msg = '결제가 완료되었습니다.';
    		        msg += '고유ID : ' + rsp.imp_uid;
    		        msg += '상점 거래ID : ' + rsp.merchant_uid;
    		        msg += '결제 금액 : ' + rsp.paid_amount;
    		        msg += '카드 승인번호 : ' + rsp.apply_num;
	    		    alert(msg);
	    		   	location.href = "<%=request.getContextPath()%>/call/getOrders.do?callNum="+callNum + "&payType=0&payStatus=1";
    		    } else {
    		        var msg = '결제에 실패하였습니다.';
    		        msg += '에러내용 : ' + rsp.error_msg;
	    		    alert(msg);
    		    }
    		});
    	});
		
    	$("#place_first_card").on("click", function() {
    		location.href = "<%= request.getContextPath() %>/call/getOrders.do?callNum="+callNum + "&payType=2&payStatus=0";
    	});
    	$("#place_first_money").on("click", function() {
    		location.href = "<%= request.getContextPath() %>/call/getOrders.do?callNum="+callNum + "&payType=3&payStatus=0";
    	});
    	$("#place_last_card").on("click", function() {
    		location.href = "<%= request.getContextPath() %>/call/getOrders.do?callNum="+callNum + "&payType=4&payStatus=0";
    	});
    	$("#place_last_money").on("click", function() {
    		location.href = "<%= request.getContextPath() %>/call/getOrders.do?callNum="+callNum + "&payType=5&payStatus=0";
    	});
    	
    	<% if(csInfo.getCsType() == 1) { %>
	    	$("#company_pay").on("click", function() {
	    		location.href = "<%= request.getContextPath() %>/call/getOrders.do?callNum="+callNum + "&payType=6&payStatus=0";
	    	});
    	<% } else { %>
    		$("#company_pay").attr("data-container", "body").attr("data-toggle", "popover").attr("data-placement", "bottom").attr("data-content", "법인회원 전용입니다.").popover('show');
    	<% } %>
    	
    	$("#placeholderImgText").popover('show');
    	
    	
	});
	
	
	var btnId;
	var btn_real;
   	function goPopup(buttonId){
   		// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
   	    var pop = window.open("<%=request.getContextPath()%>/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes");
   		btnId = $(buttonId).attr("id");
   		btn_real = btnId.substring(7);
   	}
   	/** API 서비스 제공항목 확대 (2017.02) **/
   	function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn
   							, detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo){
   		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
   		var address = "#address" + btn_real;
   		var addressDetail = "#addressDetail" + btn_real;
   		$(address).val(roadAddrPart1);
   		$(addressDetail).val(addrDetail);
   	} 

   	function addFavorite(JSONDocument) {
   		if(JSONDocument) {
   			$("#saveFavorite0").attr("checked", "checked").attr("disabled", "disabled");
   		}	
   	}
   	
	/* 콜정보 받아와서 모달에 데이터 입력 */
	function callInfomation(JSONDocument) {
		if(JSONDocument.series == 0) { /* 그룹배송이 아닐때 */
			$("#addOrderBtn").css("display", "none");
		}
		$("#placeholderImg")
		.attr("data-toggle", "modal")
		.attr("data-target", "#callInfoBox");
		
		$("#placeholderImgText")
		.attr("data-container", "body")
		.attr("data-toggle", "popover")
		.attr("data-placement", "bottom")
		.attr("data-content", "그림 클릭 시 기본정보를 확인할 수 있습니다.");
		
		
		
		 
		$("#placeholderImgText").popover('show');
		setTimeout(function() {
	        $('#placeholderImgText').popover('hide');
	    }, 2000);
		
		$("#callInfo1").val(JSONDocument.senderAddress);
		$("#callInfo2").val(JSONDocument.senderName);
		$("#callInfo3").val(JSONDocument.senderPhone);
		if(JSONDocument.urgent == 1) {
			$("#urgentBox").attr("checked", "checked");
			isUrgent = 1.5;
		}
		if(JSONDocument.series == 1) {
			$("#groupDelivery").attr("checked", "checked");
		}
		if(JSONDocument.reserved == 1) {
			$("#reserveDelivery").attr("checked", "checked");
			$("#timeInputBox").slideDown();
			$("#callInfo4").val(JSONDocument.reservationTime);
		}
		
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
	}

	var saveBtnId;
	function getSaveId(saveId) {
  		$("#favList").modal("show");
  		saveBtnId = saveId.id.substring(12);
  		$.ajax({
  			url : "<%= request.getContextPath() %>/call/getFavorite.do",
			dataType : "json",
			method : "POST",
			success : getFavList
  		});
  		
	}
	
	function getFavList(JSONDocument) {
		$("#favBox").empty();
		var Index = 0;
		for(var i in JSONDocument) {
			$("#favBox").append(
				$("<tr>").append(
					$("<td>").attr("id", "tdAddress" + Index).text(JSONDocument[i].address)
				).append(
					$("<td>").attr("id", "tdAddressDetail" + Index).text(JSONDocument[i].addrDetail)
				).append(
					$("<td>").attr("id", "tdName" + Index).text(JSONDocument[i].receiverName)
				).append(
					$("<td>").attr("id", "tdPhone" + Index).text(JSONDocument[i].receiverPhone)
				).append(
					$("<td>")
					.append(
						$("<button class = 'favBtn ColorBorder'>바로 입력하기</button>").attr("id", "favBtn"+Index)
					)
				)
			);
			Index++;
		}
		
		
		$(".favBtn").on("click", function() {
  			var favListId = this.id.substring(6);
  			var userName = "#userNameApply"+saveBtnId;
  			var userPhone = "#phone"+saveBtnId;
  			var userAddress = "#address"+saveBtnId;
  			var userAddrDetail = "#addressDetail"+saveBtnId;
  			var tdAddress = "#tdAddress"+favListId;
  			var tdAddressDetail = "#tdAddressDetail"+favListId;
  			var tdName = "#tdName"+favListId;
  			var tdPhone = "#tdPhone"+favListId;
  			
  			$(userName).val($(tdName).text());
  			$(userAddress).val($(tdAddress).text());
  			$(userAddrDetail).val($(tdAddressDetail).text());
  			$(userPhone).val($(tdPhone).text());
  			console.log("zz");
  		});
		
		
	} 
	

	
	
/* 	
	function insertAddress(btnId) {
		console.log(btnId.id);
		var Id = btnId.id.substring(6);
		$("#userNameApply0").val(),
		$("#address0").val() + " " + $("#addressDetail0").val(),
		$("#phone0").val(),
		$("#quickMemo0").val()
	}
	
 */
</script>



</head>
<body>

<%@ include file = "../quickApplyPage/quickApply_going_box.jsp" %>


<div class= "row mt-5">
	<div class = "col-md-12">
		<div class = "row" style = "width: 100%;">
			<div class = "col-md-7">
				<h2 class = "centerBox quickFirstTitle mb-5 text-conceptColor">
					도착지 정보
				</h2>
			</div>
			<div class = "col-md-5">
				<h2 class = "centerBox quickFirstTitle mb-5 text-conceptColor mr-3">
					물품 정보
					<button class = "ColorBorder ml-3" id = "gotoPayTable" data-toggle = "modal" data-target = "#payTable">요금표</button>
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
				  					<button type = "button" id = "callFavorite0" onclick = "getSaveId(this)" class = "ColorBorder"><i class="fas fa-download"></i>&nbsp;불러오기</button>
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
								     <input type="text" class="orderNumberForm field__input text-conceptColor" placeholder="도착정보를 저장하세요." name = "callId" id="callId0" readonly>
								     <span class="field__label-wrap">
								     	<span class="field__label text-conceptColor">저장 시 생성</span>
								     </span>
								 </label>   
				  			</span>
				  		</div>
				  		
						<div class = "centerBox mt-0">
							<img class = "mr-3" src = "<%= request.getContextPath() %>/image/quickApply_img/map.png" width = "40" height = "40" />
							<label class="field field_animated field_a2 page__field add">
						    	<input type="text" class="addressForm field__input text-conceptColor" placeholder="클릭해서 주소를 검색하세요." name = "address" id="address0" onclick = "goPopup(this);" readonly>
						    	<span class="field__label-wrap">
						    		<span class="field__label text-conceptColor">주소</span>
						    	</span>
						    </label>   
							
						</div>
						<div class = "centerBox">
							<img class = "mr-3" src = "<%= request.getContextPath() %>/image/quickApply_img/addressDetail.png" width = "40" height = "40" />
							
							<label class="field field_animated field_a2 page__field addD">
						    	<input type="text" class="addressForm field__input text-conceptColor" placeholder="ex) 멀티캠퍼스 9층 904호" name = "addressDetail" id="addressDetail0">
						    	<span class="field__label-wrap">
						    		<span class="field__label text-conceptColor">상세주소</span>
						    	</span>
						    </label>   
							
						</div>
						<!-- 이름, 연락처 -->
						<div class = "centerBox">
							<img class = "mr-3" src = "<%= request.getContextPath() %>/image/quickApply_img/name.png" width = "40" height = "40" />
							<label class="field field_animated field_a2 page__field name">
						      <input type="text" class="addressFormSub field__input text-conceptColor" placeholder="ex) 김민규" name = "userNameApply" id="userNameApply0">
						      <span class="field__label-wrap">
						        <span class="field__label text-conceptColor">수령인</span>
						      </span>
						    </label>   
						    
							<img class = "mr-3" src = "<%= request.getContextPath() %>/image/quickApply_img/phone.png" width = "40" height = "40" style = "margin-left : 1.5em;"/>
							<label class="field field_animated field_a2 page__field phone">
						      <input type="text" class="addressFormSub field__input text-conceptColor" placeholder="ex) 01049408292" name = "phone" id="phone0">
						      <span class="field__label-wrap">
						        <span class="field__label text-conceptColor">수령인 연락처</span>
						      </span>
						    </label>   
						</div>
						
						<div class = "centerBox">
							<label class="field field_animated field_a2 page__field textArea">
						      <textarea type="text" class="textAreaForm field__input text-conceptColor" placeholder="ex) 부재시 경비실에 맡겨주세요" name = "quickMemo" id="quickMemo0"></textarea>
						      <span class="field__label-wrap">
						        <span class="field__label text-conceptColor">배송 유의사항</span>
						      </span>
						    </label>   
						</div>
						
						
						<div class = "centerBox" style = "width: 100%; height: 100px;">
							<button class = "dangerBorder mt-5" id = "deleteOrder0" type = "button" data-container="body" data-toggle="popover" data-placement="bottom" data-content="첫번째 주문 정보는 삭제가 불가합니다." readonly><i class="far fa-times-circle"></i>&nbsp;도착지 삭제하기</button>
							<button class = "ColorBorder mt-5" id = "saveOrder0" type = "button" disabled = "disabled"><i class="far fa-save" id = "saveIcon0"></i><span id = "saveText0">&nbsp;도착지 저장하기</span></button>
						</div>
					</div>
					
						<!-- 리스트 목록 나옴 -->
						<div class = "col-md-5" style = "display : none; overflow : auto; max-height: 350px;" id = "list0">
							<div class = "row centerBox" style = "font-size: 1.2em;">
								<div class = "col-md-4">
									<div class = "text-conceptColor"> 배송 거리 </div>
									<b class = "text-linkColor"><span id = "distance0"></span></b>
								</div>
								<div class = "col-md-5">
									<div class = "text-conceptColor"> 거리 요금 </div>
									<b class = "text-dangerColor"><div id="distancePay0"></div></b>
								</div>
							</div>
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
							
							<div class = "centerBox" style = "font-size: 1.2em;">
								<span class = "text-conceptColor">화물 요금 : </span>
								<b><span class = "text-dangerColor" id = "freightPrice0"></span></b>
							</div>
							
							
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
	
	
</div>

<%@ include file = "../footer.jsp" %>




<div class="modal fade bd-example-modal-lg" id = "payBox" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h2 class="modal-title centerBox" id="exampleModalCenterTitle">결제할 금액 : <span id = "payment"></span></h2>
      </div>
      <div class="modal-body">
		<div class = "text-conceptColor centerBox">결제 방법을 선택하세요.</div>
		<!-- 결제버튼 -->
<section class = "mt-5 section_s">
  <div class="container-fluid">
    <div >
      <div class="row">
      <!-- 첫번째 -->
        <div class="col-sm-4">
          <div class="card_s text-center" id = "payMethod_1">
            <div class="title_s">
              <i class="fas fa-globe fa_s" aria-hidden="true"></i>
              <h2 class = "mt-5">웹 결제</h2>
            </div>
            <div class = "emptyBox_s mt-3">
            </div>
            <div class = "row">
            	<div class = "col-md-6">
		            <button type = "button" id = "web_card" class = "borderColor payBtn goToPay"><i class="fa_s far fa-credit-card"></i> 카드</button>
            	</div>
            	<div class = "col-md-6">
		            <button type = "button" id = "web_sendMoney" class = "borderColor payBtn goToPay"><i class="fa_s xi-bank"></i> 입금</button>
            	</div>
            </div>
          </div>
        </div>
        <!-- END Col one -->
        <div class="col-sm-4">
          <div class="card_s text-center" id = "payMethod_2">
            <div class="title_s">
              <i class="far fa-handshake fa_s" aria-hidden="true"></i>
              <h2 class = "mt-5">현장결제(선불)</h2>
            </div>
            <div class = "emptyBox_s mt-3">
            </div>
            <div class = "row">
            	<div class = "col-md-6">
		            <button type = "button" id = "place_first_card" class = "borderColor payBtn"><i class="fa_s far fa-credit-card"></i> 카드</button>
            	</div>
            	<div class = "col-md-6">
		            <button type = "button" id = "place_first_money" class = "borderColor payBtn"><i class="fa_s fas fa-hand-holding-usd"></i> 현금</button>
            	</div>
            </div>
          </div>
        </div>
        <!-- END Col two -->
       <div class="col-sm-4">
          <div class="card_s text-center" id = "payMethod_3">
            <div class="title_s">
              <i class="far fa-handshake fa_s" aria-hidden="true"></i>
              <h2 class = "mt-5">현장결제(착불)</h2>
            </div>
            <div class = "emptyBox_s mt-3">
            	
            </div>
            <div class = "row">
            	<div class = "col-md-6">
		            <button type = "button" id = "place_last_card" class = "borderColor payBtn"><i class="fa_s far fa-credit-card"></i> 카드</button>
            	</div>
            	<div class = "col-md-6">
		            <button type = "button" id = "place_last_money" class = "borderColor payBtn"><i class="fa_s fas fa-hand-holding-usd"></i> 현금</button>
            	</div>
            </div>
          </div>
        </div>
        </div>
        <!-- END Col three -->
        <div class = "row mt-5">
     		<div class="col-md-12">
     		
     		
          		<div class="card_s text-center" id = "payMethod_4">
          		
          			<div class = "row">
	          			<div class = "col-md-6 vertical_center">
			            	<div class="title_s">
			              		<i class="fas fa-building fa_ss" aria-hidden="true"></i>
			            	</div>
	          			</div>
	            		<div class = "col-md-6">
		              		<h2 class = "mt-5">신용결제(법인회원 전용)</h2>
				            <div class = "emptyBox_s mt-3">
				            </div>
				           <button type = "button" id = "company_pay" class = "borderColor payBtn"><i class="fa_s far fa-credit-card"></i> 결제목록에 추가</button>
				          </div>
	            		</div>
          			</div>
		        </div>
		      </div>
		    </div>
		  </div>
		</section>
			
      </div>
      <div class="modal-footer centerBox">
        <button type="button" class="dangerBorder" data-dismiss="modal">취소</button>
      </div>
    </div>
  </div>
</div>




<!-- 방금 신청한 퀵 정보 보여줄 모달 -->
<div class="modal fade bd-example-modal-lg" id = "callInfoBox" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h2 class="modal-title centerBox" id="exampleModalCenterTitle">출발지 정보</h2>
      </div>
      <div class="modal-body mt-5">
		
		<!-- 출발지 -->
		<div class="group">      
	      <input class = "inputDesign" type="text" id = "callInfo1" disabled>
	      <span class="highlight"></span>
	      <span class="bar"></span>
	      <label class = "labelDesign"><i class = "fas fa-street-view"></i> 출발지</label>
	    </div>

		<!-- 발송인  -->
		<div class = "row">
			<div class = "col-md-6">
				<div class="group">      
			      <input class = "inputDesign" type="text" id = "callInfo2" disabled>
			      <span class="highlight"></span>
			      <span class="bar"></span>
			      <label class = "labelDesign"><i class = "far fa-user"></i> 발송인</label>
			    </div>
			</div>
			
			<!-- 연락처 -->
			<div class = "col-md-6">
				<div class="group">      
			      <input class = "inputDesign" type="text" id = "callInfo3" disabled>
			      <span class="highlight"></span>
			      <span class="bar"></span>
			      <label class = "labelDesign"><i class = "fas fa-phone"></i> 발송인 연락처</label>
			    </div>
			</div>			
		</div>


		<div class = "urgentBox">
			<label class="label font_1em paddingZero">
			    <input  class="label__checkbox" type="checkbox" id = "urgentBox" disabled />
			    <span class="label__text ">
			      <span class="label__check">
			        <i class="fa fa-check icon font_1em"></i>
			      </span>
			    </span>
		  	</label>
		  	<label for="urgentBox" style = "line-height: 28px; color: #34495e">긴급배송</label>
		  	
		  	<label class="label font_1em paddingZero">
			    <input  class="label__checkbox" type="checkbox" id = "groupDelivery" disabled/>
			    <span class="label__text">
			      <span class="label__check ml-5">
			        <i class="fa fa-check icon font_1em"></i>
			      </span>
			    </span>
		  	</label>
		  	<label for="groupDelivery" style = "line-height: 28px; color: #34495e">일괄배송</label>
		  	
		  	<label class="label font_1em paddingZero">
			    <input  class="label__checkbox" type="checkbox" id = "reserveDelivery" disabled/>
			    <span class="label__text">
			      <span class="label__check ml-5">
			        <i class="fa fa-check icon font_1em"></i>
			      </span>
			    </span>
		  	</label>
		  	<label for="reserveDelivery" style = "line-height: 28px; color: #34495e">예약배송</label>
					  
		</div>			  	
		  	
		<div class="group" id = "timeInputBox" style = "display : none;">      
	      <input class = "inputDesign" type="text" id = "callInfo4" disabled>
	      <span class="highlight"></span>
	      <span class="bar"></span>
	      <label class = "labelDesign"><i class = "fas fa-street-view"></i> 예약배송 시간 </label>
   		</div>

<!-- 배송수단 -->
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
      		  	<input type="radio" id="motorcycle" name="radio-group" value="1" disabled>
			    <label for="motorcycle">오토바이</label>
      		</div>
      		<div class = "col-md-3 centerBox">
      		 	<input type="radio" id="damas" name="radio-group" value="2" disabled>
			    <label for="damas">다마스</label>
      		</div>
      		<div class = "col-md-3 centerBox">
      		  	<input type="radio" id="labo" name="radio-group" value="3" disabled>
			    <label for="labo">라보</label>
      		</div>
      		<div class = "col-md-3 centerBox">
      		  	<input type="radio" id="truck" name="radio-group" value="4" disabled>
			    <label for="truck">트럭</label>
      		</div>
      	</div>
			
      </div> <!-- 모달끝 -->
      <div class="modal-footer centerBox">
        <button type="button" class="ColorBorder" data-dismiss="modal">확인</button>
      </div>
    </div>
  </div>
</div>



<!-- 요금표 보여줄 모달 -->
<div class="modal fade bd-example-modal-lg" id = "payTable" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h2 class="modal-title centerBox" id="exampleModalCenterTitle">요금표</h2>
      </div>
      <div class="modal-body">
      	
      	
<table class="table table1000 table-bordered">
  <thead class="thead-light">
    <tr>
      <th class = "centerBox" scope="col">물품</th>
      <th class = "centerBox" scope="col">서류</th>
      <th class = "centerBox" scope="col">박스(소)</th>
      <th class = "centerBox" scope="col">박스(중)</th>
      <th class = "centerBox" scope="col">박스(대)</th>
      <th class = "centerBox" scope="col">음식물</th>
    </tr>
  </thead>
  <tbody class = "centerBox">
    <tr>
      <td>기본 개수</td>
      <td>10개</td>
      <td>5개</td>
      <td>2개</td>
      <td>1개</td>
      <td>1개</td>
    </tr>
    <tr>
      <td>기본 요금</td>
      <td colspan = "5" class = "centerBox">무료</td>
    </tr>
    <tr>
      <td>추가 요금</td>
      <td>500원/개</td>
      <td>1500원/개</td>
      <td>3000원/개</td>
      <td>4500원/개</td>
      <td>5000원/개</td>
    </tr>
  </tbody>
</table>



	 <div class = "text-paySize">① 총 요금(<b class = "text-linkColor">거리 30KM 이하</b>) : <b class = "text-conceptColor">3000원(기본 거리요금)</b> + <b class = "text-dangerColor">추가 물품요금</b></div>
	 <div class = "text-paySize">② 총 요금(<b class = "text-dangerColor">거리 30KM 초과</b>) : <b class = "text-linkColor">((총 거리 - 기본 거리)/10) ^ 2 * 500 원</b> + <b class = "text-conceptColor">3000원(기본 거리요금)</b> + <b class = "text-dangerColor">추가 물품요금</b></div>
	 <div class = "text-paySize mt-3 ml-5">ex) 50KM, 박스(대) 3개 신청</div>
	 <div class = "text-paySize mt-2 ml-5">= <b class = "text-linkColor">((50 - 30)/10)^2 * 500</b> + <b class = "text-conceptColor">3000</b> + <b class = "text-dangerColor">(3-1) * 4500원</b> </div>
	 <div class = "text-paySize mt-2 ml-5">= <b class = "text-linkColor">2 ^ 2 * 500</b> + <b class = "text-conceptColor">3000</b> + <b class = "text-dangerColor">2 * 4500원</b></div>
	 <div class = "text-paySize mt-2 ml-5">= <b class = "text-conceptColor">14000원</b></div>
      	
      </div>
      <div class="modal-footer centerBox">
        <button type="button" class="ColorBorder" data-dismiss="modal">확인</button>
      </div>
    </div>
  </div>
</div>






<!-- 즐겨찾기 목록 보여줄 모달 -->
<div class="modal fade bd-example-modal-lg" id = "favList" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h2 class="modal-title centerBox" id="exampleModalCenterTitle">즐겨찾기 목록</h2>
      </div>
      <div class="modal-body" style = "max-height: 350px; overflow: auto;">
   
   
   
         
<table class="table table1000 table-bordered">
  <thead class="thead-light">
    <tr>
      <th class = "centerBox" scope="col">주소</th>
      <th class = "centerBox" scope="col">상세주소</th>
      <th class = "centerBox" scope="col">수령인</th>
      <th class = "centerBox" scope="col">수령인 연락처</th>
      <th class = "centerBox" scope="col"></th>
    </tr>
  </thead>
  <tbody class = "centerBox" id = "favBox">

   
  </tbody>
</table>
      
      	
      	
	
      </div>
      <div class="modal-footer centerBox">
        <button type="button" class="ColorBorder" data-dismiss="modal">확인</button>
      </div>
    </div>
  </div>
</div>
















</body>
</html>