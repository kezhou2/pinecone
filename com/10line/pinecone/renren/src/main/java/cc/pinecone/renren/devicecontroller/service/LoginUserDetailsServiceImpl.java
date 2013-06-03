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
		System.out.println("username:"+username+"|password:"+password);
		com.tenline.pinecone.platform.model.User user = api.login(username,
				password);
		if (user != null) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			GrantedAuthorityImpl grantedAuthorityImpl = new GrantedAuthorityImpl();
			authorities.add(grantedAuthorityImpl);
			LoginUserDetailsImpl u = new LoginUserDetailsImpl(""+user.getId(),username,
					password, authorities);
			grantedAuthorityImpl.setDelegate(u);
			return u;
		}
		return null;
	}
}
