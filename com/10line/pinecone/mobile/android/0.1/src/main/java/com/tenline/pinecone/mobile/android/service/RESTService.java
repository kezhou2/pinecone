/**
 * 
 */
package com.tenline.pinecone.mobile.android.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.tenline.pinecone.platform.model.Entity;

import android.content.Intent;
import android.os.IBinder;

/**
 * @author Bill
 *
 */
public class RESTService extends AbstractService {

	private RestTemplate template;
	private String baseUrl = "http://pinecone-service.cloudfoundry.com";
	public static final String LOGIN_URL = "/j_spring_security_check";
//	private static final String LOGOUT_URL = "/j_spring_security_logout";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		template = new RestTemplate();
		template.getMessageConverters().add(new FormHttpMessageConverter());
		template.getMessageConverters().add(new StringHttpMessageConverter());
		template.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		return super.onBind(intent);
	}
	
	/**
	 * 
	 * @param path
	 * @param content
	 * @return
	 */
	public String post(String path, Object... content) {
		List<Object> params = Arrays.asList(content);
		if (path.equals(LOGIN_URL)) {
			MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
			form.add("j_username", params.get(0).toString());
			form.add("j_password", params.get(1).toString());
			return get(template.postForLocation(baseUrl + path, form).toString().substring(baseUrl.length())).toString();
		} else {
			baseUrl += "/rest";
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<Object> request = null;
			if (params.get(0) instanceof Entity) {
				headers.setContentType(MediaType.APPLICATION_JSON);
				request = new HttpEntity<Object>(params.get(0), headers);
			} else if (params.get(0) instanceof String) {
				headers.set("Content-Type", "text/uri-list; charset=utf-8");
				request = new HttpEntity<Object>(baseUrl + params.get(0), headers);
			}
			return ((ResponseEntity<String>) template.postForEntity(baseUrl + path, request, String.class)).getBody();
		}
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	public Object get(String path) {
		ResponseEntity<String> response = template.getForEntity(baseUrl + path, String.class);
		return response.getBody();
	}

}
