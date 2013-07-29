package cc.pinecone.renren.devicecontroller.service;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class LoginAuthenticationProvider extends
		AbstractUserDetailsAuthenticationProvider {

	private LoginUserDetailsService userDetailsService;

	public void setUserDetailsService(LoginUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		System.out.println("additionalAuthenticationChecks");
		if (authentication.getCredentials() == null) {
			throw new BadCredentialsException("Bad credentials:" + userDetails);
		}

		String presentedPassword = authentication.getCredentials().toString();
		System.out.println(presentedPassword);

		// TODO authenticate
		// if (!passwordEncoder.isPasswordValid(userDetails.getPassword(),
		// presentedPassword, salt))
		// {
		// logger.debug("Authentication failed: password does not match stored value");
		//
		// throw new BadCredentialsException("Bad credentials:" + userDetails);
		// }
	}

	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		UserDetails loadedUser;
		System.out.println("retrieveUser");
		try {
			String password = (String) authentication.getCredentials();
			loadedUser = getUserDetailsService().loadUserByUsername(username,
					password);
		} catch (UsernameNotFoundException notFound) {
			throw notFound;
		} catch (Exception repositoryProblem) {
			throw new AuthenticationServiceException(
					repositoryProblem.getMessage(), repositoryProblem);
		}

		if (loadedUser == null) {
			throw new AuthenticationServiceException(
					"UserDetailsService returned null, which is an interface contract violation");
		}
		return loadedUser;
	}

	protected LoginUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

}