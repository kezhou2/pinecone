/**
 * 
 */
package com.tenline.pinecone.platform.web.service.restful;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthMessage;
import net.oauth.OAuthProblemException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.tenline.pinecone.platform.web.service.OAuthService;
import com.tenline.pinecone.platform.web.service.oauth.OAuthException;
import com.tenline.pinecone.platform.web.service.oauth.OAuthProvider;
import com.tenline.pinecone.platform.web.service.oauth.OAuthRequestToken;
import com.tenline.pinecone.platform.web.service.oauth.OAuthToken;
import com.tenline.pinecone.platform.web.service.oauth.OAuthUtils;
import com.tenline.pinecone.platform.web.service.oauth.OAuthValidator;

/**
 * @author Bill
 *
 */
@Service
public class OAuthRestfulService implements OAuthService, ApplicationContextAware {

	private OAuthProvider provider;
	private OAuthValidator validator;
		
	/**
	 * 
	 */
	public OAuthRestfulService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void serveRequestToken(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub
		OAuthMessage message = OAuthUtils.readMessage(req);
		try{
			// require some parameters
			message.requireParameters(OAuth.OAUTH_CONSUMER_KEY,
					OAuth.OAUTH_SIGNATURE_METHOD,
					OAuth.OAUTH_SIGNATURE,
					OAuth.OAUTH_TIMESTAMP,
					OAuth.OAUTH_NONCE);
			
			String consumerKey = message.getParameter(OAuth.OAUTH_CONSUMER_KEY);
			// load the OAuth Consumer
			com.tenline.pinecone.platform.model.Consumer consumer = provider.getConsumer(consumerKey);
			
			// create some structures for net.oauth
			OAuthConsumer _consumer = new OAuthConsumer(null, consumerKey, consumer.getSecret(), null);
			OAuthAccessor accessor = new OAuthAccessor(_consumer);
			
			// validate the message
			validator.validateMessage(message, accessor, null);

			// create a new Request Token
			String callbackURI = message.getParameter(OAuth.OAUTH_CALLBACK);
			if (callbackURI != null && consumer.getConnectURI() != null
			        && !callbackURI.startsWith(consumer.getConnectURI())) {
			    throw new OAuthException(400, "Wrong callback URI");
			}
			OAuthToken token = provider.makeRequestToken(consumerKey, 
			                            callbackURI, 
			                            req.getParameterValues("xoauth_scope"),
			                            req.getParameterValues("xoauth_permission"));

			// send the Token information to the Client
			OAuthUtils.sendValues(resp, OAuth.OAUTH_TOKEN, token.getToken(),OAuth.OAUTH_TOKEN_SECRET, token.getSecret(), OAuthUtils.OAUTH_CALLBACK_CONFIRMED_PARAM, "true");
			resp.setStatus(HttpURLConnection.HTTP_OK);
		} catch (OAuthException x) {
			OAuthUtils.makeErrorResponse(resp, x.getMessage(), x.getHttpCode(), provider);
		} catch (OAuthProblemException x) {
			OAuthUtils.makeErrorResponse(resp, x.getProblem(), OAuthUtils.getHttpCode(x), provider);
		} catch (Exception x) {
			OAuthUtils.makeErrorResponse(resp, x.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR, provider);
		}
	}

	@Override
	public void serveTokenAuthorization(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub  
        try{
            String[] values = req.getParameterValues(OAuth.OAUTH_TOKEN);
            if (values == null || values.length != 1) {
                resp.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
                return;
            }
            String requestTokenKey = values[0];
            
            OAuthRequestToken requestToken = provider.getRequestToken(null, requestTokenKey);
            com.tenline.pinecone.platform.model.Consumer consumer = requestToken.getConsumer();
            
            // build the end user authentication and token authorization form
            String acceptHeader = req.getHeader("Accept");
            // TODO : properly check accept values, also support JSON
            String format = acceptHeader == null || acceptHeader.startsWith("application/xml") ? "xml" : "html";
            
            requestEndUserConfirmation(req, resp, consumer, requestToken, format);
            
        } catch (Exception x) {
            OAuthUtils.makeErrorResponse(resp, x.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR, provider);
        }
	}

	@Override
	public void serveTokenAuthorizationConfirmation(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub 
        try{
            String[] values = req.getParameterValues(OAuth.OAUTH_TOKEN);
            if (values == null || values.length != 1) {
                resp.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
                return;
            }
            String requestTokenKey = values[0];
            
            OAuthRequestToken requestToken = provider.getRequestToken(null, requestTokenKey);
            com.tenline.pinecone.platform.model.Consumer consumer = requestToken.getConsumer();
            
            values = req.getParameterValues("xoauth_end_user_decision");
            if (values == null || values.length != 1) {
                resp.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
                return;
            }
            
            boolean authorized = "yes".equals(values[0]) || "true".equals(values[0]);
            
            String callback = requestToken.getCallback();
            if (authorized) 
            {
                String verifier = provider.authoriseRequestToken(consumer.getKey(), requestToken.getToken());
                
                if (callback == null) {
                    OAuthUtils.sendValues(resp, OAuth.OAUTH_TOKEN, requestTokenKey, OAuth.OAUTH_VERIFIER, verifier);
                    resp.setStatus(HttpURLConnection.HTTP_OK);
                } else {
                    List<OAuth.Parameter> parameters = new ArrayList<OAuth.Parameter>();
                    parameters.add(new OAuth.Parameter(OAuth.OAUTH_TOKEN, requestTokenKey));
                    parameters.add(new OAuth.Parameter(OAuth.OAUTH_VERIFIER, verifier));
                    String location = OAuth.addParameters(callback, parameters);
                    resp.addHeader("Location", location);
                    resp.setStatus(302);
                }
            } 
            else
            {
                // TODO : make sure this response is OAuth compliant 
                OAuthUtils.makeErrorResponse(resp, "Token has not been authorized", 503, provider);
            }
        } catch (Exception x) {
            OAuthUtils.makeErrorResponse(resp, x.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR, provider);
        }
	}

