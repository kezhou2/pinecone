package cc.pinecone.renren.devicecontroller.servlet;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import cc.pinecone.renren.devicecontroller.controller.AppConfig;

import com.tenline.pinecone.platform.sdk.ChannelClient;

public class Connector implements MqttCallback{
	
	private Map<String,String> values;
	private ChannelClient client;
	
	public Connector(String deviceid) throws Exception{
		values = new LinkedHashMap<String,String>();
		
		this.client = new ChannelClient(AppConfig.CHANNEL_URL);
		//TODO
		this.client.listen(this, "pinecone@device."+deviceid);
	}

	@Override
	public void connectionLost(Throwable arg0) {
		System.out.println(arg0.getMessage());
	}

	@Override
	public void deliveryComplete(MqttDeliveryToken arg0) {
		System.out.println("deliveryComplete");
	}

	@Override
	public void messageArrived(MqttTopic arg0, MqttMessage arg1)
			throws Exception {
		System.out.println(new String(arg1.getPayload()));
		byte[] buf = arg1.getPayload();
		if(buf != null){
			JSONObject obj = (JSONObject)JSONValue.parse(new String(buf));
			values.put((String)obj.get("id"), (String)obj.get("value"));
		}
	}
	
	public String getValue(String id){
		if(values.containsKey(id)){
			return values.get(id);
		}else{
			return null;
		}
	}
	
	public void addVariable(String id){
		values.put(id, null);
	}
	
	public void addVariable(String[] ids){
		for(String id:ids){
			addVariable(id);
		}
	}
	
	public void destroy() throws Exception{
		client.disconnect();
		client = null;
		
		values.clear();
		values = null;
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray getJSONValues(){
		JSONArray array = new JSONArray();
		for(String key:values.keySet()){
			JSONObject obj = new JSONObject();
			System.out.println("key:"+key+"|value:"+values.get(key));
			if(values.get(key) != null){
				obj.put("id", key);
				obj.put("value", values.get(key));
			}
			array.add(obj);
		}
		System.out.println("getvalues:"+array.toJSONString());
		
		return array;
	}
	
	@SuppressWarnings("unchecked")
	public String getStringValues(){
		String value = "";
		for(String key:values.keySet()){
			if(values.get(key) != null){
				value=value+key+":"+values.get(key)+",";
			}
		}
		if(value.length() >= 2 && value.charAt(value.length()-1) == ','){
			value.substring(0,value.length()-2);
		}
		System.out.println("getvalues:"+value);
		
		return value;
	}
	
	@SuppressWarnings("unchecked")
	public void publish(String varid,String value) throws Exception{
		JSONObject obj = new JSONObject();
		obj.put("id", varid);
		obj.put("value", value);
		System.out.println("publish json string------------------------------:"+obj.toJSONString());
		client.publish(obj.toJSONString());
	}
}
