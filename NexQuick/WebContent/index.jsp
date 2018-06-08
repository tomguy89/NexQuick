<%@ page import = "com.nexquick.model.vo.CSInfo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- JQuery -->
<script type="text/javascript" src = "./js/jquery-3.3.1.min.js"></script>
<!-- bootstrap css -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
<!-- bootstrap js -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
<!-- fontawesome css -->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<title>NexQuick</title>
<!-- 내가만든 css -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/indexStyle.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/indexStyle_radio.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/InputBoxStyle.css" />

<script type="text/javascript">
	$(function() {
		
		setInterval(function() {
			$.ajax({
				url : "<%= request.getContextPath() %>/call/delPastCall.do",
				dataType : "json",
				method : "POST",
				success : function(JSONDocument) {
					if(JSONDocument) {
						console.log("하루 지난 미완료 콜 삭제 완료");
					}
				}
			})
		}, 60*60*1000);

		
		
		$("#loginBtn").on("click", function() {
			$.ajax({
				url : "<%=request.getContextPath()%>/account/csSignIn.do",
				data : {
					csId : $("#loginUserId").val(),
					csPassword : $("#loginUserPassword").val()
				},
				dataType : "json",
				method : "POST",
				success : loginFunction,
				error : errorFunction
			});
		});
		
		
		
		/* 아이디 중복체크 */
		$("#checkId").on("click", function() {
			$.ajax({
				url : "<%= request.getContextPath() %>/account/csIdDuplCheck.do",
				data : {
					csId : $("#userId").val()
				},
				dataType : "json",
				method : "POST",
				success : checkIdFunction
			});
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
		
		$("input[type=radio]").on("click", function() {
			if($("input[type=radio]:checked").val() != 3) {
				$(".corpForm").removeClass("corpBox");
				$(".notPersonal").attr("required", "required");
			} else {
				$(".corpForm").addClass("corpBox");
				$(".notPersonal").removeAttr("required");
			}
		});
		/* 고객분류 */
		
		$("#userId").keyup(function() {
			$("#idCheck > i").remove();
			$("#checkIdResult").text("ID 중복을 확인하세요.").css("color", "#34495e");
		});
		
		$("#submitBtn").on("click", function() {
			
			if($("#checkIdResult").text() == "ID 중복을 확인하세요." || $("#checkIdResult").text() == "이미 존재하는 아이디입니다." || $("#userId").val() == "") {
				alert("ID를 다시 확인해주세요.");
				return false;
			}
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
				url : "<%= request.getContextPath() %>/account/csSignUp.do",
				data : {
					csId : $("#userId").val(),
					csPassword : $("#password").val(), 
					csName : $("#name").val(), 
					csPhone : $("#contactNumber").val(),
				    csType : $("input[type=radio]:checked").val(), 
				    csBusinessName : $("#corpName").val(), 
				    csBusinessNumber : $("#corpNum").val(),
				    csDepartment : $("#deptName").val()
				},
				dataType : "json",
				method : "POST",
				success : signUpFunction
			});
			
			
		});
		
	});
	
	function signUpFunction(JSONDocument) {
		console.log(JSONDocument);	
		if(JSONDocument) {
			alert("정상적으로 회원가입 되었습니다.");
		} else {
			alert("회원가입에 실패하였습니다. 다시 가입해 주세요.");
		}
		location.reload();
	}
	
	
	/* ID 중복체크 */
	function checkIdFunction(JSONDocument) {
		console.log("ID 중복체크 비동기통신 완료");
		if(JSONDocument) {
			$("#checkIdResult").remove();
			$("#idCheck > i").remove();
			$("#idCheck").append($("<i class = xi-check-circle-o>"));
			$("#idCheck").append(
				$("<span id = checkIdResult>").text("가입 가능한 아이디입니다.").css("color", "#34495e")
			);
			
		} else {
			$("#checkIdResult").remove();
			$("#idCheck > i").remove();
			$("#idCheck").append($("<i class = xi-close-circle-o>").css("color", "#e55b57"));
			$("#idCheck").append(
				$("<span id = checkIdResult>").text("이미 존재하는 아이디입니다.").css("color", "#e55b57")
			);
		}
	}

	
	function errorFunction(JSONDocument) {
		alert("아이디 또는 비밀번호가 틀렸습니다. 다시 확인해주세요.");
	}
	
	/* 로그인 */
	function loginFunction(JSONDocument) {
		console.log("로그인 비동기통신 완료");
		if(JSONDocument.csId.length != 0) {
			<% CSInfo csInfo = (CSInfo) request.getSession().getAttribute("csInfo"); %>
			<% if(csInfo != null) { %>
				<% if(csInfo.getCsType() == 0) { %>	
					/* 관리자일때 페이지를 사용자 관리 페이지로 이동할 예정. 일단은 메인 인덱스 페이지로 이동시키게 함 */
					location.replace("<%= request.getContextPath() %>/mainPage/main_index.jsp");
					console.log("관리자로그인");
				<% } else {%>
					location.replace("<%= request.getContextPath() %>/mainPage/main_index.jsp");
					console.log("고객로그인");
				<% } %>
			<% } else {%>
				console.log("널일때");	
				location.replace("<%= request.getContextPath() %>/mainPage/main_index.jsp");
			<% } %>
		} 
		console.log("로그인 비동기통신 끝");
		
	}
	
	
