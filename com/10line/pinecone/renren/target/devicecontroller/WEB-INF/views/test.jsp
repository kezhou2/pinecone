<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>Renren Webcanvas Demo -- Home</title>
</head>
<%
String access_token = (String) request.getParameter("access_token");
String xn_sig_user = (String) request.getParameter("xn_sig_user");
String xn_sig_session_key = (String) request.getParameter("xn_sig_session_key");
%>
<body>
  Hi, This is home page!
  <%=access_token %>
  <%=xn_sig_user %>
  <%=xn_sig_session_key %>
  <a href='/welcome'>click </a>
</body>
</html>