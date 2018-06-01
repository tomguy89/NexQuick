<%@ page import = "com.nexquick.model.vo.CSInfo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" class="no-js">
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<link rel="shortcut icon" href="../favicon.ico">
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/GoogleNexusWebsiteMenu/css/normalize.css" />
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/GoogleNexusWebsiteMenu/css/demo.css" />
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/GoogleNexusWebsiteMenu/css/component.css" />
		<script src="<%= request.getContextPath() %>/GoogleNexusWebsiteMenu/js/modernizr.custom.js"></script>
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
		<script>
			$(function() {
				setTimeout(function(){
			        $('.trans--grow').addClass('grow');
		    	}, 275);
				
				$("#logout").on("click", function() {
					$.ajax({
						url : "<%= request.getContextPath() %>/account/signOut.do",
						dataType : "json",
						method : "POST",
						success : logOutFunction
					});
				});
				
			});
			
			function logOutFunction(JSONDocument) {
				if(JSONDocument) {
					location.replace("<%= request.getContextPath() %>/index.jsp");
				}
			}
			
			
		</script>
	</head>
	<body>
		<% CSInfo csInfo = (CSInfo) request.getSession().getAttribute("csInfo"); %>
		<% if(csInfo != null) { %>
		<ul id="gn-menu" class="gn-menu-main mb-5" style = "z-index: 9999;">
			<li class="gn-trigger">
				<a class="gn-icon gn-icon-menu"><span>Menu</span></a>
				<nav class="gn-menu-wrapper">
					<div class="gn-scroller">
						<ul class="gn-menu">
							<%
									if(csInfo.getCsType() == 0) {								
							%>
							<li>
								<a><i class="fas fa-user-lock menuIcons"></i>관리자 메뉴</a>
								<ul class="gn-submenu">
									<li><a href = "<%= request.getContextPath() %>/admin/allCs.do"><i class="far fa-user navIcons"></i>고객 관리</a></li>
									<li><a href = "<%= request.getContextPath() %>/admin/allQp.do"><i class="fas fa-motorcycle navIcons"></i>배송기사 관리</a></li>
									<li><a><i class="fas fa-coins navIcons"></i>매출 관리</a></li>
									<li><a><i class="far fa-file-alt navIcons"></i>신청 관리</a></li>
									<li><a><i class="far fa-comments navIcons"></i>상담내역 관리</a></li>
								</ul>
							</li>
							<%} else { %>
							<li>
								<a><i class="fas fa-users menuIcons"></i>사용자 메뉴</a>
								<ul class="gn-submenu">
									<li><a><i class="fas fa-box navIcons"></i>퀵 신청하기</a></li>
									<li><a><i class="fas fa-list navIcons"></i>퀵 신청 목록</a></li>
									<li><a href = "<%= request.getContextPath() %>/"><i class="fas fa-users-cog navIcons"></i>계정 설정 및 조회</a></li>
								</ul>
							</li>
							<%} %>
							<li id = "logout"><a><i class="fas fa-sign-out-alt menuIcons"></i>로그아웃</a></li>
							
						</ul>
					</div><!-- /gn-scroller -->
				</nav>
			</li>
			<li><a href="#"><i class="fas fa-motorcycle"></i><span> NexQuick</span></a></li>
			<li><a href="#"><i class="far fa-user"></i><span> <%= csInfo.getCsName() %>님 환영합니다.</span></a></li>
		</ul>
		<script src="<%= request.getContextPath() %>/GoogleNexusWebsiteMenu/js/classie.js"></script>
		<script src="<%= request.getContextPath() %>/GoogleNexusWebsiteMenu/js/gnmenu.js"></script>
		<script>
			new gnMenu( document.getElementById( 'gn-menu' ) );
		</script>
		<%} %>
	</body>
</html>











<%-- 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% User user = (User) request.getSession().getAttribute("user"); %>

</head>
<body>



<nav class="navbar navbar-expand-lg navbar-light bg-light btn-primary">
  <a class="navbar-brand" href="#">NexQuick(로고 들어갈 예정)</a>
  <button class="navbar-toggler borderColor" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNav">
  	
  	
  	
    <ul class="navbar-nav">
      유저가 관리자일때 추가
      <li class="nav-item active">
        <button class = "borderColor">관리자 메뉴</button>
      </li>
      <!-- 아니면 그냥 일반 -->
      <li class="nav-item active">
      	<button class = "borderColor">퀵 신청하기</button>
      </li>
      <li class="nav-item active">
        <button class = "borderColor">로그아웃</button>
      </li>
      <li class="nav-item active">
        <span id = "loginUserName">000님 환영합니다!</span>
      </li>
      
    </ul>
    

    
    
  </div>
</nav>

</body>
</html> --%>