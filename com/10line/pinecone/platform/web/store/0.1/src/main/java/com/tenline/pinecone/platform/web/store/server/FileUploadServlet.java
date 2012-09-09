package com.tenline.pinecone.platform.web.store.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gwt.user.server.Base64Utils;

public class FileUploadServlet extends HttpServlet {
	
	private static final long serialVersionUID = 6021751377304367035L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletFileUpload upload = new ServletFileUpload();
		String filename = null;
		FileItemIterator iter;
		try {
			iter = upload.getItemIterator(request);
			
			while(iter.hasNext()){
				FileItemStream item = iter.next();
				InputStream stream = item.openStream();
				if(!item.isFormField()){
					if(item.getFieldName().equals("file")){
//						filename = Streams.asString(stream,"utf-8");
//						System.out.println(filename);
//						Base64 base64codec = new Base64(-1);
//						String ss = base64codec.encodeToString(filename.getBytes());
//						System.out.println("ss"+ss);
						byte[] temp = new byte[1024*1024];
						int len = 0;
						int b = 0;
						while((b=stream.read()) != -1){
							temp[len] = (byte)b;
							len++;
						}

//						File file = new File("D:\\1111.png");
//						file.createNewFile();
//						FileOutputStream fos = new FileOutputStream(file);
//						fos.write(temp, 0, len);
//						fos.close();
//						String s1 = new String(temp,"ISO-8859-1");
//						System.out.println(s1.trim().length());
//						String content = new String(temp,0,len,"ISO-8859-1");
//						System.out.println(content.length());
//						File file1 = new File("D:\\2222.png");
//						file1.createNewFile();
//						FileOutputStream fos1 = new FileOutputStream(file1);
//						fos1.write(s1.getBytes());
//						fos1.close();
//						System.out.println(len);
						
						byte[] array = new byte[len];
						for(int i=0;i<len;i++){
							array[i] = temp[i];
						}
						String base64 = Base64Utils.toBase64(array);
						base64 = base64.replace('$', '+');
						base64 = base64.replace('_', '/');
//						Base64 base64codec = new Base64(-1);
//						String base64_1 = base64codec.encodeAsString(array);
//						System.out.println("base64:"+base64);
//						System.out.println("base64_1:"+base64_1);
						response.getOutputStream().print(base64);
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
	}

}
