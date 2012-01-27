/**
 * 
 */
package com.tenline.pinecone.platform.web.service.restful;

import java.io.IOException;
import java.util.Collection;

import javax.jdo.PersistenceManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.jdo.support.JdoDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.web.service.ItemService;
import com.tenline.pinecone.platform.web.service.oauth.OAuthProvider;
import com.tenline.pinecone.platform.web.service.oauth.OAuthUtils;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class ItemRestfulService extends JdoDaoSupport implements ItemService, ApplicationContextAware {
	
	private OAuthProvider provider;
	
	/**
	 * 
	 */
	@Autowired
	public ItemRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(Item.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public Item create(Item item) {
		// TODO Auto-generated method stub
		item.setVariable((Variable) getJdoTemplate().getObjectById(Variable.class, item.getVariable().getId()));
		return getJdoTemplate().makePersistent(item);
	}

	@Override
	public Item update(Item item) {
		// TODO Auto-generated method stub
		Item detachedItem = (Item) getJdoTemplate().getObjectById(Item.class, item.getId());
		if (item.getValue() != null) detachedItem.setValue(item.getValue());
		if (item.getText() != null) detachedItem.setText(item.getText());
		return getJdoTemplate().makePersistent(detachedItem);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Item> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Item.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return getJdoTemplate().find(queryString);
	}

	@Override
	public Collection<Item> showByVariable(String filter,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			OAuthUtils.doFilter(request, response, provider);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getJdoTemplate().getObjectById(Variable.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")))
		.getItems();
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub
		provider = OAuthUtils.getOAuthProvider(arg0);
	}

}
