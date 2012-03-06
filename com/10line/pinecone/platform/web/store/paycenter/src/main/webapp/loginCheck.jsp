<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>

<body>
	<%
	if (request.getParameter("id").equals("wangyq")
			&& request.getParameter("pwd").equals("wangyq")) {
		session.setAttribute("user","wangyq");
		response.sendRedirect("loginSuccess.jsp");
	} else {
		session.setAttribute("user",null);
		response.sendRedirect("index.jsp");
	}
	%>
</body>
</html>
