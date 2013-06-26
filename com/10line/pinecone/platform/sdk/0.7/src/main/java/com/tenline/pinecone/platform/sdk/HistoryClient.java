/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * @author Bill
 *
 */
public class HistoryClient {

	// Redis Client
	private Jedis client;
	private DateFormat format;
	
	/**
	 * 
	 * @param host
	 */
	public HistoryClient(String host) {
		// TODO Auto-generated constructor stub
		client = new Jedis(host); client.connect();
		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 
	 */
	public void disconnect() {
		client.disconnect();
	}
	
	/**
	 * 
	 * @param id
	 * @param date
	 * @param value
	 * @return
	 */
	public Long setValue(String id, Date date, String value) {
		return client.hset(id, format.format(date), value);
	}
	
	/**
	 * 
	 * @param id
	 * @param date
	 * @return
	 */
	public Long deleteValue(String id, Date date) {
		return client.hdel(id, format.format(date));
	}
	
	/**
	 * 
	 * @param id
	 * @param date
	 * @return
	 */
	public String getValue(String id, Date date) {
		return client.hget(id, format.format(date));
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<String> getValues(String id) {
		return client.hvals(id);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Set<String> getDates(String id) {
		return client.hkeys(id);
	}

}
