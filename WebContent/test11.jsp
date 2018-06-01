<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<script language="JavaScript">
function onopen()
{
var url =
"http://www.ftc.go.kr/bizCommPop.do?wrkr_no="+frm1.wrkr_no.value;
window.open(url, "bizCommPop", "width=750, height=700;");
}
</script>
</head>

<body>
	<form name="frm1">
		<input name="wrkr_no" type="text" value="2208183676"/>
		<input type="button" value="팝업" onclick="onopen();"/>
	</form>
</body>

</html>