</script>


</head>
<body>
	<div class = "mainBox">
		<div class = "centerBox mainFont">
			차세대 퀵 서비스 플랫폼
		</div>
		<div class = "centerBox titleFont mt-3">
			<img src = "./image/index_img/NexQuickLogo.png"/>
		</div>
		<div class = "centerBox subFont mt-3">
			로그인해서 모든 서비스를 이용하세요
		</div>
		
		<form id = "loginForm">
			<div class = "centerBox whiteBox loginInput mb-5">
				<i class="far fa-user userIcon"></i>
				
	  			<label class="field field_1 field_animated field_a2 page__field call">
				    <input type="text" class="orderNumberForm field__input text-white" placeholder="ID를 입력하세요." name = "loginUserId" id="loginUserId">
				    <span class="field__label-wrap">
					   	<span class="field__label text-white">ID</span>
				    </span>
				</label>   
			</div>
			<div class = "mt-2 mb-2">
			</div>						
			<div class = "centerBox whiteBox loginInput mt-5 mb-5"> 
				<i class="fas fa-key userIcon"></i>
	  			<label class="field field_1 field_animated field_a2 page__field call">
				    <input type="password" class="orderNumberForm field__input text-white" placeholder="비밀번호를 입력하세요." name = "loginUserPassword" id="loginUserPassword">
				    <span class="field__label-wrap">
					   	<span class="field__label text-white">비밀번호</span>
				    </span>
				</label>
			</div>
			
			<div class = "centerBox loginInput">
				<button type = "button" id = "loginBtn" class = "centerBox borderColor">
					<i class="fas fa-sign-in-alt userIcon"></i>
					로그인
				</button> 
				<button type = "button" id = "registerBtn" class = "centerBox borderColor" data-toggle="modal" data-target=".bd-example-modal-lg">
					<i class="fas fa-users userIcon"></i>
					회원가입
				</button> 
			</div>
		</form>
	</div>
	
	<!-- NexQuick은~ 화면. 흰 화면 -->
	<%@ include file="./index_import/index_sub1.jsp" %>	
	<!-- NexQuick은 고객을 ~ 화면. 지도 화면 -->
	<%@ include file="./index_import/index_sub2.jsp" %>	
	<!-- footer -->
	<%@ include file="footer.jsp" %>
	
	
	
	<!-- 회원가입 페이지 모달 -->
	<%-- <form action = "<%= request.getContextPath() %>/account/csSignUp.do" id = "regForm" method="post"> --%>
	<form action = "#" id = "regForm">
	
		<div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
		  <div class="modal-dialog modal-lg">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h3 class="modal-title" id="exampleModalCenterTitle">NexQuick 회원가입</h3>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <!-- 양식 들어가는 부분 -->
		      <div class="modal-body">
		      	
		      	<!-- ID -->
		      	<div class = "row mb-3">

		      		<div class = "col-md-4">
				      	<label class="field field_animated field_a2 page__field call">
						    <input type="text" class="orderNumberForm field__input text-conceptColor" placeholder="ID를 입력하세요." name = "csId" id="userId" required>
						    <span class="field__label-wrap">
							   	<span class="field__label text-conceptColor">* ID</span>
						    </span>
						</label> 
		      		</div>
		      		
		      		<div class = "col-md-2 vertical_center">
		      			<button type = "button" class = "ColorBorder" id = "checkId">중복확인</button>
		      		</div>
		      		<div class = "col-md-4 vertical_center" id = "idCheck">
						<span id = "checkIdResult" style = "line-height: 35px;">ID 중복을 확인하세요.</span>
		      		</div>
		      	</div>
		      	<!-- ID 끝 -->
		      	
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
						    <input type="text" class="orderNumberForm field__input text-conceptColor" placeholder="이름을 입력하세요." name = "csName" id="name" required>
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
		      		  	<input type="radio" id="test3" name="csType" value="1" checked>
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
		      <!-- 양식 들어가는 부분 끝 -->
		      <div class="modal-footer">
		        <button type="button" class="dangerBorder" data-dismiss="modal"><i class = "xi-close-circle-o"></i> 취소</button>
		        <button type="button" class="ColorBorder" id = "submitBtn" data-dismiss="modal"><i class = "xi-user-plus"></i> 회원가입</button>
		      </div>
		      
		    </div>
		  </div>
		</div>
	
	</form>
	
	
	
</body>
</html>