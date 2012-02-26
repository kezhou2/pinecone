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

import com.tenline.pinecone.platform.model.Account;

/**
 * @author Bill
 *
 */
@Path("/api/account")
public interface AccountService extends AbstractService {

	/**
	 * 
	 * @param account
	 * @return
	 */
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Account create(Account account);
	
	/**
	 * 
	 * @param account
	 * @return
	 */
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Account update(Account account);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	@Path("/show/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<Account> show(@PathParam("filter") String filter);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	@Path("/show/@User/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<Account> showByUser(@PathParam("filter") String filter);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@GET
	@Path("/show/@Bank/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	Collection<Account> showByBank(@PathParam("filter") String filter);

}
