/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.services;

import java.util.Collection;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tenline.pinecone.platform.model.Mail;

/**
 * @author Bill
 *
 */
@RemoteServiceRelativePath("mail/service")
public interface MailService extends AbstractService {
	
	/**
	 * 
	 * @param mail
	 * @return
	 * @throws Exception
	 */
	public Mail create(Mail mail) throws Exception;
	
	/**
	 * 
	 * @param mail
	 * @return
	 * @throws Exception
	 */
	public Mail update(Mail mail) throws Exception;
	
	/**
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public Collection<Mail> show(String filter) throws Exception;

//	/**
//	 * 
//	 * @param filter
//	 * @return
//	 * @throws Exception
//	 */
//	public Collection<Mail> showByReceiver(String filter) throws Exception;
//
//	/**
//	 * 
//	 * @param filter
//	 * @return
//	 * @throws Exception
//	 */
//	public Collection<Mail> showBySender(String filter) throws Exception;
	
}
