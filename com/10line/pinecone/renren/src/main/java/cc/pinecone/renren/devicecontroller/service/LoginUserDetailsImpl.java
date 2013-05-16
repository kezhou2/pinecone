package cc.pinecone.renren.devicecontroller.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginUserDetailsImpl extends User implements UserDetails {
	/**
    *
    */
   private static final long serialVersionUID = -5424897749887458053L;

   /**
    * @param username
    * @param password
    * @param enabled
    * @param accountNonExpired
    * @param credentialsNonExpired
    * @param accountNonLocked
    * @param authorities
    */
   public LoginUserDetailsImpl(String username, String password, boolean enabled, boolean accountNonExpired,
                               boolean credentialsNonExpired, boolean accountNonLocked,
                               Collection<? extends GrantedAuthority> authorities)
   {
       super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
   }

   /**
    * @param username
    * @param password
    * @param authorities
    */
   public LoginUserDetailsImpl(String username, String password, Collection<? extends GrantedAuthority> authorities)
   {
       super(username, password, authorities);
   }

   /**
    * @param username
    * @param password
    * @param authorities
    */
   public LoginUserDetailsImpl(String username, String password)
   {
       super(username, password, new ArrayList<GrantedAuthority>());
   }
}
