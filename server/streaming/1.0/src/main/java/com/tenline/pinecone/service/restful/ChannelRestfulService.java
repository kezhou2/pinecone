/**
 * 
 */
package com.tenline.pinecone.service.restful;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Service;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.tenline.pinecone.model.Event;

/**
 * @author Bill
 *
 */
@Service
public class ChannelRestfulService implements com.tenline.pinecone.service.ChannelService {

	private ChannelService channelService;
	
	/**
	 * 
	 */
	public ChannelRestfulService() {
		// TODO Auto-generated constructor stub
		channelService = ChannelServiceFactory.getChannelService();
	}

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.ChannelService#subscribe(com.tenline.pinecone.model.Event)
	 */
	@Override
	public Event subscribe(Event event) {
		// TODO Auto-generated method stub
		event.setToken(channelService.createChannel(event.getSubject()));
		return event;
	}

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.ChannelService#publish(com.tenline.pinecone.model.Event)
	 */
	@Override
	public Response publish(Event event) {
		// TODO Auto-generated method stub
		String subject = event.getSubject();
		String message = event.getMessage();
		channelService.sendMessage(new ChannelMessage(subject, message));
		return Response.status(Status.OK).build();
	}

}
