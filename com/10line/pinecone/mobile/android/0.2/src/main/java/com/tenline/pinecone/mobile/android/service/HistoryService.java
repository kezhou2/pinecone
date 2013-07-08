/**
 * 
 */
package com.tenline.pinecone.mobile.android.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import redis.clients.jedis.Jedis;

/**
 * @author Bill
 *
 */
public class HistoryService extends AbstractService {

	// Redis Client
	private Jedis client;
	private DateFormat format;
			
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i(getClass().getSimpleName(), "onBind");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				client = new Jedis(baseUrl); client.connect();
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
			
		}).start();
		return super.onBind(intent);
	}
			
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(getClass().getSimpleName(), "onDestroy");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				client.disconnect();
				format = null;
			}
			
		}).start();
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
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, String> getAll(String id) {
		return client.hgetAll(id);
	}

}
