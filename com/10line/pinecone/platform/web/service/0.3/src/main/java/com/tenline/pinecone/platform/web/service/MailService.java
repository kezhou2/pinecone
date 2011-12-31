/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tenline.pinecone.platform.model.Mail;

/**
 * @author Bill
 *
 */
@Path("/api/mail")
public interface MailService extends AbstractService {

	/**
	 * 
	 * @param mail
	 * @return
	 */
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Mail create(Mail mail);
	
	/**
	 * 
	 * @param mail
	 * @return
	 */
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Mail update(Mail mail);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	@Path("/show/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<Mail> show(@PathParam("filter") String filter);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	@Path("/show/@Sender/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<Mail> showBySender(@PathParam("filter") String filter);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	@Path("/show/@Receiver/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<Mail> showByReceiver(@PathParam("filter") String filter);
	
}
