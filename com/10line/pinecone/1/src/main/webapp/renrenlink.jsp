<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>Pinecone - device controller</title>
<link rel="icon" href="img/favicon.ico" mce_href="img/favicon.ico"
	type="image/x-icon">
<link rel="shortcut icon" href="img/favicon.ico"
	ce_href="img/favicon.ico" type="image/x-icon">
<link href="css/main.css" rel="stylesheet" type="text/css" />
<!--[if IE 8]><link href="css/ie8.css" rel="stylesheet" type="text/css" /><![endif]-->
<!--[if IE 9]><link href="css/ie9.css" rel="stylesheet" type="text/css" /><![endif]-->
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:400,600,700'
	rel='stylesheet' type='text/css'>

<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>

<script type="text/javascript"
	src="js/plugins/forms/jquery.uniform.min.js"></script>

<script type="text/javascript" src="js/files/bootstrap.min.js"></script>

<script type="text/javascript" src="js/files/login.js"></script>

<script type="text/javascript">
	window.onload = function() {
		document.form1.submit();
	}
</script>
<%
String access_token = (String) request.getParameter("access_token");
String xn_sig_user = (String) request.getParameter("xn_sig_user");
String xn_sig_session_key = (String) request.getParameter("xn_sig_session_key");

request.getSession().setAttribute("access_token", access_token);
request.getSession().setAttribute("xn_sig_user", xn_sig_user);
request.getSession().setAttribute("xn_sig_session_key", xn_sig_session_key);
%>

</head>

<body>
	<form name="form1" id="form1" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
		<input type="hidden" name="j_username" value="liugy"/> 
		<input type="hidden" name="j_password"  value="198297"/>
  		<input type="hidden" name="access_token" value="<%=access_token %>"/> 
		<input type="hidden" name="xn_sig_user"  value="<%=xn_sig_user %>"/>
		<input type="hidden" name="xn_sig_session_key" value="<%=xn_sig_session_key %>"/>
	</form>
</body>
</html>
