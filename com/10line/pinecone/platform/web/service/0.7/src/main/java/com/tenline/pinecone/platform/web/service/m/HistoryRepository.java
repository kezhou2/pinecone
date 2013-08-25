/**
 * 
 */
package com.tenline.pinecone.platform.web.service.m;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Bill
 * 
 */
@Repository
@RequestMapping("/history")
public class HistoryRepository {

	@Autowired
	private RedisTemplate<String, String> template;

	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public void create(@PathVariable String id, @RequestBody String value) {
		template.opsForHash().put(id, format.format(new Date()), value);
	}
	
	@RequestMapping(value = "/{id}/{date}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String id, @PathVariable String date) {
		template.opsForHash().delete(id, date);
	}

	@RequestMapping(value = "/{id}/{date}", method = RequestMethod.GET)
	public @ResponseBody Object find(@PathVariable String id, @PathVariable String date) {
		return template.opsForHash().get(id, date);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<Object, Object> findAll(@PathVariable String id) {
		return template.opsForHash().entries(id);
	}

}
