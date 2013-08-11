package com.tenline.pinecone.platform.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tenline.pinecone.platform.Constant;
import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.RESTClient;

public class LoginUserDetailsServiceImpl implements LoginUserDetailsService {

	private RESTClient client;

	/**
     *
     */
	public LoginUserDetailsServiceImpl() {
		client = new RESTClient(Constant.REST_URL);
	}

	public UserDetails loadUserByUsername(String username, String password)
			throws UsernameNotFoundException {
		System.out.println("loadUserByUsername");
		System.out.println("username:"+username+"|password:"+password);
		try{
			ArrayList<Entity> users = (ArrayList<Entity>) client.get(
					"/user/search/names?name=" + username, Constant.getAdminId(), Constant.getAdminPassword());
			System.out.println("login:"+users.size());
			for (Entity en : users) {
				if (en instanceof User) {
					User user = (User) en;
					User u = (User) client.get("/user/" + user.getId(), username, password).toArray()[0];
					List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
					GrantedAuthorityImpl grantedAuthorityImpl = new GrantedAuthorityImpl();
					authorities.add(grantedAuthorityImpl);
					LoginUserDetailsImpl userDetail = new LoginUserDetailsImpl(""+u.getId(),username,
							password, authorities);
					grantedAuthorityImpl.setDelegate(userDetail);
					return userDetail;
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return null;
	}
}
