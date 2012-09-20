package com.tenline.pinecone.platform.web.store.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import com.google.gwt.user.server.Base64Utils;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.Friend;
import com.tenline.pinecone.platform.model.Mail;
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
			System.out.println("*************************");
//			api.showAllFriends();
//			System.out.println("*************************");
//			api.showFriends();
//			api.deleteFriend(25);
			
//			api.showApplication();
//			api.deleteApplicaiton(22);
			
//			api.testAvatar();
			
			api.showAllMail();
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
				System.out.println("isDecided:" + f.getDecided());
				
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
		APIResponse response = api.show(Friend.class, "decided==false&&receiver.id=='1'");
		if (response.isDone()) {
			Collection<Friend> friends = (Collection<Friend>) response.getMessage();
			for(Friend f:friends){
				System.out.println("friend id:" + f.getId());
				System.out.println("sender name:" + f.getSender().getName());
				System.out.println("sender id:" + f.getSender().getId());
				System.out.println("receiver name:" + f.getReceiver().getName());
				System.out.println("receiver id:" + f.getReceiver().getId());
				System.out.println("isDecided:" + f.getDecided());
				
//				f.setDecided(true);
//				response = api.update(f);
			}
		}else{
			System.out.println(response.getMessage());
		}
	}
	
	public void deleteFriend(int id) throws Exception{
		ModelAPI api = new ModelAPI("pinecone-service.cloudfoundry.com", "80",
				null);
		APIResponse response = api.show(Friend.class, "id=='"+id+"'");
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
						System.out.println("isDecide:" + f.getDecided());
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
						System.out.println("isDecide:" + f.getDecided());
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
	
	@SuppressWarnings("unchecked")
	public void showApplication() throws Exception{
		ModelAPI api = new ModelAPI("pinecone-service.cloudfoundry.com", "80",
				null);
		APIResponse response = api.show(Application.class, "all");
		if (response.isDone()) {
			Collection<Application> apps = (Collection<Application>) response.getMessage();
			for (Application a : apps) {
				System.out.println("app id:" + a.getId());
				System.out.println("app status:" + a.getStatus());
				System.out.println("app consumer id:" + a.getConsumer().getId());
				System.out.println("app consumer name:" + a.getConsumer().getName());
			}

		}
	}
	
	@SuppressWarnings("unchecked")
	public void deleteApplicaiton(int id) throws Exception {
		ModelAPI api = new ModelAPI("pinecone-service.cloudfoundry.com", "80",
				null);
		APIResponse response = api.show(Application.class, "id=='"+id+"'");
		if (response.isDone()) {
			Collection<Application> apps = (Collection<Application>) response.getMessage();
			for (Application a : apps) {
				response = api.delete(a);
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
	
	public void showAllMail() throws Exception{
		ModelAPI api = new ModelAPI("pinecone-service.cloudfoundry.com", "80",
				null);
		APIResponse response = api.show(Mail.class, "all");
		if (response.isDone()) {
			Collection<Mail> mails = (Collection<Mail>) response.getMessage();
			for (Mail m : mails) {
				System.out.println("mail id:" + m.getId());
				System.out.println("mail title:" + m.getTitle());
				System.out.println("mail content:" + m.getContent());
				System.out.println("mail receiver id:" + m.getReceiver().getId());
				System.out.println("mail receiver name:" + m.getReceiver().getName());
				System.out.println("mail sender id:" + m.getSender().getId());
				System.out.println("mail sender name:" + m.getSender().getName());
				System.out.println("mail isRead:" + m.isRead());
			}

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

//					user.setAvatar(base64);
					user.setAvatar(null);
					
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
	
	public void testBug() throws Exception{
		
		ModelAPI api = new ModelAPI("pinecone-service.cloudfoundry.com", "80",null);
		
		String filter = "email=='liugy503@gmail.com'&&password=='198297'";
		APIResponse response = api.show(User.class, filter);
		if (response.isDone()) {
			Collection<User> users = (Collection<User>)response.getMessage();
			for(User u:users){
				System.out.println("###### login user:"+u.getEmail());
				response = api.show(Friend.class, "receiver.id=='"+u.getId()+"'");
				if(response.isDone()){
					Collection<Friend> friends = (Collection<Friend>)response.getMessage();
					for(Friend f:friends){
						System.out.println("friend id:" + f.getId());
						System.out.println("sender name:" + f.getSender().getName());
						System.out.println("sender id:" + f.getSender().getId());
						System.out.println("receiver name:" + f.getReceiver().getName());
						System.out.println("receiver id:" + f.getReceiver().getId());
						System.out.println("isDecided:" + f.getDecided());
						
						f.setDecided(true);
						response = api.update(f);
						if(response.isDone()){
							
							{
								System.out.println("###### after update:");
								APIResponse res = api.show(Friend.class, "receiver.id=='"+u.getId()+"'");
								if(res.isDone()){
									Collection<Friend> s = (Collection<Friend>)res.getMessage();
									for(Friend a:s){
										System.out.println("friend id:" + f.getId());
										System.out.println("sender name:" + f.getSender().getName());
										System.out.println("sender id:" + f.getSender().getId());
										System.out.println("receiver name:" + f.getReceiver().getName());
										System.out.println("receiver id:" + f.getReceiver().getId());
										System.out.println("isDecided:" + f.getDecided());
									}
								}
							}
							
							response = api.show(User.class, "email=='temp@gmail.com'&&password=='temp'");
							if(response.isDone()){
								Collection<User> us = (Collection<User>)response.getMessage();
								for(User uu:us){
									System.out.println("###### login user:"+uu.getEmail());
									response = api.show(Friend.class, "sender.id=='"+uu.getId()+"'");
									if(response.isDone()){
										Collection<Friend> fs = (Collection<Friend>)response.getMessage();
										for(Friend ff:fs){
											System.out.println("friend id:" + ff.getId());
											System.out.println("sender name:" + ff.getSender().getName());
											System.out.println("sender id:" + ff.getSender().getId());
											System.out.println("receiver name:" + ff.getReceiver().getName());
											System.out.println("receiver id:" + f.getReceiver().getId());
											System.out.println("isDecided:" + ff.getDecided());
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

}
