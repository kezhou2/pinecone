/**
 * 
 */
package com.tenline.pinecone.platform.web.service.integration;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.logging.Level;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.APIListener;
import com.tenline.pinecone.platform.sdk.ApplicationAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;

/**
 * @author Bill
 *
 */
public class ApplicationServiceIntegrationTest extends AbstractServiceIntegrationTest {

	private User user;
	
	private Application application;
	
	private UserAPI userAPI;
	
	private ApplicationAPI applicationAPI;
	
	@Before
	public void testSetup() {
		user = new User();
		user.setName("bill");
		application = new Application();
		application.setName("fishshow");
		application.setIconUrl("http://icon/1");
		application.setTargetUrl("http://fishshow");
		application.setSymbolicName("com.10line.life.pet.fishshow");
		application.setVersion("1.1");
	}
	
	@After
	public void testShutdown() {
		user = null;
		application = null;
		userAPI = null;
		applicationAPI = null;
	}
	
	@Test
	public void testCRUD() throws Exception {
		userAPI = new UserAPI("localhost", "8080", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				user = (User) message;
				assertEquals("bill", user.getName());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}
			
		});
		userAPI.create(user);
		applicationAPI = new ApplicationAPI("localhost", "8080", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				application = (Application) message;
				assertEquals("fishshow", application.getName());
				assertEquals("http://icon/1", application.getIconUrl());
				assertEquals("http://fishshow", application.getTargetUrl());
				assertEquals("com.10line.life.pet.fishshow", application.getSymbolicName());
				assertEquals("1.1", application.getVersion());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}
			
		});
		application.setUser(user);
		applicationAPI.create(application);
		applicationAPI = new ApplicationAPI("localhost", "8080", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				application = (Application) message;
				assertEquals("fishshow2", application.getName());
				assertEquals("http://icon/2", application.getIconUrl());
				assertEquals("http://fishshow/2", application.getTargetUrl());
				assertEquals("com.10line.life.pet.fishshow.2", application.getSymbolicName());
				assertEquals("2.1", application.getVersion());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}
			
		});
		application.setName("fishshow2");
		application.setIconUrl("http://icon/2");
		application.setTargetUrl("http://fishshow/2");
		application.setSymbolicName("com.10line.life.pet.fishshow.2");
		application.setVersion("2.1");
		applicationAPI.update(application);
		applicationAPI = new ApplicationAPI("localhost", "8080", new APIListener() {

			@Override
			@SuppressWarnings("unchecked")
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals(1, ((Collection<Application>) message).size());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}
			
		});
		applicationAPI.show("id=='"+application.getId()+"'");
		applicationAPI = new ApplicationAPI("localhost", "8080", new APIListener() {

			@Override
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals("Application Deleted!", message.toString());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}
			
		});
		applicationAPI.delete(application.getId());
		applicationAPI = new ApplicationAPI("localhost", "8080", new APIListener() {

			@Override
			@SuppressWarnings("unchecked")
			public void onMessage(Object message) {
				// TODO Auto-generated method stub
				assertEquals(0, ((Collection<Application>) message).size());
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				logger.log(Level.SEVERE, error);
			}
			
		});
		applicationAPI.showByUser("id=='"+user.getId()+"'");
	}

}
