<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<script type="text/javascript" src = "./js/jquery-3.3.1.min.js"></script>	
<script>		


var headers = {}; 
headers["appKey"]="5096fcfd-6200-419f-b367-a37263afc3cc";
$.ajax({
	
	method:"POST",
	headers : {"appKey":"5096fcfd-6200-419f-b367-a37263afc3cc"},
	url:"https://api2.sktelecom.com/tmap/routes?version=1&format=xml",//자동차 경로안내 api 요청 url입니다.
	async:false,
	data:{
		//출발지 위경도 좌표입니다.
		//37.501384, 127.039696
		startX : "127.039696",
		startY : "37.501384",
		//목적지 위경도 좌표입니다.
		//37.502143, 127.026356
		endX : "127.026356",
		endY : "37.502143",
		//출발지, 경유지, 목적지 좌표계 유형을 지정합니다.
		reqCoordType : "WGS84GEO",
		resCoordType : "EPSG3857",
/* 		//각도입니다.
		angle : "172", */
		//경로 탐색 옵션 입니다.
		searchOption : 0
	},
	//데이터 로드가 성공적으로 완료되었을 때 발생하는 함수입니다.
	success:function(response){
		prtcl = response;
	
		console.log("성공!")
		// 결과 출력
		var innerHtml ="";
		var prtclString = new XMLSerializer().serializeToString(prtcl);//xml to String	
	    xmlDoc = $.parseXML( prtclString ),
	    $xml = $( xmlDoc ),
    	$intRate = $xml.find("Document");
    	
    	var tDistance = "총 거리 : "+($intRate[0].getElementsByTagName("tmap:totalDistance")[0].childNodes[0].nodeValue/1000).toFixed(1)+"km,";
    	var tTime = " 총 시간 : "+($intRate[0].getElementsByTagName("tmap:totalTime")[0].childNodes[0].nodeValue/60).toFixed(0)+"분,";	
    	
		
    	console.log(tDistance+"..거리 잘 계산됨 ");
    	console.log(tTime+"..시간 잘 계산됨");
    	
    	$("#result").text(tDistance+tTime);
		

	},
	//요청 실패시 콘솔창에서 에러 내용을 확인할 수 있습니다.
	error:function(request,status,error){
		console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	}
});



</script>
</head>
<body>

</body>

</html>