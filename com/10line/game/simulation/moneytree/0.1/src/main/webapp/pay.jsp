<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.infoLabel {
	font-family: MS Serif, New York, serif;
}
</style>
</head>
<body>
<%
Object appId = request.getParameter("app_id");
Object orderNum = request.getParameter("order_number");
Object token = request.getParameter("token");
Object redUrl = request.getParameter("redirect_url");
 %>
<p>确认交易成功</p>
<a href="http://127.0.0.1:8888/moneytree.html?gwt.codesvr=127.0.0.1:9997">回到首页</a>
</body>
</html>