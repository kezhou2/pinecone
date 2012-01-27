/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

/**
 * @author Bill
 *
 */
@Path("/oauth")
public interface OAuthService {
	
	/**
	 * Relative path for requesting token URL
	 */
	final static String REQUEST_TOKEN_URL = "/requestToken";

	/**
     * Relative path for the token authorization URL
     */
    final static String TOKEN_AUTHORIZATION_URL = "/authorization";
    
	/**
     * Relative path for the token authorization confirmation URL 
     */
    final static String TOKEN_AUTHORIZATION_CONFIRM_URL = "/authorization/confirm";
    
    /**
	 * Relative path for accessing token URL
	 */
	final static String ACCESS_TOKEN_URL = "/accessToken";
    
    /**
     * Default token authorization HTML resource 
     */
    final static String DEFAULT_TOKEN_HTML_RESOURCE = "/token_authorization.jsp";
    
	/**
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException 
	 */
	@GET
	@Path(REQUEST_TOKEN_URL)
	public void serveRequestToken(@Context HttpServletRequest req,
			@Context HttpServletResponse resp) throws IOException, ServletException;
	
	/**
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException 
	 */
	@GET
	@Path(TOKEN_AUTHORIZATION_URL)
	public void serveTokenAuthorization(@Context HttpServletRequest req,
			@Context HttpServletResponse resp) throws IOException, ServletException;
	
	/**
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException 
	 */
	@GET
	@Path(TOKEN_AUTHORIZATION_CONFIRM_URL)
	public void serveTokenAuthorizationConfirmation(@Context HttpServletRequest req,
			@Context HttpServletResponse resp) throws IOException, ServletException;
	
	/**
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException 
	 */
	@GET
	@Path(ACCESS_TOKEN_URL)
	public void serveAccessToken(@Context HttpServletRequest req,
			@Context HttpServletResponse resp) throws IOException, ServletException;
	
}