	@Override
	public void serveAccessToken(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub
		OAuthMessage message = OAuthUtils.readMessage(req);
		try{
			// request some parameters
			message.requireParameters(OAuth.OAUTH_CONSUMER_KEY,
					OAuth.OAUTH_TOKEN,
					OAuth.OAUTH_SIGNATURE_METHOD,
					OAuth.OAUTH_SIGNATURE,
					OAuth.OAUTH_TIMESTAMP,
					OAuth.OAUTH_NONCE,
					OAuthUtils.OAUTH_VERIFIER_PARAM);
			
			// load some parameters
			String consumerKey = message.getParameter(OAuth.OAUTH_CONSUMER_KEY);
			String requestTokenString = message.getParameter(OAuth.OAUTH_TOKEN);
			String verifier = message.getParameter(OAuth.OAUTH_VERIFIER);
			
			// get the Request Token to exchange
			OAuthToken requestToken = provider.getRequestToken(consumerKey, requestTokenString);
			
			// build some structures for net.oauth
			OAuthConsumer consumer = new OAuthConsumer(null, consumerKey, requestToken.getConsumer().getSecret(), null);
			OAuthAccessor accessor = new OAuthAccessor(consumer);
			accessor.requestToken = requestTokenString;
			accessor.tokenSecret = requestToken.getSecret();

			// verify the message signature
			validator.validateMessage(message, accessor, requestToken);

			// exchange the Request Token
			OAuthToken tokens = provider.makeAccessToken(consumerKey, requestTokenString, verifier);

			// send the Access Token
			OAuthUtils.sendValues(resp, OAuth.OAUTH_TOKEN, tokens.getToken(),OAuth.OAUTH_TOKEN_SECRET, tokens.getSecret());
			resp.setStatus(HttpURLConnection.HTTP_OK);
		} catch (OAuthException x) {
			OAuthUtils.makeErrorResponse(resp, x.getMessage(), x.getHttpCode(), provider);
		} catch (OAuthProblemException x) {
			OAuthUtils.makeErrorResponse(resp, x.getProblem(), OAuthUtils.getHttpCode(x), provider);
		} catch (Exception x) {
			OAuthUtils.makeErrorResponse(resp, x.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR, provider);
		}
	}
	
	private void requestEndUserConfirmation(HttpServletRequest req,
            HttpServletResponse resp, 
            com.tenline.pinecone.platform.model.Consumer consumer,
            OAuthRequestToken requestToken,
            String format) {
		if ("xml".equals(format))
		{
			// TODO : try to get a default XSLT template, if found then use it
			// and only use in code formatting if no template is available

			String uri = getAuthorizationConfirmURI(req, requestToken.getToken());
			StringBuilder sb = new StringBuilder();
			sb.append("<tokenAuthorizationRequest xmlns=\"http://org.jboss.com/resteasy/oauth\" ")
			.append("replyTo=\"").append(uri).append("\">");
			sb.append("<consumerId>").append(consumer.getKey()).append("</consumerId>");
			if (consumer.getName() != null) {
				sb.append("<consumerName>").append(consumer.getName()).append("</consumerName>");
			}
			if (requestToken.getScopes() != null) {
				sb.append("<scopes>").append(requestToken.getScopes()[0]).append("</scopes>");
			}
			if (requestToken.getPermissions() != null) {
				sb.append("<permissions>").append(requestToken.getPermissions()[0]).append("</permissions>");
			}
			sb.append("</tokenAuthorizationRequest>");
			try {
				resp.getWriter().append(sb.toString());
				resp.setStatus(HttpURLConnection.HTTP_OK);
			} catch (IOException ex) {
				resp.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
			}
		} else if ("html".equals(format)) {
			// TODO : try to get a default XSLT template creating XHTML output, if found then use it
			// and redirect if no template is available
			RequestDispatcher dispatcher = req.getRequestDispatcher(DEFAULT_TOKEN_HTML_RESOURCE);
			if (dispatcher == null) {
				resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
				return;
			}
			try {
				req.setAttribute("oauth_consumer_id", consumer.getKey());
				req.setAttribute("oauth_consumer_display", consumer.getName());
				req.setAttribute("oauth_consumer_scopes", requestToken.getScopes());
				req.setAttribute("oauth_consumer_permissions", requestToken.getPermissions());
				req.setAttribute("oauth_request_token", requestToken.getToken());
				req.setAttribute("oauth_token_confirm_uri", getAuthorizationConfirmURI(req, null));
				dispatcher.forward(req, resp);
			} catch (Exception ex) {
				resp.setStatus(500);
			}
		}
		//else if ("json".equals(format)) {
		//}
	}
	
	private String getAuthorizationConfirmURI(HttpServletRequest req, String tokenKey) {
	    String requestURI = req.getRequestURL().toString();
        int index = requestURI.lastIndexOf(TOKEN_AUTHORIZATION_URL);
        String baseURI = requestURI.substring(0, index);
        String uri = baseURI + TOKEN_AUTHORIZATION_CONFIRM_URL;
        if (tokenKey != null) 
        {
            uri += ("?" + OAuth.OAUTH_TOKEN + "=" + OAuthUtils.encodeForOAuth(tokenKey));
        }
        return uri;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub
		provider = OAuthUtils.getOAuthProvider(arg0);
		validator = OAuthUtils.getValidator(provider);
	}

}
