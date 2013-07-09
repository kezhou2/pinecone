package cc.pinecone.renren.devicecontroller.servlet;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import cc.pinecone.renren.devicecontroller.config.Config;
import cc.pinecone.renren.devicecontroller.controller.AppConfig;
import cc.pinecone.renren.devicecontroller.model.FocusVariable;

import com.tenline.pinecone.platform.sdk.ChannelClient;

public class Connector implements MqttCallback{
	
	private Map<String,String> values;
	private ChannelClient client;
	private String deviceId;
	
	private Date lastReceived;
	
	private final String CONNECT = "connect";
	private final String DISCONNECT = "disconnect";
	private final String NUMERIC = "numeric";
	private final String STRING = "string";
	
	public Connector(String deviceid, String topic) throws Exception{
		System.out.println("####################initialize topic:"+topic);
		values = new LinkedHashMap<String,String>();
		this.deviceId = deviceid;
		
		this.client = new ChannelClient(AppConfig.CHANNEL_URL);
		this.client.listen(this, topic);
		this.lastReceived = new Date();
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
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&"+new String(arg1.getPayload()));
		byte[] buf = arg1.getPayload();
		if(buf != null){
			JSONObject obj = (JSONObject)JSONValue.parse(new String(buf));
			values.put((String)obj.get("id"), (String)obj.get("value"));
			this.lastReceived = new Date();
		}
	}
	
	public String getValue(String id){
		Date now = new Date();
		if((now.getTime() - this.lastReceived.getTime()) < 5*60*1000){		//long time no revceived data
			if(values.containsKey(id)){										//load data in cache
				return values.get(id);
			}
		}
		
		return null;
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
	public JSONArray getJSONValue(Config conf){
		JSONArray array = new JSONArray();
		for(String variableId:values.keySet()){
			JSONObject obj = new JSONObject();
			System.out.println("key:"+variableId+"|value:"+values.get(variableId));
			if(values.get(variableId) != null){
				obj.put("id", variableId);
				obj.put("value", values.get(variableId));
				FocusVariable fv = conf.getVariable(deviceId, variableId);
				boolean isAlerm = isAlerm(fv.getAlermString(), values.get(variableId));
				obj.put("isAlerm", isAlerm);
			}
			array.add(obj);
		}
		System.out.println("getvalues:"+array.toJSONString());
		
		return array;
	}
	
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
	public void publish(String topic, String varid,String value) throws Exception{
		JSONObject obj = new JSONObject();
		obj.put("id", varid);
		obj.put("value", value);
		System.out.println("publish json string------------------------------:"+obj.toJSONString());
		client.publish(topic,obj.toJSONString());
	}
	
	public String getDeviceStatus(){
		if(values.keySet().size() == 0){
			return DISCONNECT;
		}else{
			Date now = new Date();
			if(now.getTime() - this.lastReceived.getTime() > 5*60*1000){
				return "DISCONNECT";
			}
		}
		
		//TODO here we need to add alermer
		
		return CONNECT;
	}
	
	@SuppressWarnings("unused")
	private boolean isAlerm(String alermStr, String value){
		
		if(alermStr == null || "".equals(alermStr))
			return false;
		
		try{
			JSONParser parser = new JSONParser();
			JSONObject ob = (JSONObject)parser.parse(alermStr);
			String conditionType = (String)ob.get("conditionType");
			String condition = (String)ob.get("condition");
			String variablevalue = (String)ob.get("variablevalue");
			String clog = (String)ob.get("clog");
			String cpage = (String)ob.get("cpage");
			String csound = (String)ob.get("csound");
			String csms = (String)ob.get("csms");
			String cemail = (String)ob.get("cemail");
			
			if(conditionType.equals(NUMERIC)){
				float val = Float.parseFloat(variablevalue);
				float cur = Float.parseFloat(value);
				if(condition.equals(">")){
					if(cur > val)
						return true;
				}else if(condition.equals(">=")){
					if(cur >= val)
						return true;
				}else if(condition.equals("=")){
					if(cur == val)
						return true;
				}else if(condition.equals("<")){
					if(cur < val)
						return true;
				}else if(condition.equals("<=")){
					if(cur <= val)
						return true;
				}else if(condition.equals("!=")){
					if(cur != val)
						return true;
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return false;
	}
	
}
