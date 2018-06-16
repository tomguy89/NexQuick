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
				var routeLayer = new Tmap.Layer.Vector("route");
				map.addLayer(routeLayer);
				
				var markerStartLayer = new Tmap.Layer.Markers("start");
				map.addLayer(markerStartLayer);
				
				var size = new Tmap.Size(24, 38);
				var offset = new Tmap.Pixel(-(size.w / 2), -size.h);
				var icon = new Tmap.IconHtml("<img src='http://tmapapis.sktelecom.com/upload/tmap/marker/pin_r_m_s.png' />", size, offset);
				var marker_s = new Tmap.Marker(new Tmap.LonLat("126.96665", "37.56558").transform("EPSG:4326", "EPSG:3857"), icon);
				markerStartLayer.addMarker(marker_s);
				
				// 도착
				var markerEndLayer = new Tmap.Layer.Markers("end");
				map.addLayer(markerEndLayer);
				
				var size = new Tmap.Size(24, 38);
				var offset = new Tmap.Pixel(-(size.w / 2), -size.h);
				var icon = new Tmap.IconHtml("<img src='http://tmapapis.sktelecom.com/upload/tmap/marker/pin_r_m_e.png' />", size, offset);
				var marker_e = new Tmap.Marker(new Tmap.LonLat("127.00197", "37.57079").transform("EPSG:4326", "EPSG:3857"), icon);
				markerEndLayer.addMarker(marker_e);
				
				var markerWaypointLayer = new Tmap.Layer.Markers("waypoint");
				map.addLayer(markerWaypointLayer);
				
				var size = new Tmap.Size(24, 38);
				var offset = new Tmap.Pixel(-(size.w / 2), -size.h);
				var icon = new Tmap.IconHtml("<img src='http://tmapapis.sktelecom.com/upload/tmap/marker/pin_b_m_p.png' />", size, offset);
				var marker = new Tmap.Marker(new Tmap.LonLat("126.97201", "37.56994").transform("EPSG:4326", "EPSG:3857"), icon);
				markerWaypointLayer.addMarker(marker);
				
				icon = new Tmap.IconHtml("<img src='http://tmapapis.sktelecom.com/upload/tmap/marker/pin_b_m_p.png' />", size, offset);
				marker = new Tmap.Marker(new Tmap.LonLat("126.98227", "37.56990").transform("EPSG:4326", "EPSG:3857"), icon);
				markerWaypointLayer.addMarker(marker);

				icon = new Tmap.IconHtml("<img src='http://tmapapis.sktelecom.com/upload/tmap/marker/pin_b_m_p.png' />", size, offset);
				marker = new Tmap.Marker(new Tmap.LonLat("126.98334", "37.56575").transform("EPSG:4326", "EPSG:3857"), icon);
				markerWaypointLayer.addMarker(marker);

				icon = new Tmap.IconHtml("<img src='http://tmapapis.sktelecom.com/upload/tmap/marker/pin_b_m_p.png' />", size, offset);
				marker = new Tmap.Marker(new Tmap.LonLat("126.98781", "37.56637").transform("EPSG:4326", "EPSG:3857"), icon);
				markerWaypointLayer.addMarker(marker);

				icon = new Tmap.IconHtml("<img src='http://tmapapis.sktelecom.com/upload/tmap/marker/pin_b_m_p.png' />", size, offset);
				marker = new Tmap.Marker(new Tmap.LonLat("126.99074", "37.56796").transform("EPSG:4326", "EPSG:3857"), icon);
				markerWaypointLayer.addMarker(marker);

				icon = new Tmap.IconHtml("<img src='http://tmapapis.sktelecom.com/upload/tmap/marker/pin_b_m_p.png' />", size, offset);
				marker = new Tmap.Marker(new Tmap.LonLat("126.99151", "37.57316").transform("EPSG:4326", "EPSG:3857"), icon);
				markerWaypointLayer.addMarker(marker);

				icon = new Tmap.IconHtml("<img src='http://tmapapis.sktelecom.com/upload/tmap/marker/pin_b_m_p.png' />", size, offset);
				marker = new Tmap.Marker(new Tmap.LonLat("126.99666", "37.57092").transform("EPSG:4326", "EPSG:3857"), icon);
				markerWaypointLayer.addMarker(marker);
				
				var prtcl;
				var headers = {}; 
				headers["appKey"]="5096fcfd-6200-419f-b367-a37263afc3cc";
				$.ajax({
					type:"POST",
					headers : headers,
					url:"https://api2.sktelecom.com/tmap/routes/routeOptimization10?version=1&format=xml",//
					async:false,
					contentType: "application/json",
					data: JSON.stringify({
							  "reqCoordType": "WGS84GEO",
							  "resCoordType" : "EPSG3857",
							  "startName": "출발",
							  "startX": "126.96665",
							  "startY": "37.56558",
							  "startTime": "201711121314",
							  "endName": "도착",
							  "endX": "126.997589",
							  "endY": "37.570594",
							  "viaPoints": [
								    {
								      "viaPointId": "test01",
								      "viaPointName": "test01",
								      "viaX": "126.97201",
								      "viaY": "37.56994"
								    },
								    {
								      "viaPointId": "test02",
								      "viaPointName": "test02",
								      "viaX": "126.98227",
								      "viaY": "37.56990"
								    },
								    {
								      "viaPointId": "test03",
								      "viaPointName": "test03",
								      "viaX": "126.98334",
								      "viaY": "37.56575"
								    },
								    {
								      "viaPointId": "test04",
								      "viaPointName": "test04",
								      "viaX": "126.98781",
								      "viaY": "37.56637"
								    },
								    {
								      "viaPointId": "test05",
								      "viaPointName": "test05",
								      "viaX": "126.99074",
								      "viaY": "37.56796"
								    },
								    {
								      "viaPointId": "test06",
								      "viaPointName": "test06",
								      "viaX": "126.99151",
								      "viaY": "37.57316"
								    },
								    {
								      "viaPointId": "test07",
								      "viaPointName": "test07",
								      "viaX": "126.99666",
								      "viaY": "37.57092"
								    }
								  ]
					}),
					success:function(response){
						prtcl = response;
						
					
						var pointLayer = new Tmap.Layer.Vector("point");
						var prtclString = new XMLSerializer().serializeToString(prtcl);//xml to String	
					    xmlDoc = $.parseXML( prtclString );
					    $xml = $( xmlDoc );
				    	
				    	var tDistance = "총 거리 : "+($xml.find("Document")[0].getElementsByTagName("tmap:totalDistance")[0].childNodes[0].nodeValue/1000).toFixed(1)+"km,";
				    	var tTime = " 총 시간 : "+($xml.find("Document")[0].getElementsByTagName("tmap:totalTime")[0].childNodes[0].nodeValue/60).toFixed(0)+"분,";	
				    	console.log(tDistance+"..거리 잘 계산됨 ");
				    	console.log(tTime+"..시간 잘 계산됨");

					    $intRate = $xml.find("Placemark");
					    var style_red = {
					            fillColor:"#FF0000",
					            fillOpacity:0.2,
					            strokeColor: "#FF0000",
					            strokeWidth: 3,
					            strokeDashstyle: "solid",
					            pointRadius: 2,
					            title: "this is a red line"
					       };
					    $intRate.each(function(index, element) {
					    	var nodeType = element.getElementsByTagName("tmap:nodeType")[0].childNodes[0].nodeValue;
							if(nodeType == "POINT"){
								var point = element.getElementsByTagName("coordinates")[0].childNodes[0].nodeValue.split(',');
								var geoPoint =new Tmap.Geometry.Point(point[0],point[1]);
								var pointFeature = new Tmap.Feature.Vector(geoPoint, null, style_red); 
								pointLayer.addFeatures([pointFeature]);
							}
					    });
					    map.addLayer(pointLayer);
					    /* -------------- Geometry.Point -------------- */
					    //경유지 최적화 결과 Line 그리기
						routeLayer.style ={
								fillColor:"#FF0000",
						        fillOpacity:0.2,
						        strokeColor: "#FF0000",
						        strokeWidth: 3,
						        strokeDashstyle: "solid",
						        pointRadius: 2,
						        title: "this is a red line"	
						}
						var kmlForm = new Tmap.Format.KML().read(prtcl);
					    routeLayer.addFeatures(kmlForm);
						
						map.zoomToExtent(routeLayer.getDataExtent());
					
					},
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

