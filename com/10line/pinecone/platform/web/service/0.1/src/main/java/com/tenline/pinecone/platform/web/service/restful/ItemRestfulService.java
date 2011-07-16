/**
 * 
 */
package com.tenline.pinecone.platform.web.service.restful;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;

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
		try {
			item.setText(URLDecoder.decode(item.getText(), "utf-8"));
			item.setValue(URLDecoder.decode(item.getValue(), "utf-8"));
			item.setVariable((Variable) getJdoTemplate().getObjectById(Variable.class, item.getVariable().getId()));
			item = (Item) getJdoTemplate().makePersistent(item);
			item.setText(URLEncoder.encode(item.getText(), "utf-8"));
			item.setValue(URLEncoder.encode(item.getValue(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return item;
	}

	@Override
	public Item update(Item item) {
		// TODO Auto-generated method stub
		try {
			Item detachedItem = (Item) getJdoTemplate().getObjectById(Item.class, item.getId());
			if (item.getValue() != null) detachedItem.setValue(URLDecoder.decode(item.getValue(), "utf-8"));
			if (item.getText() != null) detachedItem.setText(URLDecoder.decode(item.getText(),"utf-8"));
			item = (Item) getJdoTemplate().makePersistent(detachedItem);
			item.setText(URLEncoder.encode(item.getText(), "utf-8"));
			item.setValue(URLEncoder.encode(item.getValue(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return item;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Item> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Item.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		Collection<Item> items = getJdoTemplate().find(queryString);
		for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
			Item item = iterator.next();
			try {
				item.setText(URLEncoder.encode(item.getText(), "utf-8"));
				item.setValue(URLEncoder.encode(item.getValue(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return items;
	}

	@Override
	public Collection<Item> showByVariable(String filter) {
		// TODO Auto-generated method stub
		Variable variable = getJdoTemplate().getObjectById(Variable.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")));
		Collection<Item> items = variable.getItems();
		for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
			Item item = iterator.next();
			try {
				item.setText(URLEncoder.encode(item.getText(), "utf-8"));
				item.setValue(URLEncoder.encode(item.getValue(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return items;
	}

}
