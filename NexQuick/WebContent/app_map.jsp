<%@ page import = "com.nexquick.model.vo.OnDelivery" %>
<%@ page import = "java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=request.getContextPath() %>/Table_Fixed_Header/vendor/jquery/jquery-3.2.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/indexStyle.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/InputBoxStyle.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>
<title>NexQuick :: 퀵 라이더 위치 지도</title>
<% 
	double qpLat = (double) request.getSession().getAttribute("qpLat");
	double qpLon = (double) request.getSession().getAttribute("qpLon");
	String senderAddress = (String) request.getSession().getAttribute("senderAddress");
	String receiverAddress = (String) request.getSession().getAttribute("receiverAddress");
%>


<script type="text/javascript">
$(function() {

	findAddress();

	
});

function findAddress() {
	console.log("<%=qpLat%>");
	console.log("<%=qpLon%>");
	console.log("<%=senderAddress%>");
	console.log("<%=receiverAddress%>");
	$("#lat").val(<%=qpLat%>);
	$("#lon").val(<%=qpLon%>);
	$("#senderAddress").val("<%=senderAddress%>");
	$("#receiverAddress").val("<%=receiverAddress%>");
	
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
	<input type = "text" id = "lat" hidden/>
	<input type = "text" id = "lon" hidden/>
	<input type = "text" id = "senderAddress" hidden/>
	<input type = "text" id = "receiverAddress" hidden/>
	<h2 id = "titleText">
		
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

		    	var moveLatLon;
		    	if($("#lat").val() != 0) {
		    		moveLatLon = new daum.maps.LatLng($("#lat").val(), $("#lon").val());
		    		$("#titleText").text("배송기사가 이동 중입니다.");
		    	} else {
			        moveLatLon = coords_Start;
			        $("#titleText").text("배정된 배송기사 위치정보가 없습니다.");
		    	}
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

	
	



</body>
</html>