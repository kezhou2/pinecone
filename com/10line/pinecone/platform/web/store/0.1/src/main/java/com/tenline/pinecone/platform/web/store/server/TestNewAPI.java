package com.tenline.pinecone.platform.web.store.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import com.google.gwt.user.server.Base64Utils;
import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.ModelAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;

public class TestNewAPI {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestNewAPI api = new TestNewAPI();
		try {
//			api.testShowAllUser();
//			api.showAllFriends();
			System.out.println("*************************");
			api.showFriends();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showAllFriends() throws Exception{
		ModelAPI api = new ModelAPI("pinecone-service.cloudfoundry.com", "80",
				null);
		APIResponse response = api.show(Friend.class, "all");
		if (response.isDone()) {
			Collection<Friend> friends = (Collection<Friend>) response.getMessage();
			for(Friend f:friends){
				System.out.println("friend id:" + f.getId());
				System.out.println("sender name:" + f.getSender().getName());
				System.out.println("sender id:" + f.getSender().getId());
				System.out.println("receiver name:" + f.getReceiver().getName());
				System.out.println("receiver id:" + f.getReceiver().getId());
				System.out.println("isDecided:" + f.isDecided());
				
//				f.setDecided(true);
//				response = api.update(f);
			}
		}else{
			System.out.println(response.getMessage());
		}
	}
	
	public void showFriends() throws Exception{
		ModelAPI api = new ModelAPI("pinecone-service.cloudfoundry.com", "80",
				null);
		APIResponse response = api.show(Friend.class, "!isDecided");
		if (response.isDone()) {
			Collection<Friend> friends = (Collection<Friend>) response.getMessage();
			for(Friend f:friends){
				System.out.println("friend id:" + f.getId());
				System.out.println("sender name:" + f.getSender().getName());
				System.out.println("sender id:" + f.getSender().getId());
				System.out.println("receiver name:" + f.getReceiver().getName());
				System.out.println("receiver id:" + f.getReceiver().getId());
				System.out.println("isDecided:" + f.isDecided());
				
//				f.setDecided(true);
//				response = api.update(f);
			}
		}else{
			System.out.println(response.getMessage());
		}
	}
	
	public void deleteFriend() throws Exception{
		ModelAPI api = new ModelAPI("pinecone-service.cloudfoundry.com", "80",
				null);
		APIResponse response = api.show(Friend.class, "id=='5'");
		if (response.isDone()) {
			Collection<Friend> friends = (Collection<Friend>) response.getMessage();
			for(Friend f:friends){
				api.delete(f);
			}
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public void showFriend() throws Exception{
		ModelAPI api = new ModelAPI("pinecone-service.cloudfoundry.com", "80",
				null);
		APIResponse response = api.show(User.class, "all");
		if (response.isDone()) {
			Collection<User> users = (Collection<User>) response.getMessage();
			for (User user : users) {
				System.out.println("user id:" + user.getId());
				System.out.println("user name:" + user.getName());
				System.out.println("user pwd:" + user.getPassword());
				System.out.println("user email:" + user.getEmail());
				
				System.out.println("=======Sender=======");
				response = api.show(Friend.class, "sender.id=='3'");
				if (response.isDone()) {
					Collection<Friend> friends = (Collection<Friend>) response.getMessage();
					for(Friend f:friends){
						System.out.println("friend id:" + f.getId());
						System.out.println("sender name:" + f.getSender().getName());
						System.out.println("receiver name:" + f.getReceiver().getName());
						System.out.println("isDecide:" + f.isDecided());
					}
				}
				
				System.out.println("=======Receiver=======");
				response = api.show(Friend.class, "receiver.id=='3'");
				if (response.isDone()) {
					Collection<Friend> friends = (Collection<Friend>) response.getMessage();
					for(Friend f:friends){
						System.out.println("friend id:" + f.getId());
						System.out.println("sender name:" + f.getSender().getName());
						System.out.println("receiver name:" + f.getReceiver().getName());
						System.out.println("isDecide:" + f.isDecided());
					}
				}
			}

		}
	}

	@SuppressWarnings("unchecked")
	public void testShowAllUser() throws Exception {

		ModelAPI api = new ModelAPI("pinecone-service.cloudfoundry.com", "80",
				null);
		APIResponse response = api.show(User.class, "all");
		if (response.isDone()) {
			Collection<User> users = (Collection<User>) response.getMessage();
			for (User user : users) {
				System.out.println("user id:" + user.getId());
				System.out.println("user name:" + user.getName());
				System.out.println("user pwd:" + user.getPassword());
				System.out.println("user email:" + user.getEmail());
				System.out.println("user avatar:" + user.getAvatar());
			}

		}

	}

	@SuppressWarnings("unchecked")
	public void deleteUser() throws Exception {
		ModelAPI api = new ModelAPI("pinecone-service.cloudfoundry.com", "80",
				null);
		APIResponse response = api.show(User.class, "id=='25'");
		if (response.isDone()) {
			Collection<User> users = (Collection<User>) response.getMessage();
			for (User user : users) {
				response = api.delete(user);
				if (response.isDone()) {
					System.out.println(response.getMessage());
				}
			}

		}
	}

	public void createConsumer() throws Exception {
		ModelAPI api = new ModelAPI("pinecone-service.cloudfoundry.com", "80",
				null);

		Category ca = new Category();
		ca.setName("Site");
		ca.setSubdomain(Category.SUB_DOMAIN_LEISURE);
		ca.setDomain(Category.DOMAIN_GAME);
		ca.setType(Category.COM);
		APIResponse response = api.create(ca);
		if (response.isDone()) {
			Category c = (Category) response.getMessage();

			Consumer con = new Consumer();
			con.setName("Sina");
			con.setNut(0);
			con.setVersion("1.0.0");
			con.setAlias("None");
			con.setCategory(c);
			// con.set
			// fo`}

		}
	}

	public void testAvatar() {
		try {
			FileInputStream fis = new FileInputStream(
					new File("D:\\config.png"));
			byte[] temp = new byte[1024 * 1024];
			int len = 0;
			int b = 0;
			while ((b = fis.read()) != -1) {
				temp[len] = (byte) b;
				len++;
			}
			byte[] array = new byte[len];
			for (int i = 0; i < len; i++) {
				array[i] = temp[i];
			}
			String base64 = Base64Utils.toBase64(array);
			base64 = base64.replace('$', '+');
			base64 = base64.replace('_', '/');
			System.out.println("Before save:"+base64);

			ModelAPI api = new ModelAPI("pinecone-service.cloudfoundry.com",
					"80", null);
			APIResponse response = api.show(User.class, "id=='2'");
			if (response.isDone()) {
				Collection<User> users = (Collection<User>) response.getMessage();
				for (User user : users) {
					System.out.println("user id:" + user.getId());
					System.out.println("user name:" + user.getName());
					System.out.println("user pwd:" + user.getPassword());
					System.out.println("user email:" + user.getEmail());
					System.out.println("user avatar:" + user.getAvatar());

					user.setAvatar(base64);
					
					response = api.update(user);
					
					if (response.isDone()) {
						User u = (User) response.getMessage();
						System.out.println("user id:" + u.getId());
						System.out.println("user name:" + u.getName());
						System.out.println("user pwd:" + u.getPassword());
						System.out.println("user email:" + u.getEmail());
						System.out.println("user avatar:" + u.getAvatar());
					}else{
						System.out.println(response.getMessage());
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
