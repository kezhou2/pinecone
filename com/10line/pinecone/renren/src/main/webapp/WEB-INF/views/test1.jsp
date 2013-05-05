<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tenline.pinecone.renren.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
a : ${a}  
	<table>
		<tr>
			<th>Value</th>
			<th>Square</th>
		</tr>
		<c:forEach items="${items}" var="user">
			<tr>
				<td align="left" class="blogTitle"><c:out value="${user.name}"
						escapeXml="false" /></td>
				<td align="left" class="blogText"><c:out value="${user.age}"
						escapeXml="false" /></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>