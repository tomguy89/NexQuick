<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>simpleMap</title>
        <script src="https://api2.sktelecom.com/tmap/js?version=1&format=javascript&appKey=5096fcfd-6200-419f-b367-a37263afc3cc"></script>
        <script type="text/javascript" src = "./js/jquery-3.3.1.min.js"></script>
        <script type="text/javascript">
			function initTmap(){
				var map = new Tmap.Map({
					div:'map_div',
					width : "934px",
					height : "452px",
				});
				map.setCenter(new Tmap.LonLat("126.986072", "37.570028").transform("EPSG:4326", "EPSG:3857"), 15);
				var markerStartLayer = new Tmap.Layer.Markers("start");
				map.addLayer(markerStartLayer);
				
				var size = new Tmap.Size(24, 38);
				var offset = new Tmap.Pixel(-(size.w / 2), -size.h);
				var icon = new Tmap.IconHtml("<img src='http://tmapapis.sktelecom.com/upload/tmap/marker/pin_r_m_s.png' />", size, offset);
				var marker_s = new Tmap.Marker(new Tmap.LonLat("126.93751471394472", "37.55420997079275").transform("EPSG:4326", "EPSG:3857"), icon);
				markerStartLayer.addMarker(marker_s);
				
				// 도착
				var markerEndLayer = new Tmap.Layer.Markers("end");
				map.addLayer(markerEndLayer);
				
				var size = new Tmap.Size(24, 38);
				var offset = new Tmap.Pixel(-(size.w / 2), -size.h);
				var icon = new Tmap.IconHtml("<img src='http://tmapapis.sktelecom.com/upload/tmap/marker/pin_r_m_e.png' />", size, offset);
				var marker_e = new Tmap.Marker(new Tmap.LonLat("126.98500054232991", "37.566474000000234").transform("EPSG:4326", "EPSG:3857"), icon);
				markerEndLayer.addMarker(marker_e);
				 
				//경유지 심볼 찍기
				var markerWaypointLayer = new Tmap.Layer.Markers("waypoint");
				map.addLayer(markerWaypointLayer);
				
				var size = new Tmap.Size(24, 38);
				var offset = new Tmap.Pixel(-(size.w / 2), -size.h);
				var icon = new Tmap.IconHtml("<img src='http://tmapapis.sktelecom.com/upload/tmap/marker/pin_b_m_p.png' />", size, offset);
				var marker = new Tmap.Marker(new Tmap.LonLat("126.9453253065815","37.55596209855752").transform("EPSG:4326", "EPSG:3857"), icon);
				markerWaypointLayer.addMarker(marker);
				
				var icon = new Tmap.IconHtml("<img src='http://tmapapis.sktelecom.com/upload/tmap/marker/pin_b_m_p.png' />", size, offset);
				var marker = new Tmap.Marker(new Tmap.LonLat("126.94880144945907","37.55568992607372").transform("EPSG:4326", "EPSG:3857"), icon);
				markerWaypointLayer.addMarker(marker);
				
				var icon = new Tmap.IconHtml("<img src='http://tmapapis.sktelecom.com/upload/tmap/marker/pin_b_m_p.png' />", size, offset);
				var marker = new Tmap.Marker(new Tmap.LonLat("126.96279185168888","37.55800336050195").transform("EPSG:4326", "EPSG:3857"), icon);
				markerWaypointLayer.addMarker(marker);
				
				
				var icon = new Tmap.IconHtml("<img src='http://tmapapis.sktelecom.com/upload/tmap/marker/pin_b_m_p.png' />", size, offset);
				var marker = new Tmap.Marker(new Tmap.LonLat("126.96879999987864","37.55242377872695").transform("EPSG:4326", "EPSG:3857"), icon);
				markerWaypointLayer.addMarker(marker);
				
				
				var icon = new Tmap.IconHtml("<img src='http://tmapapis.sktelecom.com/upload/tmap/marker/pin_b_m_p.png' />", size, offset);
				var marker = new Tmap.Marker(new Tmap.LonLat("126.97326319568134","37.55851366725071").transform("EPSG:4326", "EPSG:3857"), icon);
				markerWaypointLayer.addMarker(marker);
				
				var icon = new Tmap.IconHtml("<img src='http://tmapapis.sktelecom.com/upload/tmap/marker/pin_b_m_p.png' />", size, offset);
				var marker = new Tmap.Marker(new Tmap.LonLat("126.9815887724651","37.55960230996702").transform("EPSG:4326", "EPSG:3857"), icon);
				markerWaypointLayer.addMarker(marker);
				
				var icon = new Tmap.IconHtml("<img src='http://tmapapis.sktelecom.com/upload/tmap/marker/pin_b_m_p.png' />", size, offset);
				var marker = new Tmap.Marker(new Tmap.LonLat("126.98794024340755","37.56232384717036").transform("EPSG:4326", "EPSG:3857"), icon);
				markerWaypointLayer.addMarker(marker);

			var prtcl;
			var headers = {}; 
			headers["appKey"]="발급AppKey";//실행을 위한 키 입니다. 발급받으신 AppKey를 입력하세요.
			$.ajax({
				type:"POST",
				headers : headers,
				url:"https://api2.sktelecom.com/tmap/routes/routeOptimization10?version=1&format=xml",//경유지 최적화 api요청 url입니다.
				async:false,
				contentType: "application/json",
				data: JSON.stringify({
						  "reqCoordType": "WGS84GEO",//요청 좌표 타입입니다.
						  "resCoordType": "EPSG3857",
						  "startName": "출발",//출발지 명칭입니다.
						  "startX": "126.93751471394472",//출발지 경도입니다.
						  "startY": "37.55420997079275",//출발지 위도입니다.
						  "startTime": "201711121314",//출발 시작 시간입니다.
/* 						  "endName": "도착",//도착치 명칭입니다.
						  "endX": "126.98500054232991",//도착지 경도입니다.
						  "endY": "37.566474000000234",//도착지 위도입니다. */
						  "viaPoints" : 
									[
									 {
										 "viaPointId" : "test01",//경유지 id입니다.
										 "viaPointName" : "nmae01",//경유지 명칭입니다.
										 "viaX" : "126.9453253065815" ,//경유지 경도입니다.
										 "viaY" : "37.55596209855752" //경유지 위도입니다.
									 },
									 {
										 "viaPointId" : "test02",
										 "viaPointName" : "nmae02",
										 "viaX" : "126.94880144945907" ,
										 "viaY" : "37.55568992607372" 
									 },
									 {
										 "viaPointId" : "test03",
										 "viaPointName" : "nmae03",
										 "viaX" : "126.96279185168888" ,
										 "viaY" : "37.55800336050195" 
									 },
									 {
										 "viaPointId" : "test04",
										 "viaPointName" : "nmae04",
										 "viaX" : "126.96879999987864" ,
										 "viaY" : "37.55242377872695" 
									 },
									 {
										 "viaPointId" : "test05",
										 "viaPointName" : "nmae05",
										 "viaX" : "126.97326319568134" ,
										 "viaY" : "37.55851366725071" 
									 },
									 {
										 "viaPointId" : "test06",
										 "viaPointName" : "nmae06",
										 "viaX" : "126.9815887724651" ,
										 "viaY" : "37.55960230996702" 
									 },
									 {
										 "viaPointId" : "test07",
										 "viaPointName" : "nmae07",
										 "viaX" : "126.98794024340755" ,
										 "viaY" : "37.56232384717036" 
									 }
									]
				}),
				//데이터 로드가 성공적으로 완료되었을 때 발생하는 함수입니다.
				success:function(response){
					prtcl = response;
					
					// 결과 출력
					var innerHtml ="";
					var prtclString = new XMLSerializer().serializeToString(prtcl);//xml to String	
				    xmlDoc = $.parseXML( prtclString ),
				    $xml = $( xmlDoc ),
			    	$intRate = $xml.find("Document");
			    	
			    	var tDistance = "총 거리 : "+($intRate[0].getElementsByTagName("tmap:totalDistance")[0].childNodes[0].nodeValue/1000).toFixed(1)+"km,";
			    	var tTime = " 총 시간 : "+($intRate[0].getElementsByTagName("tmap:totalTime")[0].childNodes[0].nodeValue/60).toFixed(0)+"분,";	
			    	var tFare = " 총 요금 : "+$intRate[0].getElementsByTagName("tmap:totalFare")[0].childNodes[0].nodeValue+"원";	

			    	$("#result").text(tDistance+tTime+tFare);
					
					prtcl=new Tmap.Format.KML({extractStyles:true, extractAttributes:true}).read(prtcl);//데이터(prtcl)를 읽고, 벡터 도형(feature) 목록을 리턴합니다.
					var routeLayer = new Tmap.Layer.Vector("route");//레이어를 생성합니다.
					
					//벡터 도형(Feature)이 추가되기 직전에 트리거됩니다.
					routeLayer.events.register("beforefeatureadded", routeLayer, onBeforeFeatureAdded);
					        function onBeforeFeatureAdded(e) {
						        	var style = {};//스타일을 저장하기위한 변수입니다.
						        	switch (e.feature.attributes.styleUrl) {
						        	case "#pointStyle":
							        	style.externalGraphic = "http://topopen.tmap.co.kr/imgs/point.png"; //렌더링 포인트에 사용될 외부 이미지 파일의 url입니다.
							        	style.graphicHeight = 16;//외부 이미지 파일의 크기 설정을 위한 픽셀 높이입니다.
							        	style.graphicOpacity = 1;//외부 이미지 파일의 투명도 (0-1)입니다.
							        	style.graphicWidth = 16;//외부 이미지 파일의 크기 설정을 위한 픽셀 폭입니다.
						        	break;
						        	default:
							        	style.strokeColor = "#ff0000";//stroke에 적용될 16진수 color입니다.
							        	style.strokeOpacity = "1";//stroke의 투명도(0~1)입니다.
							        	style.strokeWidth = "5";//stroke의 넓이(pixel 단위)
						        	};
					        	e.feature.style = style;//도형의 스타일을 적용합니다.
					        }
					
					routeLayer.addFeatures(prtcl); //레이어에 도형을 등록합니다.
					map.addLayer(routeLayer);//맵에 레이어 추가
					
					map.zoomToExtent(routeLayer.getDataExtent());//map의 zoom을 routeLayer의 영역에 맞게 변경합니다.	
				},
				//요청 실패시 콘솔창에서 에러 내용을 확인할 수 있습니다.
				error:function(request,status,error){
					console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});
				
				
			} 
		</script>
    </head>
 	 <body onload="initTmap()">
        <div id="map_div">
        </div>        
    </body>
</html>

