/**
 * 
 */
package com.tenline.pinecone.platform.web.service.restful;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Service;

import com.tenline.pinecone.platform.model.Message;
import com.tenline.pinecone.platform.web.service.ChannelService;

/**
 * @author Bill
 *
 */
@Service
public class ChannelRestfulService implements ChannelService {
	
	private ConcurrentHashMap<String, Object> cache;

	/**
	 * 
	 */
	public ChannelRestfulService() {
		// TODO Auto-generated constructor stub
		cache = new ConcurrentHashMap<String, Object>();
	}

	@Override
	public void subscribe(String subject, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			if (cache.containsKey(subject)) {
				Message message = (Message) cache.get(subject);
				response.setCharacterEncoding(message.getCharacterEncoding());
				response.setContentType(message.getContentType());
				response.setContentLength(message.getContentLength());
				OutputStream output = response.getOutputStream();
				output.write(message.getContentBytes());
				output.flush();
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Response publish(String subject, HttpServletRequest request) {
		// TODO Auto-generated method stub
		try {
			InputStream input = request.getInputStream();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			int data;
			while ((data = input.read()) != -1) {
				output.write(data);	
			}
			output.flush();
			output.close();
			Message message = new Message();
			message.setCharacterEncoding(request.getCharacterEncoding());
			message.setContentType(request.getContentType());
			message.setContentLength(request.getContentLength());
			message.setContentBytes(output.toByteArray());
			cache.put(subject, message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(Status.OK).build();
	}
	
}
