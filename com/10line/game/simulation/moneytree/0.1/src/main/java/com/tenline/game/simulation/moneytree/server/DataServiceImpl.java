package com.tenline.game.simulation.moneytree.server;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.google.api.client.auth.oauth.OAuthCallbackUrl;
import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.param.impl.SessionKey;
import com.renren.api.client.services.PayService;
import com.tenline.game.simulation.moneytree.client.DataService;
import com.tenline.game.simulation.moneytree.shared.Constant;
import com.tenline.game.simulation.moneytree.shared.ExUser;
import com.tenline.game.simulation.moneytree.shared.Order;
import com.tenline.game.simulation.moneytree.shared.PlatformConfig;
import com.tenline.game.simulation.moneytree.shared.RenrenConfig;
import com.tenline.pinecone.platform.model.Application;
import com.tenline.pinecone.platform.model.Consumer;
import com.tenline.pinecone.platform.model.Transaction;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.sdk.ApplicationAPI;
import com.tenline.pinecone.platform.sdk.ConsumerAPI;
import com.tenline.pinecone.platform.sdk.TransactionAPI;
import com.tenline.pinecone.platform.sdk.UserAPI;
import com.tenline.pinecone.platform.sdk.development.APIResponse;
import com.tenline.pinecone.platform.sdk.oauth.AuthorizationAPI;

