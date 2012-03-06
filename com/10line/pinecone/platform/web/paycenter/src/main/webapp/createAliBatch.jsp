<%@page
	import="com.tenline.pinecone.platform.web.store.payment.BatchPayInfoCreater"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.text.SimpleDateFormat;"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
	<%
		String id = (String) (session.getAttribute("user"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date date1 = sdf.parse((String)(request.getParameter("from")));
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-mm-dd");
		Date date2 = sdf2.parse((String)(request.getParameter("to")));
		if (id.equals("wangyq")) {
			String rst = BatchPayInfoCreater.createAliBatch(date1,date2);
			if (rst != null) {
				Date today = Calendar.getInstance().getTime();
				SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
				String day = sd.format(today);
				String path = "./downfiles/moneytree/" + day + "/";
				String fileName = path + "AlipayBatch.txt";
				response.sendRedirect(fileName);
			}
		} else {
		}
	%>
</body>
</html>
