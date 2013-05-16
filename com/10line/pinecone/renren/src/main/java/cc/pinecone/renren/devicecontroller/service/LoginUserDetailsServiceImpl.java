package cc.pinecone.renren.devicecontroller.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cc.pinecone.renren.devicecontroller.dao.PineconeApi;

public class LoginUserDetailsServiceImpl implements LoginUserDetailsService {

	private PineconeApi api;

	/**
     *
     */
	public LoginUserDetailsServiceImpl() {
		api = new PineconeApi();
	}

	public UserDetails loadUserByUsername(String username, String password)
			throws UsernameNotFoundException {
		System.out.println("loadUserByUsername");
		com.tenline.pinecone.platform.model.User user = api.login(username,
				password);
		System.out.println("loadUserByUsername");
		if (user != null) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			GrantedAuthorityImpl grantedAuthorityImpl = new GrantedAuthorityImpl();
			authorities.add(grantedAuthorityImpl);
			LoginUserDetailsImpl u = new LoginUserDetailsImpl(username,
					password, authorities);
			grantedAuthorityImpl.setDelegate(u);
			return u;
		}
		return null;
	}
}
