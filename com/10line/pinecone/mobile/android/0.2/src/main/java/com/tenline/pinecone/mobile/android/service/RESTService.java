/**
 * 
 */
package com.tenline.pinecone.mobile.android.service;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.support.Base64;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.tenline.pinecone.platform.model.Entity;
import com.tenline.pinecone.platform.model.Link;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * @author Bill
 *
 */
public class RESTService extends AbstractService {

	private RestTemplate template;
	private ObjectMapper mapper = new ObjectMapper();
	private static final int TIMEOUT = 30 * 1000;
	public static final String LOGIN_URL = "/j_spring_security_check";
	public static final String LOGOUT_URL = "/j_spring_security_logout";
	private static String username; private static String password;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i(getClass().getSimpleName(), "onBind");
		template = new RestTemplate();
		template.getMessageConverters().add(new FormHttpMessageConverter());
		template.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		template.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		SimpleClientHttpRequestFactory factory =  (SimpleClientHttpRequestFactory) template.getRequestFactory();
		factory.setConnectTimeout(TIMEOUT); factory.setReadTimeout(TIMEOUT);
		return super.onBind(intent);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(getClass().getSimpleName(), "onDestroy");
		template = null; mapper = null;
	}
	
	/**
	 * 
	 * @param path
	 * @param content
	 * @return
	 * @throws RestClientException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Object post(String path, Object... content) throws RestClientException, JsonParseException, JsonMappingException, IOException, 
		ClassNotFoundException {
		List<Object> params = Arrays.asList(content);
		if (path.contains(LOGIN_URL)) {
			MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
			username = params.get(0).toString(); form.add("j_username", username);
			password = params.get(1).toString(); form.add("j_password", password);
			path = template.postForLocation(baseUrl + path, form).toString().substring(baseUrl.length());
			if (path.contains("spring_security")) {return get(path).toString();} return path;
		} else {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Basic " + Base64.encodeBytes("admin:admin".getBytes()));
			if (params.get(0) instanceof Entity) {
				headers.set("Content-Type", "application/json; charset=utf-8");
				URI uri = template.postForLocation(baseUrl + "/rest" + path, new HttpEntity<Object>(params.get(0), headers));
				return get(uri.toString().substring((baseUrl + "/rest").length()));
			} else {
				headers.set("Content-Type", "text/uri-list; charset=utf-8");
				return template.postForObject(baseUrl + "/rest" + path, new HttpEntity<Object>(baseUrl + "/rest" + params.get(0), headers), String.class);
			}
		}
	}
	
	/**
	 * 
	 * @param path
	 * @throws RestClientException
	 */
	public void delete(String path) throws RestClientException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Basic " + Base64.encodeBytes("admin:admin".getBytes()));
		template.exchange(baseUrl + "/rest" + path, HttpMethod.DELETE, new HttpEntity<Object>(headers), String.class);
	}
	
	/**
	 * 
	 * @param path
	 * @param content
	 * @throws RestClientException
	 */
	public void put(String path, Object content) throws RestClientException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Basic " + Base64.encodeBytes("admin:admin".getBytes()));
		if (content instanceof Entity) {
			headers.set("Content-Type", "application/json; charset=utf-8");
			template.put(baseUrl + "/rest" + path, new HttpEntity<Object>(content, headers));
		} else if (content instanceof String) {
			headers.set("Content-Type", "text/uri-list; charset=utf-8");
			template.put(baseUrl + "/rest" + path, new HttpEntity<Object>(baseUrl + "/rest" + content, headers));
		}
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 * @throws RestClientException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public Object get(String path) throws RestClientException, JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
		Log.i(getClass().getSimpleName(), path);
		if (path.contains("spring_security")) {
			if (path.contains(LOGOUT_URL)) {username = null; password = null;}
			return template.getForObject(baseUrl + path, String.class);
		} else {
			HttpHeaders headers = new HttpHeaders(); if (username == null) {username = "admin";} if (password == null) {password = "admin";}
			headers.set("Authorization", "Basic " + Base64.encodeBytes((username + ":" + password).getBytes()));
			ResponseEntity<String> response = template.exchange(baseUrl + "/rest" + path, HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
			JsonNode rootNode = mapper.readTree(response.getBody()); ArrayList<Entity> entities = new ArrayList<Entity>();
			if (path.substring(path.lastIndexOf("/") + 1).matches("^-?\\d+$")) {
				entities.add(getEntity(rootNode));
			} else {
				if (rootNode.has("results")) {
					for (Iterator<JsonNode> results = rootNode.get("results").getElements(); results.hasNext();) {
						entities.add(getEntity(results.next()));
					}
				} else {
					for (Iterator<JsonNode> linkNodes = rootNode.get("_links").getElements(); linkNodes.hasNext();) {
						entities.add(((ArrayList<Entity>) get(getLink(linkNodes.next()).getHref())).get(0));
					}
				}
			}
			return entities;
		}
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private Entity getEntity(JsonNode node) throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
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
			link.setHref(link.getHref().substring((baseUrl + "/rest").length()));
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
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private Link getLink(JsonNode node) throws JsonParseException, JsonMappingException, IOException {
		Link link = mapper.readValue(node, Link.class);
		if (!link.getRel().equals("self")) {link.setRel(link.getRel().substring(link.getRel().lastIndexOf(".") + 1));}
		link.setHref(link.getHref().substring((baseUrl + "/rest").length()));
		return link;
	}

}
