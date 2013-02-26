/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.model.Link;

import sun.misc.BASE64Encoder;

/**
 * @author Bill
 *
 */
@SuppressWarnings("restriction")
public class RESTClient {

	private String baseUrl;	
	private HttpURLConnection connection;
	private final static int TIMEOUT = 10000;
	private ObjectMapper mapper = new ObjectMapper();
	private BASE64Encoder encoder = new BASE64Encoder();
	
	/**
	 * 
	 * @param baseUrl
	 */
	public RESTClient(String baseUrl) {
		// TODO Auto-generated constructor stub
		this.baseUrl = baseUrl;
		this.baseUrl += "/rest";
	}
	
	/**
	 * 
	 * @param path
	 * @param username
	 * @param password
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String post(String path, String username, String password, Object content) throws Exception {
		connection = (HttpURLConnection) new URL(baseUrl + path).openConnection();
		connection.setRequestProperty("Authorization", "Basic " + encoder.encode((username + ":" + password).getBytes()));
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		if (content instanceof Entity) {
			connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		} else if (content instanceof String) {
			connection.setRequestProperty("Content-Type", "text/uri-list; charset=utf-8");
		}
		connection.setUseCaches(false);
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (content instanceof Entity) {
			mapper.writeValue(connection.getOutputStream(), content);
		} else if (content instanceof String) {
			connection.getOutputStream().write((baseUrl + content.toString()).getBytes());
			connection.getOutputStream().flush();
	        connection.getOutputStream().close();
		}
		if (connection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
			if (content instanceof Entity) {
				String location = connection.getHeaderField("Location");
				return location.substring(location.lastIndexOf("/") + 1);
			}
		}
		connection.disconnect();
		return connection.getResponseMessage();
	}
	
	/**
	 * 
	 * @param path
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public String delete(String path, String username, String password) throws Exception {
		connection = (HttpURLConnection) new URL(baseUrl + path).openConnection();
		connection.setRequestProperty("Authorization", "Basic " + encoder.encode((username + ":" + password).getBytes()));
		connection.setRequestMethod("DELETE");
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		connection.disconnect();
		return connection.getResponseMessage();
	}
	
	/**
	 * 
	 * @param path
	 * @param username
	 * @param password
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String put(String path, String username, String password, Object content) throws Exception {
		connection = (HttpURLConnection) new URL(baseUrl + path).openConnection();
		connection.setRequestProperty("Authorization", "Basic " + encoder.encode((username + ":" + password).getBytes()));
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		if (content instanceof Entity) {
			connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		} else if (content instanceof String) {
			connection.setRequestProperty("Content-Type", "text/uri-list; charset=utf-8");
		}
		connection.setUseCaches(false);
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (content instanceof Entity) {
			mapper.writeValue(connection.getOutputStream(), content);
		} else if (content instanceof String) {
			connection.getOutputStream().write((baseUrl + content.toString()).getBytes());
			connection.getOutputStream().flush();
	        connection.getOutputStream().close();
		}
		connection.disconnect();
		return connection.getResponseMessage();
	}
	
	/**
	 * 
	 * @param path
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Entity> get(String path, String username, String password) throws Exception {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		connection = (HttpURLConnection) new URL(baseUrl + path).openConnection();
		connection.setRequestProperty("Authorization", "Basic " + encoder.encode((username + ":" + password).getBytes()));
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			JsonNode rootNode = mapper.readTree(connection.getInputStream());
			if (path.substring(path.lastIndexOf("/") + 1).matches("^-?\\d+$")) {
				entities.add(getEntity(rootNode));
			} else {
				if (rootNode.has("results")) {
					for (Iterator<JsonNode> results = rootNode.get("results").getElements(); results.hasNext();) {
						entities.add(getEntity(results.next()));
					}
				} else {
					for (Iterator<JsonNode> linkNodes = rootNode.get("_links").getElements(); linkNodes.hasNext();) {
						entities.add(get(getLink(linkNodes.next()).getHref(), username, password).get(0));
					}
				}
			}
		}
		connection.disconnect();
		return entities;
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 * @throws Exception
	 */
	private Entity getEntity(JsonNode node) throws Exception {
		Class<?> entityClass = null;
		for (JsonNode linkNode : node.get("_links")) {
			Link link = getLink(linkNode);
			if (link.getRel().equals("self")) {
				String className = link.getHref().substring(1, link.getHref().lastIndexOf("/"));
				className = className.substring(0, 1).toUpperCase() + className.substring(1);
				entityClass = Class.forName("com.tenline.pinecone.platform.model." + className);
			}
		}
		Entity entity = (Entity) mapper.readValue(node, entityClass);
		for (Link link : entity.get_links()) {
			link.setHref(link.getHref().substring(baseUrl.length()));
			if (link.getRel().equals("self")) {
				entity.setId(Long.valueOf(link.getHref().substring(link.getHref().lastIndexOf("/") + 1)));
			} else {
				link.setRel(link.getRel().substring(link.getRel().lastIndexOf(".") + 1));
			}
		}
		return entity;
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 * @throws Exception
	 */
	private Link getLink(JsonNode node) throws Exception {
		Link link = mapper.readValue(node, Link.class);
		if (!link.getRel().equals("self")) {link.setRel(link.getRel().substring(link.getRel().lastIndexOf(".") + 1));}
		link.setHref(link.getHref().substring(baseUrl.length()));
		return link;
	}
	
}
