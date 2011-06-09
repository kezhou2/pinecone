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

import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.web.service.ItemService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class ItemRestfulService extends JdoDaoSupport implements ItemService {
	
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
		return (Item) getJdoTemplate().makePersistent(item);
	}

	@Override
	public Item update(Item item) {
		// TODO Auto-generated method stub
		Item detachedItem = (Item) getJdoTemplate().getObjectById(Item.class, item.getId());
		if (item.getValue() != null) detachedItem.setValue(item.getValue());
		if (item.getText() != null) detachedItem.setText(item.getText());
		return (Item) getJdoTemplate().makePersistent(detachedItem);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Item> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Item.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return (Collection<Item>) getJdoTemplate().find(queryString);
	}

}
