<%@ page import = "com.nexquick.model.vo.OnDelivery" %>
<%@ page import = "java.util.*" %>
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
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>
<title>NexQuick :: 진행중인 퀵</title>

<script type="text/javascript">
var senderAddressArr = new Array();
var receiverAddressArr = new Array();
var qpAddress_lat = new Array();
var qpAddress_lon = new Array();
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

				} else if (!JSONDocument) {
					alert("로그아웃 되었습니다. 다시 로그인 해주세요.");
					location.replace("<%= request.getContextPath() %>/index.jsp");
				}
			}
		})
	}, 5000);
	
	
	
	$.ajax({
		url : "<%= request.getContextPath() %>/list/userCallList.do",
		dataType : "json",
		method : "POST",
		async : false,
		success : setCallList
	});
	

	$(".jconfirm-box-container").css("margin-left", "auto").css("margin-right", "auto");
	
	
});

function setCallList(JSONDocument) {
	$("#tableBody").empty();
	var deliveryStatus;
	

	var count = 0;
	var classNameClone;
	for(var i in JSONDocument) {
		senderAddressArr.push(JSONDocument[i].senderAddress);
		receiverAddressArr.push(JSONDocument[i].receiverAddress);
		var className = ".cNum" + JSONDocument[i].callNum;
		classNameClone = className;
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
		default:
			deliveryStatus = "배차실패";
			break;
		}
		$("#tableBody").append(
			$("<tr class='row100 body callListTr'>")
			.append(
				$("<td class='cell100 column1 centerBox makeMarker'>").addClass("callNum" + JSONDocument[i].callNum).attr("id", "tdId"+count).text(JSONDocument[i].orderNum).attr("onclick", "findAddress(this)").css("cursor", "pointer").css("color", "#55B296").css("text-decoration", "underline")
			).append(
				$("<td class='cell100 column2 c2 centerBox'>").text(JSONDocument[i].callTime)
			).append(
				$("<td class='cell100 column3 centerBox'>").text(JSONDocument[i].receiverAddress + " " + JSONDocument[i].receiverAddressDetail)
			).append(
				$("<td class='cell100 column4 centerBox'>").text("물품들")
			).append(
				$("<td class='cell100 column5 centerBox deliveryStatus'>").text(deliveryStatus).attr("id", "deliveryStatus"+JSONDocument[i].callNum)
			).append(
				$("<td class='cell100 column6 centerBox'>").addClass("cNum" + JSONDocument[i].callNum)
			)
		)
		
		$.ajax({
			url : "<%= request.getContextPath() %>/call/getQPInfo.do",
			dataType : "json",
			method : "POST",
			async : false,
			data : {
				callNum : JSONDocument[i].callNum
			},
			success : function(resultObj) {
				$(className).text(resultObj.qpName);
				$.ajax({
					url : "<%= request.getContextPath() %>/qpAccount/getQPPosition.do",
					dataType : "json",
					method : "POST",
					async : false,
					data : {
						qpId : resultObj.qpId
					},
					success : saveQPPosition
				});
			}, error : saveQPPosition
		});
		
		
		count++;
		
	}
	
}

function saveQPPosition(JSONDocument) {
	
	if(typeof(JSONDocument.qpLatitude) == 'undefined') {
		qpAddress_lat.push(0);
		qpAddress_lon.push(0);
	} else {
		qpAddress_lat.push(JSONDocument.qpLatitude);
		qpAddress_lon.push(JSONDocument.qpLongitude);
	}
	
	$(".callListTr > td").each(function() {
		if($(this).text() == '배차실패') {
		var id = $(this).attr("id").substring(14);
			$(this).css("color", "#F43F22").css("text-decoration", "underline");
			$(this).off("click");
			$(this).on("click", function() {
				var result = $.confirm({
				    title: '신청번호 ' + id +'번 배차 실패',
				    content: '다시 배차 요청하시겠습니까?',
				    closeIcon: true,
				    theme: 'modern',
				    type: 'red',
				    columnClass: 'centerBox',

				    buttons: {
				        "배차 요청": {
				        	btnClass: 'btn-blue',
				        	action : function () {
					        	$.ajax({
					        		url : "<%= request.getContextPath() %>/call/reRegistCall.do",
									dataType : "json",
									method : "POST",
									async : false,
									data : {
										callNum : id
									},
									success : function() {
										console.log(id + " 재신청 완료");
										location.reload();
									},
									error : function() {
										console.log(id + " 재신청에 실패");
									}
					        	});
				      		}
				        },
				        "신청 삭제": {
				        	
				        	btnClass: 'btn-red',
					        action: function () {
					        	$.ajax({
					        		url : "<%= request.getContextPath() %>/call/cancelCall.do",
									dataType : "json",
									method : "POST",
									async : false,
									data : {
										callNum : id
									},
									success : function() {
										console.log(id + " 삭제 완료");
										location.reload();
									},
									error : function() {
										console.log(id + " 삭제에 실패");
									}
					        	});
					        }
				        } ,
				        "취소": {
				        	
				        }
				    }
				});
				console.log($(this).attr("id"));
			});
			return;
		}
	});
}

