/**
 * 
 */
package com.tenline.pinecone.platform.web.service.impl;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Driver;
import com.tenline.pinecone.platform.sdk.ModelAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.web.service.IConstants;

/**
 * @author Bill
 *
 */
@Service
public class SynchronizationServiceImpl implements ApplicationContextAware {

	/**
	 * 
	 */
	private SAXReader builder = new SAXReader();
	
	/**
	 * 
	 */
	private ModelAPI modelAPI = new ModelAPI(IConstants.WEB_SERVICE_HOST, IConstants.WEB_SERVICE_PORT, IConstants.WEB_SERVICE_CONTEXT);
	
	/**
	 * 
	 */
	public SynchronizationServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param type
	 * @param name
	 * @param domain
	 * @param subdomain
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private Category synchronizeCategory(String type, String name, String domain, String subdomain)
			throws Exception {
		String filter = "type == '" + type + "' " + "&& name == '" + name + "' " +
				"&& domain == '" + domain + "' " + "&& subdomain == '" + subdomain + "'";
		APIResponse response = modelAPI.show(Category.class, filter);
		if (response.isDone()) {
			Collection<Category> categories = (Collection<Category>) response.getMessage();
			if (categories.size() == 0) {
				Category category = new Category();
				category.setType(type);
				category.setName(name);
				category.setDomain(domain);
				category.setSubdomain(subdomain);
				response = modelAPI.create(category);
				if (response.isDone()) return (Category) response.getMessage();
			} else {
				return (Category) categories.toArray()[0];
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param category
	 * @param alias
	 * @param name
	 * @param version
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private Driver synchronizeDriver(Category category, String alias, String name, String version) 
			throws Exception {
		String filter = "version == '" + version + "' " + "&& alias == '" + alias + "' " +
				"&& category.id == '" + category.getId() + "'";
		APIResponse response = modelAPI.show(Driver.class, filter);
		if (response.isDone()) {
			Collection<Driver> drivers = (Collection<Driver>) response.getMessage();
			if (drivers.size() == 0) {
				Driver driver = new Driver();
				driver.setAlias(alias);
				driver.setCategory(category);
				driver.setName(name);
				driver.setVersion(version);
				response = modelAPI.create(driver);
				if (response.isDone()) return (Driver) response.getMessage();
			} else {
				return (Driver) drivers.toArray()[0];
			}
		}
		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		// TODO Auto-generated method stub
		new Timer().schedule(new TimerTask() {

			@Override
			@SuppressWarnings("unchecked")
			public void run() {
				// TODO Auto-generated method stub
				try { 
					Document document = builder.read(new URL(IConstants.REPOSITORY_URL));
					List<Element> nodes = document.selectNodes("/repository/resource");
					for (Element node : nodes) {
						String version = node.attribute("version").getValue();
						String[] symbolName = node.attribute("symbolicname").getValue().split("\\.");
						String presentationName = node.attribute("presentationname").getValue();
						Category category = synchronizeCategory(symbolName[0], symbolName[1], symbolName[2], symbolName[3]);
						synchronizeDriver(category, symbolName[4], presentationName, version);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
		}, 5000, 1000 * 60 * 5);
	}

}
