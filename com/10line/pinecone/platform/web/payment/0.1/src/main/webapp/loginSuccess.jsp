<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script language="javascript" type="text/javascript"
	src="My97DatePicker/WdatePicker.js"></script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>

<body>
	<form name="frm" action="createAliBatchNew.jsp" method="post" align=center style="width: 683px; ">
		<table align=center bgColor=#999999 cellPadding=3 cellSpacing=1
			width="95%">
			<tr bgColor=#eeeeee>
				<td align=left width="35%">From：</td>
				<td><input class="Wdate" name ="from" type="text" onClick="WdatePicker()">
				</td>

			</tr>
			<tr bgColor=#eeeeee>
				<td align=left width="35%">To：</td>
				<td><input class="Wdate" name ="to" type="text" onClick="WdatePicker()" >
				</td>
			</tr>
		</table>
		<input type="submit" value="Create ali batch file now !" >
	</form>
</body>
</html>
