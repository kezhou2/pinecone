<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>Renren Webcanvas Demo -- Welcome</title>
</head>
<%
	String appId =(String) request.getAttribute("appId");
	appId = "230784";
%>
<body>
  <script type="text/javascript">
      top.location="http://graph.renren.com/oauth/authorize?client_id=<%=appId%>&response_type=token&display=page&redirect_uri=" +encodeURIComponent('http://apps.renren.com/devicecontroller/renrenlink.jsp');
  </script>
</body>
</html>