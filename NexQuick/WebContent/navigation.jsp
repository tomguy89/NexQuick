<%@ page import = "com.nexquick.model.vo.CSInfo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" class="no-js">
	<head>
	<% CSInfo csInfo = (CSInfo) request.getSession().getAttribute("csInfo"); %>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<link rel="shortcut icon" href="../favicon.ico">
		
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/GoogleNexusWebsiteMenu/css/normalize.css" />
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/GoogleNexusWebsiteMenu/css/demo.css" />
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/GoogleNexusWebsiteMenu/css/component.css" />
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/indexStyle_radio.css" />
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/InputBoxStyle.css" />
		
		<script src="<%= request.getContextPath() %>/GoogleNexusWebsiteMenu/js/modernizr.custom.js"></script>
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
		
		
		<script>
			$(function() {
				$("#logout").on("click", function() {
					$.ajax({
						url : "<%= request.getContextPath() %>/account/signOut.do",
						dataType : "json",
						method : "POST",
						success : logOutFunction
					});
				});
				
				$("#getModal").on("click", function() {
					$('#exampleModal_1').modal('toggle');
				});
				
				$("#getModal_title").on("click", function() {
					$('#exampleModal_1').modal('toggle');
				});

				/* 비밀번호 체크 */
				$("#passwordRe").keyup(function() {
					var password = $("#password").val();
					var passwordRe = $("#passwordRe").val(); 
					if(password != passwordRe) {
						$("#pwCheckResult").text("비밀번호가 다릅니다. 확인해 주세요.").css("color", "red");				
					} else {
						$("#pwCheckResult").text("비밀번호가 일치합니다.").css("color", "#0070c0");
					}
					
				});

				$("#password").keyup(function() {
					var password = $("#password").val();
					var passwordRe = $("#passwordRe").val();
					if(password == "") {
						$("#pwCheckResult").text("").css();
					} else if(password != passwordRe) {
						$("#pwCheckResult").text("비밀번호가 다릅니다. 확인해 주세요.").css("color", "red");				
					} else {
						$("#pwCheckResult").text("비밀번호가 일치합니다.").css("color", "#0070c0");
					}
				});
				/* 여기까지 비밀번호 체크 */
				
				$("#userId").val("<%= csInfo.getCsId() %>");
				$("#name").val("<%= csInfo.getCsName() %>");
				$("#contactNumber").val("<%= csInfo.getCsPhone() %>");
				
				switch(<%=csInfo.getCsType()%>) {
				case 1:
					$("#test3").attr("checked", "checked");
					$(".corpForm").removeClass("corpBox");
					$("#corpNum").val("<%=csInfo.getCsBusinessNumber()%>");
					$("#corpName").val("<%=csInfo.getCsBusinessName()%>");
					$("#deptName").val("<%=csInfo.getCsDepartment() %>");
					break;
				case 2:
					$("#test2").attr("checked", "checked");
					$(".corpForm").removeClass("corpBox");
					$("#corpNum").val("<%=csInfo.getCsBusinessNumber()%>");
					$("#corpName").val("<%=csInfo.getCsBusinessName()%>");
					$("#deptName").val("<%=csInfo.getCsDepartment() %>");
					break;
				case 3:
					$(".corpForm").addClass("corpBox");
					$("#test1").attr("checked", "checked");
					break;
				}
				
				$("input[name=csType]").on("click", function() {
					if($("input[name=csType]:checked").val() != 3) {
						$(".corpForm").removeClass("corpBox");
						$(".notPersonal").attr("required", "required");
					} else {
						$(".corpForm").addClass("corpBox");
						$(".notPersonal").removeAttr("required");
					}
				});
				/* 고객분류 */
				
				
				$("#submitBtn").on("click", function() {
					
					if($("#pwCheckResult").text()=="비밀번호가 다릅니다. 확인해 주세요." || $("#password").val() == "") {
						alert("비밀번호를 다시 확인해주세요.");
						return false;
					}
					if($("#name").val()=="") {
						alert("이름을 입력해주세요.");
						return false;
					}
					if($("#contactNumber").val() == "") {
						alert("연락처를 다시 확인해주세요.");
						return false;
					}
					if($("input[type=radio]:checked").val() != 3) {
						if($("#corpName").val() == "") {
							alert("법인명을 다시 확인해주세요.");
							return false;
						}
						if($("#corpNum").val() == "") {
							alert("사업자등록번호를 다시 확인해주세요.");
							return false;
						}
						if($("#deptName").val() == "") {
							alert("부서명을 다시 확인해주세요.");
							return false;
						}
						if($("#CEOName").val() == "") {
							alert("대표자명을 다시 확인해주세요.");
							return false;
						}
					}
					
					$.ajax({
						url : "<%= request.getContextPath() %>/account/csModify.do",
						data : {
							csPassword : $("#password").val(), 
							csPhone : $("#contactNumber").val(),
						    csType : $("input[type=radio]:checked").val(), 
						    csBusinessName : $("#corpName").val(), 
						    csBusinessNumber : $("#corpNum").val(),
						    csDepartment : $("#deptName").val()
						},
						dataType : "json",
						method : "POST",
						success : modifyFunction
					});
					
					
				});
				
			});
			
			function modifyFunction(JSONDocument) {
				if(JSONDocument) {
					alert("수정이 완료되었습니다.");
					location.reload();
				} else {
					alert("수정되지 않았습니다. 다시 시도하세요.");
					location.reload();
				}
			}
			
			
			function logOutFunction(JSONDocument) {
				if(JSONDocument) {
					location.replace("<%= request.getContextPath() %>/index.jsp");
				}
			}
			
			
		</script>
	</head>
	<body>
		
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
									<li><a href = "<%= request.getContextPath() %>/admin/admin_UserPageList.jsp"><i class="far fa-user navIcons"></i>고객 관리</a></li>
									<li><a href = "<%= request.getContextPath() %>/admin/admin_QuickProListPage.jsp"><i class="fas fa-motorcycle navIcons"></i>배송기사 관리</a></li>
									<li><a><i class="fas fa-coins navIcons"></i>매출 관리</a></li>
									<li><a href = "<%= request.getContextPath() %>/admin/admin_CallListPage.jsp"><i class="far fa-file-alt navIcons"></i>신청 관리</a></li>
									<li><a><i class="far fa-comments navIcons"></i>상담내역 관리</a></li>
								</ul>
							</li>
							<%} else { %>
							<li>
								<a><i class="fas fa-users menuIcons"></i>사용자 메뉴</a>
								<ul class="gn-submenu">
									<li><a href = "<%= request.getContextPath() %>/quickApplyPage/quickApply_first.jsp"><i class="fas fa-box navIcons"></i>퀵 신청하기</a></li>
									<li><a href = "<%= request.getContextPath() %>/mainPage/main_list.jsp"><i class="fas fa-motorcycle navIcons"></i>진행중인 퀵</a></li>
									<li><a href = "<%= request.getContextPath() %>/csPage/UserPage.jsp"><i class="fas fa-list-ul navIcons"></i>신청내역 조회</a></li>
									<li ><a id = "getModal" data-toggle="modal" data-target="#exampleModal_1"><i class="fas fa-users-cog navIcons"></i>개인정보 수정</a></li>
								</ul>
							</li>
							<%} %>
							<li id = "logout"><a><i class="fas fa-sign-out-alt menuIcons"></i>로그아웃</a></li>
							
						</ul>
					</div><!-- /gn-scroller -->
				</nav>
			</li>
			<li><a href="<%= request.getContextPath() %>/mainPage/main_index.jsp"><img src = "<%= request.getContextPath() %>/image/index_img/NexQuickLogo_Nav.png" height = "40" width = "250" style = "margin-top: 10px;"/></a></li>
			<li><a id = "getModal_title" data-toggle="modal" data-target="#exampleModal_1"><i class="far fa-user"></i><span> <%= csInfo.getCsName() %>님 환영합니다.</span></a></li>
		</ul>
		<script src="<%= request.getContextPath() %>/GoogleNexusWebsiteMenu/js/classie.js"></script>
		<script src="<%= request.getContextPath() %>/GoogleNexusWebsiteMenu/js/gnmenu.js"></script>
		<script>
			new gnMenu( document.getElementById( 'gn-menu' ) );
		</script>
		<%} %>
		
		
		
		
		
		
		
		
		
			
	<div class="modal fade bd-example-modal-lg" id="exampleModal_1" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">개인정보 수정</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      
    		 	<div class = "row">
		      		<div class = "col-md-6">
				      	<label class="field field_animated field_a2 page__field call">
						    <input type="text" class="orderNumberForm field__input text-conceptColor" placeholder="ID를 입력하세요." name = "csId" id="userId" required disabled>
						    <span class="field__label-wrap">
							   	<span class="field__label text-conceptColor">* ID</span>
						    </span>
						</label> 
		      		</div>
		      	</div>     	
   				<div class = "ml-2">
	    	  		<span class = "text-dangerColor">ID는 변경할 수 없습니다. </span>
		      	</div>  
   	
	      		      	
		      	<!-- 비밀번호 -->
		      	<div class = "row">
		      		<div class = "col-md-4">
		      		
		      			<label class="field field_animated field_a2 page__field call">
						    <input type="password" class="orderNumberForm field__input text-conceptColor" placeholder="비밀번호를 입력하세요." name = "csPassword" id="password" required>
						    <span class="field__label-wrap">
							   	<span class="field__label text-conceptColor">* 비밀번호</span>
						    </span>
						</label> 
		      		</div>
		      		
		      		<div class = "col-md-6 offset-md-2">
		      			<label class="field field_animated field_a2 page__field call">
						    <input type="password" class="orderNumberForm field__input text-conceptColor" placeholder="한번 더 입력해주세요." name = "passwordRe" id="passwordRe" required>
						    <span class="field__label-wrap">
							   	<span class="field__label text-conceptColor">* 비밀번호 확인</span>
						    </span>
						</label> 
		      		</div>
		      	</div>
		      	
		      	<div class = "row mb-3">
		      		<div class = "col-md-12">
			      		<span id = "pwCheckResult"/>
		      		</div>
		      	</div>
		      	<!-- 비밀번호 끝 -->
		      	
		      	
		      	<!-- 이름, 전화번호 -->
		      	<div class = "row mb-3">
		      		<div class = "col-md-4">
		      			<label class="field field_animated field_a2 page__field call">
						    <input type="text" class="orderNumberForm field__input text-conceptColor" placeholder="이름을 입력하세요." name = "csName" id="name" required disabled>
						    <span class="field__label-wrap">
							   	<span class="field__label text-conceptColor">* 사용자 이름</span>
						    </span>
						</label> 
		      		</div>
		      		
		      		<div class = "col-md-6 offset-md-2">
		      			<label class="field field_animated field_a2 page__field call">
						    <input type="text" class="orderNumberForm field__input text-conceptColor" placeholder="ex) 01049408292" name = "csPhone" id="contactNumber" required>
						    <span class="field__label-wrap">
							   	<span class="field__label text-conceptColor">* 연락처</span>
						    </span>
						</label> 
		      		</div>
		      	</div>

		      	<!-- 이름, 전화번호 끝 -->
		      	
		      	
		      	<div class = "row mt-4 mb-3">
		      		<div class = "col-md-3">
		      			* 고객분류
		      		</div>
		      		<div class = "col-md-3">
		      		  	<input type="radio" id="test3" name="csType" value="1">
					    <label for="test3">법인/기업</label>
		      		</div>
		      		<div class = "col-md-3">
		      		 	<input type="radio" id="test2" name="csType" value="2">
					    <label for="test2">자영업</label>
		      		</div>
		      		<div class = "col-md-3">
		      		  	<input type="radio" id="test1" name="csType" value="3">
					    <label for="test1">개인</label>
		      		</div>
		      	</div>
		      	
		      		      	
		      	<div class = "row mb-3  corpForm">
		      		<div class = "col-md-4">
		      			
		      			<label class="field field_animated field_a2 page__field call">
						    <input type="text" class="orderNumberForm field__input text-conceptColor notPersonal" placeholder="ex) 1112233333" name = "csBusinessNumber" id="corpNum">
						    <span class="field__label-wrap">
							   	<span class="field__label text-conceptColor">* 사업자등록번호</span>
						    </span>
						</label> 
		      			
		      		</div>
		      		
		      		<div class = "col-md-6 offset-md-2">
		      			<label class="field field_animated field_a2 page__field call">
						    <input type="text" class="orderNumberForm field__input text-conceptColor notPersonal" placeholder="ex) (주) 규스컴퍼니" name = "csBusinessName" id="corpName">
						    <span class="field__label-wrap">
							   	<span class="field__label text-conceptColor">* 법인명</span>
						    </span>
						</label> 
		      		</div>
		      	</div>
		      	
		      	<div class = "row mb-3  corpForm">
		      		<div class = "col-md-4">
		      			<label class="field field_animated field_a2 page__field call">
						    <input type="text" class="orderNumberForm field__input text-conceptColor notPersonal" placeholder="ex) 김민규" name = "CEOName" id="CEOName">
						    <span class="field__label-wrap">
							   	<span class="field__label text-conceptColor">* 대표자</span>
						    </span>
						</label> 
		      		</div>
		      		<div class = "col-md-6 offset-md-2">
		      		
		      			<label class="field field_animated field_a2 page__field call">
						    <input type="text" class="orderNumberForm field__input text-conceptColor notPersonal" placeholder="ex) 개발부" name = "csDepartment" id="deptName">
						    <span class="field__label-wrap">
							   	<span class="field__label text-conceptColor">* 부서명</span>
						    </span>
						</label>
		      		</div>
		      	</div>
	      
			</div>
	   		<div class="modal-footer">
		        <button type="button" class="dangerBorder" data-dismiss="modal"><i class = "xi-close-circle-o"></i> 취소</button>
		        <button type="button" class="ColorBorder" id = "submitBtn" data-dismiss="modal"><i class = "xi-user-plus"></i> 수정하기</button>
		    </div>
		      
	    </div>
	  </div>
	</div>

		
	
		
		
		
		
		
		
		
	</body>
</html>





