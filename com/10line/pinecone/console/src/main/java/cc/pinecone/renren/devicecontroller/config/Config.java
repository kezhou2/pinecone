package cc.pinecone.renren.devicecontroller.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	
	public Config(String userid) throws FileNotFoundException, IOException, ParseException{
		
		String path = this.getClass().getClassLoader().getResource("/").getPath();
		
		conf = new Properties();
		conf.load(new FileInputStream(path+File.separator+userid+".properties"));
		
		String focus = conf.getProperty("pinecone.focus");
		String alerm = conf.getProperty("pinecone.focus");
		
		if(focus != null){
			JSONParser parser = new JSONParser();
			JSONArray focusArray = (JSONArray)parser.parse(focus);			
		}
		
		if(alerm != null){
			JSONParser parser = new JSONParser();
			JSONArray alermJson = (JSONArray)parser.parse(alerm);
		}
	}

}
