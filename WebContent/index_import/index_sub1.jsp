<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class = "centerBox font_3em mt-3">
	<b>NexQuick은</b>
</div>
<div class = "row centerBox mt-5">
	<div class = "col-md-4">
		<img src = "<%= request.getContextPath() %>/image/index_img/browser.png" width = "100px"  height = "100px" class = "mr-2"/>
		<img src = "<%= request.getContextPath() %>/image/index_img/phone.png" width = "100px"  height = "100px"  class = "ml-2"/>
		<div class = "mt-4 font_2em">
			<b>편리합니다.</b>
		</div>
		<div class = "mt-5 desc_box">
			더 이상 전화가 아닌, <br/>
			<b>인터넷과 모바일 앱</b>으로 간편하게 주문하세요.
		</div>
		<div class = "imageBox">
			<img src = "<%= request.getContextPath() %>/image/index_img/user.png" width = "64px"  height = "64px" />
			<img src = "<%= request.getContextPath() %>/image/index_img/rightArrow.png" width = "20px"  height = "20px" class = "mx-4"/>
			<img src = "<%= request.getContextPath() %>/image/index_img/order.png" width = "64px"  height = "64px"  />
			<img src = "<%= request.getContextPath() %>/image/index_img/rightArrow.png" width = "20px"  height = "20px" class = "mx-4" />
			<img src = "<%= request.getContextPath() %>/image/index_img/check.png" width = "64px"  height = "64px"  />
			
		</div>
		<div class="desc_box mt-3">
			간단한 회원가입 후, 빠르게 주문할 수 있습니다. 
		</div>
		
	</div>
	<div class = "col-md-4">
		<img src = "<%= request.getContextPath() %>/image/index_img/stopwatch.png" width = "100px"  height = "100px" class = "mr-2"/>
		<div class = "mt-4 font_2em">
			<b>빠릅니다.</b>
		</div>
		<div class = "mt-5 desc_box">
			NexQuick만의 배송 알고리즘으로,<br/>
			최단거리에 있는 배송기사님들을 배정하여 <br/>
			<b>가장 빠른 서비스</b>를 보장합니다.
		</div>
		
		<div class = "imageBox">
			<img src = "<%= request.getContextPath() %>/image/index_img/gps.png" width = "64px"  height = "64px" />
			<img src = "<%= request.getContextPath() %>/image/index_img/linked.png" width = "64px"  height = "64px" />
			<img src = "<%= request.getContextPath() %>/image/index_img/rightArrow.png" width = "20px"  height = "20px" class = "mx-2"/>
			<table style = "display: inline;">
				<tr>
					<td colspan = "2">
						<img src = "<%= request.getContextPath() %>/image/index_img/motorcycle.png" width = "64px"  height = "64px"  />
					</td>
				</tr>
				<tr>
					<td>
						<img src = "<%= request.getContextPath() %>/image/index_img/car.png" width = "64px"  height = "64px"  />
					</td>
					<td>
						<img src = "<%= request.getContextPath() %>/image/index_img/truck.png" width = "64px"  height = "64px"  />
					</td>
				</tr>
			</table>
		</div>
		<div class="desc_box mt-3">
			GPS 기반으로 최단거리의 배송기사님을 배정한 후, <br/>
			다양한 운송수단으로 배송합니다. 
		</div>
		
		
		
	</div>
	<div class = "col-md-4">
		<img src = "<%= request.getContextPath() %>/image/index_img/shield.png" width = "100px"  height = "100px" class = "mr-2"/>
		<div class = "mt-4 font_2em">
			<b>안전을 생각합니다.</b>
		</div>
		<div class = "mt-5 desc_box">
			NexQuick만의 스마트 헬멧으로,<br/>
			배송기사님들을 <b>운전에 집중할 수 있게</b> 합니다.
		</div>
		<div class="imageBox">
			<img src = "<%= request.getContextPath() %>/image/index_img/bluetooth.png" width = "64px"  height = "64px" />
			<img src = "<%= request.getContextPath() %>/image/index_img/voice.png" width = "64px"  height = "64px" class = "mx-3"/>
			<img src = "<%= request.getContextPath() %>/image/index_img/helmet.png" width = "64px"  height = "64px" />
		</div>
		<div class="desc_box mt-3">
			스마트 헬멧을 착용하면 퀵 서비스 주문을 <br/>
			블루투스, 음성인식, 모션인식으로 받을 수 있습니다. 
		</div>

		
	</div>
</div>
