/**
 * 
 */
package com.tenline.pinecone.platform.web.service.restful;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Service;

import com.tenline.pinecone.platform.web.service.StaticResourceService;

/**
 * @author Bill
 *
 */
@Service
public class StaticResourceRestfulService implements StaticResourceService {

	/**
	 * 
	 */
	public StaticResourceRestfulService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Response uploadIcon(String id, HttpServletRequest request) {
		// TODO Auto-generated method stub
		try {
			InputStream input = request.getInputStream();
			FileOutputStream output = new FileOutputStream(new File
					(request.getSession().getServletContext().getRealPath("/") +
					"/icons/" + id + ".png"));
			int data;
			while ((data = input.read()) != -1) {
				output.write(data);	
			}
			output.flush();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(Status.OK).build();
	}

	@Override
	public void downloadIcon(String id, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			OutputStream output = response.getOutputStream();
			FileInputStream input= new FileInputStream(new File
					(request.getSession().getServletContext().getRealPath("/") +
					"/icons/" + id + ".png"));
			int data;
			while ((data = input.read()) != -1) {
				output.write(data);	
			}
			output.flush();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
