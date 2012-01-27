/**
 * 
 */
package com.tenline.pinecone.platform.web.service.restful;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import javax.cache.Cache;
import javax.cache.CacheConfiguration.Duration;
import javax.cache.CacheConfiguration.ExpiryType;
import javax.cache.Caching;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.tenline.pinecone.platform.model.Message;
import com.tenline.pinecone.platform.web.service.ChannelService;
import com.tenline.pinecone.platform.web.service.oauth.OAuthProvider;
import com.tenline.pinecone.platform.web.service.oauth.OAuthUtils;

/**
 * @author Bill
 *
 */
@Service
public class ChannelRestfulService implements ChannelService, ApplicationContextAware {
	
	private Cache<Object, Object> cache;
    private final static int EXPIRATION_MILLISECONDS = 1900; // 1.9 seconds
    private OAuthProvider provider;

	/**
	 * 
	 */
	public ChannelRestfulService() {
		// TODO Auto-generated constructor stub
		cache = Caching.getCacheManager().createCacheBuilder("channel")
        .setExpiry(ExpiryType.MODIFIED, new Duration(TimeUnit.MILLISECONDS, EXPIRATION_MILLISECONDS))
        .build();
	}

	@Override
	public void subscribe(String subject, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			OAuthUtils.doFilter(request, response, provider);
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
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@Override
	public Response publish(String subject, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			OAuthUtils.doFilter(request, response, provider);
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
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return Response.status(Status.OK).build();
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub
		provider = OAuthUtils.getOAuthProvider(arg0);
	}
	
}
