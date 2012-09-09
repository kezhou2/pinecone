/**
 * 
 */
package com.tenline.pinecone.platform.web.store.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tenline.pinecone.platform.sdk.ModelAPI;

/**
 * @author Bill
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractService extends RemoteServiceServlet {

	protected static final String HOST = "pinecone-service.cloudfoundry.com";
	protected static final String PORT = "80";
	protected static final String CONTEXT = null;
	
	protected static final String consumerKey = "88384d8e-ee4b-4ab7-af69-bf9070633ba7";
	protected static final String consumerSecret = "3ce8b5a8-a465-4a43-8ee2-65e899838d86";
	protected static String token;
	protected static String tokenSecret;
	protected static String verifier;
	
//	private AuthorizationAPI authorizationAPI;
	protected ModelAPI modelAPI;
	public AbstractService(){
//		authorizationAPI = new AuthorizationAPI(HOST, PORT, CONTEXT);
//		APIResponse response = authorizationAPI.requestToken(consumerKey, consumerSecret, null);
//        if (response.isDone()) {
//                token = ((OAuthCredentialsResponse) response.getMessage()).token;
//                tokenSecret = ((OAuthCredentialsResponse) response.getMessage()).tokenSecret;
//                System.out.println("token:"+token);
//                System.out.println("tokenSecret:"+tokenSecret);
//        } else {
//                System.out.println(response.getMessage().toString());
//        }
//        response = authorizationAPI.authorizeToken(token);
//        if (response.isDone()) {
//                System.out.println(response.getMessage());
//        } else {
//                System.out.println(response.getMessage().toString());
//        }
//        response = authorizationAPI.confirmAuthorization(token, "yes");
//        if (response.isDone()) {
//                token = ((OAuthCallbackUrl) response.getMessage()).token;
//                String verifier = ((OAuthCallbackUrl) response.getMessage()).verifier;
//                System.out.println("verifier:"+verifier);
//        } else {
//                System.out.println(response.getMessage().toString());
//        }
//        response = authorizationAPI.accessToken(consumerKey, consumerSecret, token, tokenSecret, verifier);
//        if (response.isDone()) {
//                token = ((OAuthCredentialsResponse) response.getMessage()).token;
//                tokenSecret = ((OAuthCredentialsResponse) response.getMessage()).tokenSecret;
//                System.out.println("token:"+token);
//                System.out.println("tokenSecret:"+tokenSecret);
//        } else {
//                System.out.println(response.getMessage().toString());
//        }
        
        modelAPI = new ModelAPI(HOST, PORT, CONTEXT);
	}
}
