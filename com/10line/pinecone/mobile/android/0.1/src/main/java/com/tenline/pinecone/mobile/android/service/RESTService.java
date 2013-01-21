/**
 * 
 */
package com.tenline.pinecone.mobile.android.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
	public static final String LOGIN_URL = "/j_spring_security_check";
	public static final String LOGOUT_URL = "/j_spring_security_logout";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i(getClass().getSimpleName(), "onBind");
		template = new RestTemplate();
		template.getMessageConverters().add(new FormHttpMessageConverter());
		template.getMessageConverters().add(new StringHttpMessageConverter());
		template.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
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
	 * @throws Exception
	 */
	public Object post(String path, Object... content) throws Exception {
		List<Object> params = Arrays.asList(content);
		if (path.contains(LOGIN_URL)) {
			MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
			form.add("j_username", params.get(0).toString());
			form.add("j_password", params.get(1).toString());
			return get(template.postForLocation(baseUrl + path, form).toString().substring(baseUrl.length())).toString();
		} else {
			if (params.get(0) instanceof Entity) {
				return get(template.postForLocation(baseUrl + "/rest" + path, params.get(0)).toString().substring((baseUrl + "/rest").length()));
			} else {
				HttpHeaders headers = new HttpHeaders();
				headers.set("Content-Type", "text/uri-list; charset=utf-8");
				return template.postForObject(baseUrl + "/rest" + path, new HttpEntity<Object>(baseUrl + "/rest" + params.get(0), headers), String.class);
			}
		}
	}
	
	/**
	 * 
	 * @param path
	 * @throws Exception
	 */
	public void delete(String path) throws Exception {
		template.delete(baseUrl + "/rest" + path);
	}
	
	/**
	 * 
	 * @param path
	 * @param content
	 * @throws Exception
	 */
	public void put(String path, Object content) throws Exception {
		HttpHeaders headers = new HttpHeaders();
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
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object get(String path) throws Exception {
		Log.i(getClass().getSimpleName(), path);
		if (path.contains("spring_security") || path.contains("index.html")) {
			return template.getForObject(baseUrl + path, String.class);
		} else {
			ArrayList<Entity> entities = new ArrayList<Entity>();
			JsonNode rootNode = mapper.readTree(template.getForObject(baseUrl + "/rest" + path, String.class));
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
	 * @throws Exception
	 */
	private Link getLink(JsonNode node) throws Exception {
		Link link = mapper.readValue(node, Link.class);
		if (!link.getRel().equals("self")) {link.setRel(link.getRel().substring(link.getRel().lastIndexOf(".") + 1));}
		link.setHref(link.getHref().substring((baseUrl + "/rest").length()));
		return link;
	}

}
