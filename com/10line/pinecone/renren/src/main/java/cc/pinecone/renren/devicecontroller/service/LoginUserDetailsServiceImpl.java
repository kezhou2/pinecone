//package cc.pinecone.renren.devicecontroller.service;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//public class LoginUserDetailsServiceImpl implements LoginUserDetailsService {
//
//	private UserAccountService userAccountService;
//	 
//    /**
//     *
//     */
//    public LoginUserDetailsServiceImpl()
//    {
//    }
// 
//    /**
//     * getter method
//     *
//     * @see LoginUserDetailsServiceImpl#userAccountService
//     * @return the userAccountService
//     */
//    public UserAccountService getUserAccountService()
//    {
//        return userAccountService;
//    }
// 
//    /**
//     * 功能描述：查找登录的用户
//     * <p>
//     * 前置条件：
//     * <p>
//     * 方法影响：
//     * <p>
//     * Author , 2012-9-26
//     *
//     * @since server 2.0
//     * @param username
//     * @return
//     */
//    public UserDetails loadUserByUsername(String username, String password) throws UsernameNotFoundException
//    {
//        boolean result = userAccountService.validate(username, password);
//        if (!result)
//        {
//            return null;
//        }
// 
//        List authorities = new ArrayList();
//        GrantedAuthorityImpl grantedAuthorityImpl = new GrantedAuthorityImpl();
//        authorities.add(grantedAuthorityImpl);
//        LoginUserDetailsImpl user = new LoginUserDetailsImpl(username, password, authorities);
//        grantedAuthorityImpl.setDelegate(user);
//    }
// 
//    /**
//     * setter method
//     *
//     * @see LoginUserDetailsServiceImpl#userAccountService
//     * @param userAccountService
//     *            the userAccountService to set
//     */
//    public void setUserAccountService(UserAccountService userAccountService)
//    {
//        this.userAccountService = userAccountService;
//    }
//}
