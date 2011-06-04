/**
 * 
 */
package com.tenline.pinecone.service.restful;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Service;

import com.tenline.pinecone.model.Message;
import com.tenline.pinecone.service.ChannelService;

/**
 * @author Bill
 *
 */
@Service
public class ChannelRestfulService implements ChannelService {
	
	private Cache cache;

	/**
	 * 
	 */
	public ChannelRestfulService() {
		// TODO Auto-generated constructor stub
		try {
			 CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			 cache = cacheFactory.createCache(Collections.emptyMap());
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	}

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.ChannelService#subscribe(java.lang.String, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void subscribe(String subject, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			Message message = (Message) cache.get(subject);
			response.setCharacterEncoding(message.getCharacterEncoding());
			response.setContentType(message.getContentType());
			response.setContentLength(message.getContentLength());
			OutputStream output = response.getOutputStream();
			output.write(message.getContentBytes());
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.ChannelService#publish(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Response publish(String subject, HttpServletRequest request) {
		// TODO Auto-generated method stub
		try {
			InputStream input = request.getInputStream();
			byte[] bytes = new byte[input.available()];
			input.read(bytes);
			Message message = new Message();
			message.setCharacterEncoding(request.getCharacterEncoding());
			message.setContentType(request.getContentType());
			message.setContentLength(request.getContentLength());
			message.setContentBytes(bytes);
			cache.put(subject, message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(Status.OK).build();
	}
	
}
