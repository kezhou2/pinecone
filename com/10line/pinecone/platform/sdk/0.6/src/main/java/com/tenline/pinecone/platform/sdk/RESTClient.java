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

/**
 * @author Bill
 *
 */
public class RESTClient {

	private String baseUrl;	
	private HttpURLConnection connection;
	private final static int TIMEOUT = 10000;
	private ObjectMapper mapper = new ObjectMapper();
	
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
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String post(String path, Object content) throws Exception {
		String responseMessage;
		connection = (HttpURLConnection) new URL(baseUrl + path).openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		connection.setUseCaches(false);
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		mapper.writeValue(connection.getOutputStream(), content);
        responseMessage = connection.getResponseMessage();
		connection.disconnect();
		return responseMessage;
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public String delete(String path) throws Exception {
		String responseMessage;
		connection = (HttpURLConnection) new URL(baseUrl + path).openConnection();
		connection.setRequestMethod("DELETE");
		connection.setConnectTimeout(TIMEOUT);
		connection.connect();
		responseMessage = connection.getResponseMessage();
		connection.disconnect();
		return responseMessage;
	}
	
	/**
	 * 
	 * @param path
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String put(String path, Object content) throws Exception {
		String responseMessage;
		connection = (HttpURLConnection) new URL(baseUrl + path).openConnection();
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
        responseMessage = connection.getResponseMessage();
		connection.disconnect();
		return responseMessage;
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Entity> get(String path) throws Exception {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		connection = (HttpURLConnection) new URL(baseUrl + path).openConnection();
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
						entities.add(get(getLink(linkNodes.next()).getHref()).get(0));
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