@SuppressWarnings("serial")
public class DataServiceImpl extends RemoteServiceServlet implements
		DataService {

	protected Logger logger = Logger.getLogger(getClass().getName());
	
	private OrderPersistence orderPersistence = null;

	private Consumer thisConsumer = null;

	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		super.init();
		
		System.out.println("Cur dir:"+System.getProperty("user.dir"));

		AuthorizationAPI authorizationAPI = new AuthorizationAPI(
				PlatformConfig.HOST, PlatformConfig.PORT, null);
		APIResponse response = authorizationAPI.requestToken(
				PlatformConfig.APP_STORE_CONSUMER_KEY,
				PlatformConfig.APP_STORE_CONSUMER_SECRET, null);
		if (response.isDone()) {
			PlatformConfig.APP_STORE_TOKEN = ((OAuthCredentialsResponse) response
					.getMessage()).token;
			PlatformConfig.APP_STORE_SECRET = ((OAuthCredentialsResponse) response
					.getMessage()).tokenSecret;
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = authorizationAPI
				.authorizeToken(PlatformConfig.APP_STORE_TOKEN);
		if (response.isDone()) {
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = authorizationAPI.confirmAuthorization(
				PlatformConfig.APP_STORE_TOKEN, "yes");
		if (response.isDone()) {
			PlatformConfig.APP_STORE_TOKEN = ((OAuthCallbackUrl) response
					.getMessage()).token;
			PlatformConfig.APP_STORE_VERIFIER = ((OAuthCallbackUrl) response
					.getMessage()).verifier;
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}
		response = authorizationAPI.accessToken(
				PlatformConfig.APP_STORE_CONSUMER_KEY,
				PlatformConfig.APP_STORE_CONSUMER_SECRET,
				PlatformConfig.APP_STORE_TOKEN,
				PlatformConfig.APP_STORE_SECRET,
				PlatformConfig.APP_STORE_VERIFIER);
		if (response.isDone()) {
			PlatformConfig.APP_STORE_TOKEN = ((OAuthCredentialsResponse) response
					.getMessage()).token;
			PlatformConfig.APP_STORE_SECRET = ((OAuthCredentialsResponse) response
					.getMessage()).tokenSecret;
		} else {
			logger.log(Level.SEVERE, response.getMessage().toString());
		}

		ConsumerAPI api = new ConsumerAPI(PlatformConfig.HOST,
				PlatformConfig.PORT, null);

		try {
			response = api.show("id=='" + PlatformConfig.APP_STORE_CONSUMER_ID
					+ "'");

			if (response.isDone()) {
				Collection<Consumer> cons = (Collection<Consumer>) response
						.getMessage();
				for (Consumer con : cons) {
					thisConsumer = con;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			orderPersistence = OrderPersistence.getInstance();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public Date getPlantDate(Application app) {
		TransactionAPI tapi = new TransactionAPI(PlatformConfig.HOST,
				PlatformConfig.PORT, null);
		try {
			APIResponse response = tapi.showByApplication("id=='" + app.getId()
					+ "'");

			if (response.isDone()) {
				Collection<Transaction> trans = (Collection<Transaction>) response
						.getMessage();
				for (Transaction t : trans) {
					if (t.getType().equals(Transaction.PAYOUT)) {
						return t.getTimestamp();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public boolean plantGoldTree(Application app) {
		app.getUser().setNut(1); // TODO

		if (app.getUser().getNut() > 0) {
			TransactionAPI tapi = new TransactionAPI(PlatformConfig.HOST,
					PlatformConfig.PORT, null);
			try {
				Transaction t = new Transaction();
				t.setApplication(app);
				t.setNut(PlatformConfig.DOU_PER_SEED);
				t.setTimestamp(new Date());
				t.setType(Transaction.PAYOUT);

				APIResponse response = tapi.create(t);
				if (response.isDone()) {
					System.out.println(response.getMessage());
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;

	}

	@SuppressWarnings("unchecked")

	public int harvest(Application app) {
		TransactionAPI tapi = new TransactionAPI(PlatformConfig.HOST,
				PlatformConfig.PORT, null);
		try {
			APIResponse response = tapi.showByApplication("id=='" + app.getId()
					+ "'");
			if (response.isDone()) {
				Collection<Transaction> tans = (Collection<Transaction>) response
						.getMessage();
				for (Transaction t : tans) {
					// 如果已经种植了且树已经成熟，则调用薛亮提供的算法，计算用户本次收获的Nut
					if (t.getType().equals(Transaction.PAYOUT)) {
						Date now = new Date();
						if (now.getTime() >= t.getTimestamp().getTime()) {
							// TODO 调用算法获得用户本次种植的收获
							Random ran = new Random();
							app.getUser().setNut(
									app.getUser().getNut() + ran.nextInt(10));

							UserAPI uapi = new UserAPI(PlatformConfig.HOST,
									PlatformConfig.PORT, null);
							response = uapi.update(app.getUser());
							if (response.isDone()) {
								System.out.println(response.getMessage());
							}

							// 删除Transaction
							response = tapi.delete(t.getId());
							if (response.isDone()) {
								System.out.println(response.getMessage());
							}

							return app.getUser().getNut();
						} else {
							return -1;
						}
					}
				}
			} else {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public String getGoldTreeStatus(Application app) {
		TransactionAPI tapi = new TransactionAPI(PlatformConfig.HOST,
				PlatformConfig.PORT, null);

		try {
			APIResponse response = tapi.showByApplication("id=='" + app.getId()
					+ "'");

			if (response.isDone()) {

				Collection<Transaction> tans = (Collection<Transaction>) response
						.getMessage();
				for (Transaction t : tans) {
					// 如果已经种植了且树已经成熟，则调用薛亮提供的算法，计算用户本次收获的Nut
					System.out.println("trans:" + t + " date:"
							+ t.getTimestamp());
					if (t.getType().equals(Transaction.PAYOUT)) {
						Date now = new Date();
						if (now.getTime() >= (t.getTimestamp().getTime() + Constant.HARVEST_TIME)) {
							return Constant.STATUS_HARVESTED;
						} else {
							return Constant.STATUS_GROWING;
						}
					}
				}

				return Constant.STATUS_NOT_PLANT;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Constant.STATUS_NOT_PLANT;
	}

	@SuppressWarnings("unchecked")
	public ExUser getUser(String userId) {
		/**
		 * TODO 从人人网API获取用户信息
		 */
		ExUser user = new ExUser();

		/**
		 * 获取本应用的用户信息
		 */
		UserAPI uapi = new UserAPI(PlatformConfig.HOST, PlatformConfig.PORT,
				null);
		try {
			APIResponse response = uapi.show("name=='" + userId + "'");

			if (response.isDone()) {
				Collection<User> users = (Collection<User>) response
						.getMessage();
				for (User u : users) {

					user.setAvatar(u.getAvatar());
					user.setEmail(u.getEmail());
					user.setName(u.getName());
					user.setNut(u.getNut());
					user.setPassword(u.getPassword());

					return user;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("unchecked")

	public Application initUser(String userId) {
		System.out.println("initUser()");
		/**
		 * 获取本应用的用户信息
		 */
		UserAPI uapi = new UserAPI(PlatformConfig.HOST, PlatformConfig.PORT,
				null);
		try {
			APIResponse response = uapi.show("name=='" + userId + "'");

			if (response.isDone()) {
				@SuppressWarnings("unchecked")
				Collection<User> users = (Collection<User>) response
						.getMessage();
				// 如果用户不存在
				if (users.size() == 0) {
					User user = new User();
					user.setName(userId);
					user.setPassword("unknown");
					user.setEmail("unknown");
					user.setNut(0);
					user.setAvatar(new byte[100]);
					response = uapi.create(user);
					if (response.isDone()) {
						System.out.println(response.getMessage());
						// 用户安装consumer对应的application
						ApplicationAPI aapi = new ApplicationAPI(
								PlatformConfig.HOST, PlatformConfig.PORT, null);
						Application app = new Application();
						app.setConsumer(thisConsumer);
						app.setDefault(false);
						app.setStatus(Application.CLOSED);
						app.setUser(user);
						response = aapi.create(app);
						if (response.isDone()) {
							System.out.println(response.getMessage());
							Application a = (Application) response.getMessage();
							return a;
						}
					}
				} else {
					for (User user : users) {
						ApplicationAPI aapi = new ApplicationAPI(
								PlatformConfig.HOST, PlatformConfig.PORT, null);
						response = aapi
								.showByUser("id=='" + user.getId() + "'");
						if (response.isDone()) {
							System.out.println(response.getMessage());
							Collection<Application> apps = (Collection<Application>) response
									.getMessage();
							if (apps.size() == 0) {
								Application app = new Application();
								app.setConsumer(thisConsumer);
								app.setDefault(false);
								app.setStatus(Application.CLOSED);
								app.setUser(user);
								response = aapi.create(app);
								if (response.isDone()) {
									System.out.println(response.getMessage());
									Application a = (Application) response
											.getMessage();
									return a;
								}
							} else {
								for (Application app : apps) {
									return app;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void buy(String _key) {
		System.out.println("step 1");

		System.out.println("step 2/3");
		RenrenApiClient apiClient = RenrenApiClient.getInstance();
		PayService ps = apiClient.getPayService();
		long orderId = (new Date()).getTime();
		String token = ps.regOrderTest(orderId, 100, "test", 0, new SessionKey(
				_key));

		System.out.println("step 4");
		Properties formProperties = new Properties();
		formProperties.put("xn_sig_user", "Admin");
		formProperties.put("xn_sig_skey", "manage");
		// byte[] b = NetUtils.requestPostForm(
		// "http://localhost:8080/zlex/j_spring_security_check",
		// formProperties);
		// System.err.println(new String(b, "utf-8"));
	}

	public String getRenrenOrderToken(String renrenSessionKey,
			String renrenUserId, int renrenDouNum, long orderId) {
		RenrenApiClient apiClient = RenrenApiClient.getInstance();
		PayService ps = apiClient.getPayService();
		String token = ps.regOrderTest(orderId, renrenDouNum
				/ PlatformConfig.DOU_PER_SEED, "PineconeNut", 0,
				new SessionKey(renrenSessionKey));

		// TODO 利用本地文件存储订单
		Order order = new Order();
		order.setOrderId(""+orderId);
		order.setUserId(renrenUserId);
		order.setAmount(""+renrenDouNum);
		order.setSessionKey(renrenSessionKey);
		try {
			orderPersistence.addOrder(order);
			orderPersistence.flush();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		return token;
	}

	public boolean submitRenrenOrder(String renrenSessionKey, String token,
			int renrenDouNum, long orderId) {

		Properties formProperties = new Properties();
		formProperties.put("app_id", RenrenConfig.APP_ID);
		formProperties.put("order_number", orderId);
		formProperties.put("token", token);
		formProperties.put("redirect_url",
				"http://apps.renren.com/moneytree/redirect");
		byte[] b;
		try {
			b = NetUtils.requestPostForm(RenrenConfig.APP_ID, formProperties);
			System.out.println(new String(b, "utf-8"));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
