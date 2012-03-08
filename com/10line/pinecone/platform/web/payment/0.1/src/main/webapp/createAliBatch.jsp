<%@page
	import="com.tenline.pinecone.platform.web.payment.BatchPayInfoCreater"%>
<%@page import="com.tenline.pinecone.platform.web.payment.model.PayInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.text.SimpleDateFormat;"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
	<%
		String id = (String) (session.getAttribute("user"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date from = sdf.parse((String) (request.getParameter("from")));
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Date to = sdf2.parse((String) (request.getParameter("to")));
		if (id.equals("wangyq")) {
			ArrayList<PayInfo> infoList = BatchPayInfoCreater
					.queryBatchPayInfo(from, to);
			out.println("UserAccountID\t\tPayNumber");
	%>
	<br>
	<br>
	<%
		for (PayInfo info : infoList) {
				out.println(info.getUserAccountID() + "\t\t"
						+ info.getPayNumber());
	%>
	<br>
	<%
		}
		} else {
		}
	%>
</body>
</html>
