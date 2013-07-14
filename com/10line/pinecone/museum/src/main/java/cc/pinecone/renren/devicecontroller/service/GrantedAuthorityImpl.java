package cc.pinecone.renren.devicecontroller.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class GrantedAuthorityImpl implements GrantedAuthority {

	/**
     * ROLE USER 权限
     */
    private static final String ROLE_USER = "ROLE_USER";
 
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
 
    private UserDetails delegate;
 
    public GrantedAuthorityImpl(UserDetails user)
    {
        this.delegate = user;
    }
 
    /**
     *
     */
    public GrantedAuthorityImpl()
    {
    }
 
    public String getAuthority()
    {
        return ROLE_USER;
    }
 
    /**
     * setter method
     *
     * @see GrantedAuthorityImpl#delegate
     * @param delegate
     *            the delegate to set
     */
    public void setDelegate(UserDetails delegate)
    {
        this.delegate = delegate;
    }
 
    /**
     * getter method
     *
     * @see GrantedAuthorityImpl#delegate
     * @return the delegate
     */
    public UserDetails getDelegate()
    {
        return delegate;
    }

}
