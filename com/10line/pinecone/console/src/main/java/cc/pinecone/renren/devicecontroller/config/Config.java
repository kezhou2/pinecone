package cc.pinecone.renren.devicecontroller.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Config {
	private JSONArray focusJson;
	private JSONArray alermJson;
	
	private Properties conf;
	
	private final String KEY_FOCUS = "pinecone.focus";
	private final String KEY_ALERM = "pinecone.alerm";
	
	private String CONFIG_FILE;
	private final String CACHE_FOLDER = "config-cache"; 
	
	public Config(String userid) throws FileNotFoundException, IOException, ParseException{
		
		String path = this.getClass().getClassLoader().getResource("/").getPath();
		File pathFile = new File(path);
		CONFIG_FILE = pathFile.getParentFile().getParentFile().getAbsolutePath()+File.separatorChar+CACHE_FOLDER+File.separatorChar+userid+".properties";
		
		conf = new Properties();
		
		File confFile = new File(CONFIG_FILE);
		if(!confFile.exists()){
			confFile.createNewFile();
		}else{
			conf.load(new FileInputStream(CONFIG_FILE));
			String focus = conf.getProperty("pinecone.focus");
			String alerm = conf.getProperty("pinecone.alerm");
			
			JSONParser parser = new JSONParser();
			if(focus != null){
				focusJson = (JSONArray)parser.parse(focus);	
			}
			
			if(alerm != null){
				alermJson = (JSONArray)parser.parse(alerm);
			}
		}
	}
	
	private void save() throws FileNotFoundException, IOException{
		conf.setProperty(KEY_FOCUS, focusJson.toJSONString());
		conf.setProperty(KEY_ALERM, alermJson.toJSONString());
		conf.store(new FileOutputStream(new File(CONFIG_FILE)), "User's device configuration");
	}
	
	@SuppressWarnings("unchecked")
	public void addFocusVariable(String deviceId, String variable){
		for(int i=0;i<focusJson.size();i++){
			JSONObject o = (JSONObject)focusJson.get(i);
			if(o.get(deviceId) == null){
				o.put(deviceId, variable);
			}else{
				String vStr = (String)o.get(deviceId);
				o.put(deviceId, vStr+"_"+variable);
			}
		}
	}
	
	public void deleteFocusVariable(String deviceId, String variable){
		for(int i=0;i<focusJson.size();i++){
			JSONObject o = (JSONObject)focusJson.get(i);
			if(o.get(deviceId) != null){
				String vStr = (String)o.get(deviceId);
				if(str)
				o.put(deviceId, vStr+"_"+variable);
			}
		}
	}
	
	public List<String> getFocusDeviceVariables(String deviceId){
		return null;
	}
	
	public void addAlermVariable(String deviceId, String variable, String alermString){
		
	}
	
	public void deleteAlermVariable(String deviceId, String variable){
		
	}
	
	public Map<String,String> getAlermVariableString(String deviceId){
		return null;
	}

}
