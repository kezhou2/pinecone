package cc.pinecone.renren.devicecontroller.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface LoginUserDetailsService
{
    /**
     * 功能描述：根据用户密码验证用户信息
     * <p>
     * 前置条件：
     * <p>
     * 方法影响：
     * <p>
     * Author , 2012-9-26
     *
     * @since server 2.0
     * @param username
     * @param password
     * @return
     * @throws UsernameNotFoundException
     */
    UserDetails loadUserByUsername(String username, String password) throws UsernameNotFoundException;
}
