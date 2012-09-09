/**
 * 
 */
package com.tenline.pinecone.platform.web.store.server;

import java.util.Collection;

import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.web.store.client.services.MailService;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
public class MailServiceImpl extends AbstractService implements MailService {

	/**
	 * 
	 */
	public MailServiceImpl() {
		super();
	}

	@Override
	public boolean delete(Entity entity) throws Exception {
		APIResponse response = modelAPI.delete(entity);
		if (response.isDone()) return true;
		else return false;
	}

	@Override
	public Mail create(Mail mail) throws Exception {
		APIResponse response = modelAPI.create(mail);
		if (response.isDone()) return (Mail) response.getMessage();
		else return null;
	}

	@Override
	public Mail update(Mail mail) throws Exception {
		APIResponse response = modelAPI.update(mail);
		if (response.isDone()) return (Mail) response.getMessage();
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Mail> show(String filter) throws Exception {
		APIResponse response = modelAPI.show(Mail.class,filter);
		if (response.isDone()) return (Collection<Mail>) response.getMessage();
		else return null;
	}

//	@Override
//	@SuppressWarnings("unchecked")
//	public Collection<Mail> showByReceiver(String filter) throws Exception {
//		APIResponse response = mailAPI.showByReceiver(filter);
//		if (response.isDone()) return (Collection<Mail>) response.getMessage();
//		else return null;
//	}
//
//	@Override
//	@SuppressWarnings("unchecked")
//	public Collection<Mail> showBySender(String filter) throws Exception {
//		APIResponse response = mailAPI.showBySender(filter);
//		if (response.isDone()) return (Collection<Mail>) response.getMessage();
//		else return null;
//	}

}
