<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>Daum 지도 시작하기</title>
</head>
<body>
	<div id="map" style="width:500px;height:500px;"></div>

	<script type="text/javascript" src = "./js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7039bd6474ba59d8e1698a4fe4b35471"></script>
	<script>
		var container = document.getElementById('map');
		var options = {
			center: new daum.maps.LatLng(37.501410, 127.039621), //지도의 중심이 될 위치 (어차피 뒤에서 경계설정해서 무의미하지만 지우면 작동안됨)
			level: 5 //1 : 좁고 자세하게 -> 5: 넓게 보여줌.
		};

		var map = new daum.maps.Map(container, options);	////지도가 생성된다.
		var bounds = new daum.maps.LatLngBounds();   
		
		var imageSrc0 = 'images/scooter.png', // 마커이미지의 주소입니다    
  		 	 imageSize0 = new daum.maps.Size(40, 40), // 마커이미지의 크기입니다
   			 imageOption0 = {offset: new daum.maps.Point(27, 69)}; 

		
		var markerImage0 = new daum.maps.MarkerImage(imageSrc0, imageSize0, imageOption0),
	    markerPosition0 = new daum.maps.LatLng(33.452278, 126.567803); // 마커가 표시될 기사님 위치입니다
		
	    var marker0 = new daum.maps.Marker({
	    	  position: markerPosition0,
	    	  image: markerImage0 // 마커이미지 설정 
	    	});
		
	    marker0.setMap(map);
	    bounds.extend(markerPosition0);
		
	    
		var imageSrc1 = 'images/location.png', // 마커이미지의 주소입니다    
		 	 imageSize1 = new daum.maps.Size(40, 40), // 마커이미지의 크기입니다
			 imageOption1 = {offset: new daum.maps.Point(27, 69)}; 

	
		var markerImage1 = new daum.maps.MarkerImage(imageSrc1, imageSize1, imageOption1),
  			 markerPosition1 = new daum.maps.LatLng(33.452671, 126.574792); // 마커가 표시될 주무자 위치입니다
	
  		 var marker1 = new daum.maps.Marker({
   	  position: markerPosition1,
   	  image: markerImage1 // 마커이미지 설정 
   	});
	
   marker1.setMap(map);
   bounds.extend(markerPosition1);
	    
   
/*    
   var content = '<div class="customoverlay">' +
   '    <span class="title">기사님 위치</span>' +
   '</div>';

//커스텀 오버레이가 표시될 위치입니다 
var position = new daum.maps.LatLng(33.452278, 126.567803);  

//커스텀 오버레이를 생성합니다
var customOverlay = new daum.maps.CustomOverlay({
   map: map,
   position: position,
   content: content,
   yAnchor: 1 
}); */
   
	   

		$(function(){
			map.setBounds(bounds);
		});
		

		
	</script>
	

</body>
</html>