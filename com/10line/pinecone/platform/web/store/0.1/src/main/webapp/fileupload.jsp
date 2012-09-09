<%@ page contentType="text/html;charset=GB2312" import="java.io.*"
	import="org.apache.commons.fileupload.*"
	import="org.apache.commons.fileupload.servlet.*"
	import="org.apache.commons.fileupload.util.*"
%>
<%
ServletFileUpload upload = new ServletFileUpload();
String filename = null;
FileItemIterator iter;
iter = upload.getItemIterator(request);
while(iter.hasNext()){
	FileItemStream item = iter.next();
	InputStream stream = item.openStream();
	if(!item.isFormField()){
		if(item.getFieldName().equals("file")){
			filename = Streams.asString(stream,"utf-8");
			out.print("filename");
		}
	}
}
%>

