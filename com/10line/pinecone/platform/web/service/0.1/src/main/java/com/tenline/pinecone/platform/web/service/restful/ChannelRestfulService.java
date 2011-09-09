/**
 * 
 */
package com.tenline.pinecone.platform.web.service.restful;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Service;

import com.google.appengine.api.memcache.stdimpl.GCacheFactory;
import com.tenline.pinecone.platform.model.Message;
import com.tenline.pinecone.platform.web.service.ChannelService;

/**
 * @author Bill
 *
 */
@Service
public class ChannelRestfulService implements ChannelService {
	
	private Cache cache;
	private final static int EXPIRATION_MILLIS = 1900; // 1.9 seconds

	/**
	 * 
	 */
	public ChannelRestfulService() {
		// TODO Auto-generated constructor stub
		try {
			Map<Integer, Integer> props = new HashMap<Integer, Integer>();
	        props.put(GCacheFactory.EXPIRATION_DELTA_MILLIS, EXPIRATION_MILLIS); 
	        cache = CacheManager.getInstance().getCacheFactory().createCache(props);
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
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
