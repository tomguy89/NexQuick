<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id = "goingBox">
<div class = "row centerBox">
	<div class = "col-md-2 offset-md-1">
		<h1 class = "centerBox text-conceptColor">
				퀵 신청하기
		</h1>
	</div>
	<div class = "col-md-1 offset-md-2">
		<img src="<%= request.getContextPath() %>/image/quickApply_img/placeholder_black.png" width="60" height= "60" id = "placeholderImg">
	</div>
	<div class = "col-md-1" style = "line-height: 60px; margin-top: auto; margin-bottom: auto;">
		<img src="<%= request.getContextPath() %>/image/quickApply_img/right_black.png" width="20" height= "20"  id = "right_Img_1">
	</div>
	<div class = "col-md-1">
		<img src="<%= request.getContextPath() %>/image/quickApply_img/applyDetail_black.png" width="60" height= "60"  id = "applyDetailImg">
	</div>
	<div class = "col-md-1" style = "line-height: 60px; margin-top: auto; margin-bottom: auto;">
		<img src="<%= request.getContextPath() %>/image/quickApply_img/right_black.png" width="20" height= "20"  id = "right_Img_2">
	</div>
	<div class = "col-md-1">
		<img src="<%= request.getContextPath() %>/image/quickApply_img/complete_black.png" width="60" height= "60"  id = "completeImg">
	</div>
</div>
<div class = "row centerBox">
	<div class = "col-md-1 offset-md-5">
		<span id = "placeholderImgText" class = "text-conceptColor" style = "font-size: 11px;">기본정보 설정</span>
	</div>
	
	<div class = "col-md-1 offset-md-1">
		<span id = "applyDetailImgText" class = "text-conceptColor" style = "font-size: 11px;">상세정보 설정</span>
	</div>
	
	<div class = "col-md-1 offset-md-1">
		<span id = "completeImgText" class = "text-conceptColor" style = "font-size: 11px;">신청 완료</span>
	</div>
</div>
<hr class="trans--grow hr1"/>
</div>
