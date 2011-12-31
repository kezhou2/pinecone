/**
 * 
 */
package com.tenline.pinecone.platform.web.service.restful;

import java.util.Collection;

import javax.jdo.PersistenceManagerFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.support.JdoDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.web.service.MailService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class MailRestfulService extends JdoDaoSupport implements MailService {

	/**
	 * 
	 */
	@Autowired
	public MailRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(Mail.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public Mail create(Mail mail) {
		// TODO Auto-generated method stub
		mail.setReceiver(getJdoTemplate().getObjectById(User.class, mail.getReceiver().getId()));
		mail.setSender(getJdoTemplate().getObjectById(User.class, mail.getSender().getId()));
		return getJdoTemplate().makePersistent(mail);
	}

	@Override
	public Mail update(Mail mail) {
		// TODO Auto-generated method stub
		Mail detachedMail = getJdoTemplate().getObjectById(Mail.class, mail.getId());
		if (mail.isRead() != null) detachedMail.setRead(mail.isRead());
		if (mail.getTitle() != null) detachedMail.setTitle(mail.getTitle());
		if (mail.getContent() != null) detachedMail.setContent(mail.getContent());
		return getJdoTemplate().makePersistent(detachedMail);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Mail> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Mail.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return getJdoTemplate().find(queryString);
	}

	@Override
	public Collection<Mail> showBySender(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(User.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")))
		.getSentMails();
	}

	@Override
	public Collection<Mail> showByReceiver(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(User.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")))
		.getReceivedMails();
	}

}
