<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="org.json.JSONObject,org.json.JSONArray,org.json.JSONException"%>
<jsp:include page="install_header.jsp"></jsp:include>

<%
	String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
	String db_host = null;
	String db_port = null;
	String db_name = null;
	String db_password = null;
	String db_username = null;
	if(VCAP_SERVICES != null){
		try{
			JSONObject jsonObj = new JSONObject(VCAP_SERVICES);
			String[] names = jsonObj.getNames(jsonObj);
			for(String n:names){
				JSONArray array = jsonObj.getJSONArray(n);
				JSONObject obj = array.getJSONObject(0);
				
				String name = obj.getString("name");
				System.out.println("Name:"+name);
				String label = obj.getString("label");
				System.out.println("label:"+label);
				String plan = obj.getString("plan");
				System.out.println("plan:"+plan);
				
				JSONArray tags = obj.getJSONArray("tags");
				System.out.println("tags:"+tags);
				
				JSONObject credentials = obj.getJSONObject("credentials");
				System.out.println("credentials:"+credentials);
				String credentials_name = credentials.getString("name");
				db_name = credentials_name;
				System.out.println("credentials_name:"+credentials_name);
				String credentials_hostname = credentials.getString("hostname");
				db_host = credentials_hostname;
				System.out.println("credentials_hostname:"+credentials_hostname);
				String credentials_host = credentials.getString("host");
				System.out.println("credentials_host:"+credentials_host);
				String credentials_port = credentials.getString("port");
				db_port = credentials_port;
				System.out.println("credentials_port:"+credentials_port);
				String credentials_user = credentials.getString("user");
				System.out.println("credentials_user:"+credentials_user);
				String credentials_username = credentials.getString("username");
				db_username = credentials_username;
				System.out.println("credentials_username:"+credentials_username);
				String credentials_password = credentials.getString("password");
				db_password = credentials_password;
				System.out.println("credentials_password:"+credentials_password);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
%>
<form id="theform" method="post" action="${theurl}?step=2">
	<table class="showtable">
		<tr><td><strong># 设置JavaCenter Home数据库信息</strong></td></tr>
		<tr><td id="msg1">这里设置JavaCenter Home的数据库信息</td></tr>
	</table>
	<table class=datatable>
		<tr>
			<td width="25%">数据库服务器地址:</td>
			<td><input type="text" name="db[dbHost]" size="20" value=<%=db_host %>></td>
			<td width="30%">一般为localhost或127.0.0.1</td>
		</tr>
		<tr>
			<td width="25%">数据库服务器端口:</td>
			<td><input type="text" name="db[dbPort]" size="20" value=<%=db_port %>></td>
			<td width="30%">一般为3306</td>
		</tr>
		<tr>
			<td>数据库用户名:</td>
			<td><input type="text" name="db[dbUser]" size="20" value=<%=db_username %>></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>数据库密码:</td>
			<td><input type="password" name="db[dbPw]" size="20" value=<%=db_password %>></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>数据库名:</td>
			<td><input type="text" name="db[dbName]" size="20" value=<%=db_name %>></td>
			<td>如果不存在，则会尝试自动创建</td>
		</tr>
		<tr>
			<td>表名前缀:</td>
			<td><input type="text" name="db[tablePre]" size="20" value="jchome_"></td>
			<td>不能为空，默认为jchome_</td>
		</tr>
	</table>
	<table class=button>
		<tr><td><input type="submit" id="sqlsubmit" name="sqlsubmit" value="设置完毕,检测我的数据库配置"></td></tr>
	</table>
	<input type="hidden" name="formhash" value="${formhash}">
</form>
<jsp:include page="install_footer.jsp"></jsp:include>