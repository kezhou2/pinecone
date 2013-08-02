/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.platform.model.Authority;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.RESTClient;

/**
 * @author Bill
 *
 */
public class AuthorityServiceTest extends AbstractServiceTest {

	private int page = 5;
	private RESTClient client;
	
	@Before
	public void testSetup() throws Exception {
		client = new RESTClient("http://www.pinecone.cc");
	}
	
	@After
	public void testShutdown() throws Exception {
		client = null;
	}
	
	@Test
	public void test() throws Exception {
		ArrayList<Entity> entities = client.get("/user?page=" + page, "admin", "admin");
		for (Entity entity : entities) {
			User user = (User) entity;
			if (client.get("/user/"+user.getId()+"/authorities", "admin", "admin").size() == 0) {
				logger.info(user.getName());	
				Authority authority = new Authority();
				authority.setAuthority("ROLE_USER");
				authority.setUserName(user.getName());
				long id = Long.valueOf(client.post("/authority", authority));
				client.post("/authority/" + id + "/user", "/user/" + user.getId());
			}
			if (client.get("/user/search/names?name="+user.getName(), "admin", "admin").size()>1) {
				logger.info(user.getName());
				client.delete("/user/" + user.getId());
			}
			if (client.get("/user/search/emails?email="+user.getEmail(), "admin", "admin").size()>1) {
				logger.info(user.getEmail());
				client.delete("/user/" + user.getId());
			}
		}
		entities = client.get("/authority?page=" + page, "admin", "admin");
		for (Entity entity : entities) {
			Authority authority = (Authority) entity;
			if (client.get("/authority/"+authority.getId()+"/user", "admin", "admin").size() == 0) {
				client.delete("/authority/"+authority.getId());
			}
		}
	}

}
