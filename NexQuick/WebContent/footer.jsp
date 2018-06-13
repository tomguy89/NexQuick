<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- footer -->

<iframe style="
				position: fixed;
				border-radius: 20px;
				right: 25px; 
				bottom: 50px; 
				width: 350px; 
				height: 520px; 
				max-height: 720px; 
				min-height: 520px; 
				border: none; 
				overflow: auto; 
				z-index: 1000;
				display : none;" 
		src = "<%=request.getContextPath()%>/payTest.jsp" id = "iframeChatBot">
	
</iframe>

<div style = "position: fixed; right: 25px; bottom: 25px; width: 80px; height: 80px; background-color: #55B296; border-radius: 50%; z-index: 3000" id = "chatBotBox">
	<img id = "chatBotImg" src = "<%= request.getContextPath() %>/image/index_img/chatting.png" width = "40" height = '40' style = "display : block; margin-top: 20px; margin-left: auto; margin-right: auto;"/>
</div>

<footer class = "centerBox">
	<div class = "row">
		<div class = "col-md-4">
			<a href = "https://www.playchat.ai/mobile/chatbot/blank_user1286_1528617943506"><img src = "<%= request.getContextPath() %>/image/index_img/android.png" width = "25" height = "25" class = "mr-3"/></a>
			<img src = "<%= request.getContextPath() %>/image/index_img/kakaoImg.png" width = "25" height = "25" class = "ml-3"/>
		</div>
		<div class = "col-md-4">
			2018&nbsp;
			<img src = "<%= request.getContextPath() %>/image/index_img/BALBADAK.png" width = "25" height = "25"/>
			BALBADAK Corp.
		</div>
		<div class = "col-md-4">
			<span>대표 : 황태진</span>
		</div>
	</div>
</footer>
