/**
 * 
 */
package com.tenline.pinecone.platform.web.store.server;

import java.util.Collection;

import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.sdk.MailAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.web.store.client.services.MailService;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
public class MailServiceImpl extends AbstractService implements MailService {
	
	private MailAPI mailAPI;

	/**
	 * 
	 */
	public MailServiceImpl() {
		// TODO Auto-generated constructor stub
		mailAPI = new MailAPI(HOST, PORT, CONTEXT);
	}

	@Override
	public boolean delete(String id) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = mailAPI.delete(id);
		if (response.isDone()) return true;
		else return false;
	}

	@Override
	public Mail create(Mail mail) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = mailAPI.create(mail);
		if (response.isDone()) return (Mail) response.getMessage();
		else return null;
	}

	@Override
	public Mail update(Mail mail) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = mailAPI.update(mail);
		if (response.isDone()) return (Mail) response.getMessage();
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Mail> show(String filter) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = mailAPI.show(filter);
		if (response.isDone()) return (Collection<Mail>) response.getMessage();
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Mail> showByReceiver(String filter) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = mailAPI.showByReceiver(filter);
		if (response.isDone()) return (Collection<Mail>) response.getMessage();
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Mail> showBySender(String filter) throws Exception {
		// TODO Auto-generated method stub
		APIResponse response = mailAPI.showBySender(filter);
		if (response.isDone()) return (Collection<Mail>) response.getMessage();
		else return null;
	}

}
