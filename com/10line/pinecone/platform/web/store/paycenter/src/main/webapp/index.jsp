<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>Pinecone Pay Center</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	</head>

	<body>
		<h2 align = center>Welcome to Pinecone web pay center!</h2>
		<br>
		<form action="loginCheck.jsp" method="post" align = center>
			<tr>
				<td>
					UserName:
				</td>
			</tr>
			<tr>
				<td>
					<input type="text" name="id"  align = left style="width: 150px; ">
				</td>
			</tr>
			<tr>
					Password:
			</tr>
			<tr>
				<td>
					<input type="password" name="pwd" align = left style="width: 150px; ">
				</td>
			</tr>
			<tr>
				<td colspan="2"><td>
					<input type="submit" value="login">
				</td>
				</td></tr>
			<tr>
		</form>
	</body>
</html>
