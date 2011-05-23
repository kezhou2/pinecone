/**
 * 
 */
package com.tenline.pinecone.service.restful;

import java.util.Collection;

import javax.jdo.PersistenceManagerFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.support.JdoDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenline.pinecone.model.Item;
import com.tenline.pinecone.model.Variable;
import com.tenline.pinecone.service.ItemService;

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

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.AbstractService#delete(java.lang.String)
	 */
	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(Item.class, id));
		return Response.status(Status.OK).build();
	}

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.ItemService#create(com.tenline.pinecone.model.Item)
	 */
	@Override
	public Item create(Item item) {
		// TODO Auto-generated method stub
		item.setVariable((Variable) getJdoTemplate().getObjectById(Variable.class, item.getVariable().getId()));
		return (Item) getJdoTemplate().makePersistent(item);
	}

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.ItemService#update(com.tenline.pinecone.model.Item)
	 */
	@Override
	public Item update(Item item) {
		// TODO Auto-generated method stub
		Item detachedItem = (Item) getJdoTemplate().getObjectById(Item.class, item.getId());
		if (item.getValue() != null) detachedItem.setValue(item.getValue());
		if (item.getText() != null) detachedItem.setText(item.getText());
		return (Item) getJdoTemplate().makePersistent(detachedItem);
	}

	/* (non-Javadoc)
	 * @see com.tenline.pinecone.service.ItemService#show(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Collection<Item> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Item.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return (Collection<Item>) getJdoTemplate().find(queryString);
	}

}