function findAddress(Address) {
	var index = Address.id.substring(4);
	
	if(qpAddress_lat[index] != 0) {
		$("#mapTitle").text("현재 배송 중입니다.");
	} else {
		$("#mapTitle").text("배송기사를 배정 중입니다.");
	}
	
	$("#lat").val(qpAddress_lat[index]);
	$("#lon").val(qpAddress_lon[index]);
	$("#senderAddress").val(senderAddressArr[index]);
	$("#receiverAddress").val(receiverAddressArr[index]);
	
	setTimeout(function(){
		$("#lat").trigger("click");
	}, 500);
	
	
	
	
}


</script>
<!-- 배송중인 배달이 있다면. -->
<!-- 구글맵 API (기사님 위치 다 찍어야됨) -->



<!-- 없다면? 그냥 -->
</head>
<body>
	<!-- 내비게이션 임포트 -->
	<%@ include file = "../navigation.jsp" %>  
	
	<input type = "text" id = "lat" hidden/>
	<input type = "text" id = "lon" hidden/>
	<input type = "text" id = "senderAddress" hidden/>
	<input type = "text" id = "receiverAddress" hidden/>
	
	<div class = "row">
		<div class = "col-md-5">
			<h2 class = "centerBox mainListTitle text-conceptColor mb-3" id = "mapTitle">
				배송기사 위치를 확인해보세요.
			</h2>
			
		<div class="map_wrap">
		    <div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div> 
		    <!-- 지도타입 컨트롤 div 입니다 -->
		    <div class="custom_typecontrol radius_border">
		        <span id="btnTraffic" class="ColorBorder_map" onclick="setMapType('traffic')">교통정보</span>
		        <span id="btnStart" class="ColorBorder_map" onclick="setMapType('start')">출발지</span>
   		        <span id="btnQP" class="ColorBorder_map" onclick="setMapType('qp')">배송기사</span>
		        <span id="btnDepart" class="ColorBorder_map" onclick="setMapType('depart')">도착지</span>
		    </div>
	    </div>
		    
			<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=fb36d3fbdea0b0b3d5ac3c3bb1c0532d&libraries=services,clusterer,drawing"></script>
			<script type="text/javascript">
				var status = 0;
				var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
			    mapOption = { 
			        center: new daum.maps.LatLng(37.5014846, 127.0393984), // 지도의 중심좌표
			        level: 8 // 지도의 확대 레벨
			    };
		
				var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
    

				document.getElementById('lat').addEventListener('click', mapInit)
				
				var markers = [];
			    var coords_Start;
				var coords_End;
				var coords_QP;

				function mapInit() {
					deleteMarkers(null);
					map.setLevel('5');
					
					
					// 주소-좌표 변환 객체를 생성합니다(출발지)
					var geocoder = new daum.maps.services.Geocoder();

					var imageSrc_start = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/red_b.png';
				    var imageSize_start = new daum.maps.Size(50, 45);
					var markerImage_start = new daum.maps.MarkerImage(imageSrc_start, imageSize_start); 
				    console.log($("#senderAddress").val());
					geocoder.addressSearch($("#senderAddress").val(), function(result, status) {
					    // 정상적으로 검색이 완료됐으면 
					     if (status === daum.maps.services.Status.OK) {
					    	coords_Start = new daum.maps.LatLng(result[0].y, result[0].x);
					    	
					        var marker = new daum.maps.Marker({
					            map: map,
					            title : "출발 지점",
					            position: coords_Start,
					            image : markerImage_start
					        });
					    	markers.push(marker);
					    } 
					});
					
					// 주소-좌표 변환 객체를 생성합니다(도착지)

				    console.log($("#receiverAddress").val());
					var imageSrc_end = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/blue_b.png';
				    var imageSize_end = new daum.maps.Size(50, 45);
					var markerImage_end = new daum.maps.MarkerImage(imageSrc_end, imageSize_end); 
					geocoder.addressSearch($("#receiverAddress").val(), function(result, status) {
					    // 정상적으로 검색이 완료됐으면 
					     if (status === daum.maps.services.Status.OK) {
					    	 coords_End = new daum.maps.LatLng(result[0].y, result[0].x);
					        var marker = new daum.maps.Marker({
					            map: map,
					            title : "도착 지점",
					            position: coords_End,
					            image : markerImage_end
					        });
					    	markers.push(marker);
					    } 
					});    
					

				    
									
					// 마커를 표시할 위치와 title 객체 배열입니다 
					coords_QP = new daum.maps.LatLng($("#lat").val(), $("#lon").val());

					var imageSrc_qp = "<%=request.getContextPath()%>/image/index_img/place_qp.png"; 
				    var imageSize_qp = new daum.maps.Size(48, 35);
					var markerImage_qp = new daum.maps.MarkerImage(imageSrc_qp, imageSize_qp); 
				    // 마커를 생성합니다
				    var marker = new daum.maps.Marker({
				        map: map, // 마커를 표시할 지도
				        position: coords_QP, // 마커를 표시할 위치
				        title : "배송기사 위치", // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
				        image : markerImage_qp // 마커 이미지 
				    });
			    	markers.push(marker);

					
			        var moveLatLon = new daum.maps.LatLng($("#lat").val(), $("#lon").val());
				    map.panTo(moveLatLon);     
					
				}
				
				function deleteMarkers(map) {
					for (var i = 0; i < markers.length; i++) {
						markers[i].setMap(map);
					}
					markers = [];
				}
			
				function setMapType(maptype) { 
				    var StartControl = document.getElementById('btnStart');
				    var QPControl = document.getElementById('btnQP'); 
				    var DepartControl = document.getElementById('btnDepart'); 
				    var moveLatLon;
				    
				    if (maptype === 'start') {
				    	moveLatLon = coords_Start;
					    map.panTo(moveLatLon);     
					    
				    } else if (maptype === 'qp') {
				        moveLatLon = new daum.maps.LatLng($("#lat").val(), $("#lon").val());
					    map.panTo(moveLatLon);
					    
				    } else if (maptype === 'depart') {
				    	moveLatLon = coords_End;
					    map.panTo(moveLatLon);
					    
				    } else {
				    	if(status == 0) {
							map.addOverlayMapTypeId(daum.maps.MapTypeId.TRAFFIC);
							status = 1;
				    	} else {
				    		map.removeOverlayMapTypeId(daum.maps.MapTypeId.TRAFFIC);
				    		status = 0;
				    	}
				    }
				}
				
				
			</script>
		</div>
		<div class = "col-md-7">
			<h2 class = "centerBox mainListTitle text-conceptColor mb-3">
				주문 이력
			</h2>
			
			<div class="limiter">
				<div class="container-table100" style = "top:0em!important;">
					<div class = "table1000">
						<div class="table100 ver1" style = "max-height: 400px; width: 100%!important;">
							<div class="table100-head">
						
								<table class = "table1000">
									<thead>
										<tr class="row100 head">
											<th class="column100 column1 centerBox">신청번호</th>
											<th class="column100 column2 c2 centerBox">날짜</th>
											<th class="column100 column3 centerBox">목적지</th>
											<th class="column100 column4 centerBox">물품</th>
											<th class="column100 column5 centerBox">배송 현황</th>
											<th class="column100 column6 centerBox">배송 기사</th>
										</tr>
									</thead>
								</table>
							</div>
							<div class="table100-body js-pscroll" style = "max-height: 400px!important;">
								<table class = "table1000">
									<tbody id = "tableBody">
									</tbody>
								</table>
							</div>
							<div class = "centerBox text-conceptColor mt-5">
								<h6> 신청 번호를 클릭하면 해당 신청 번호의 출발지, 도착지, 담당 배송기사님의 위치를 조회할 수 있습니다. </h6>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			
			
			
			
			<!-- javascript가 여기에 들어가야 작동됨 -->
			<script src="<%=request.getContextPath() %>/Table_Fixed_Header/vendor/bootstrap/js/popper.js"></script>
			<script src="<%=request.getContextPath() %>/Table_Fixed_Header/vendor/bootstrap/js/bootstrap.min.js"></script>
			<script src="<%=request.getContextPath() %>/Table_Fixed_Header/vendor/select2/select2.min.js"></script>
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
			<script src="<%=request.getContextPath() %>/Table_Fixed_Header/js/main.js"></script>
			
			
		</div>
	</div>

	<%@ include file = "../footer.jsp" %>
</body>
</html>