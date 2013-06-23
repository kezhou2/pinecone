package cc.pinecone.renren.devicecontroller.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import cc.pinecone.renren.devicecontroller.model.FocusDevice;
import cc.pinecone.renren.devicecontroller.model.FocusVariable;

public class Config {
	
	private static Map<String, Config> map = new LinkedHashMap<String,Config>(); 
	
	private Document doc;
	
	private String CONFIG_FILE;
 	
	public static Config getInstance(String userid, String configPath){
		if(map.get(userid) == null){
			try {
				File file = new File(configPath);
				if(!file.exists())
					file.mkdir();
				Config conf = new Config(configPath+File.separatorChar+userid+".xml");
				map.put(userid, conf);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		
		return map.get(userid);
	}
	
	private Config(String filePath) throws DocumentException{
		
		CONFIG_FILE = filePath;
		
		File confFile = new File(CONFIG_FILE);
		if(!confFile.exists()){
			doc = DocumentHelper.createDocument();
			doc.addElement("configuration");
		}else{
			SAXReader saxReader = new SAXReader();
			doc = saxReader.read(CONFIG_FILE);
		}
	}
	
	private void save() throws FileNotFoundException, IOException{
		XMLWriter output = new XMLWriter(new FileWriter( new File(CONFIG_FILE) ));
	    output.write(doc);
	    output.close();
	}
	
	@SuppressWarnings("rawtypes")
	public boolean addFocusVariable(String deviceId, String variableId) throws FileNotFoundException, IOException{
		Element root = doc.getRootElement();
		Iterator it = root.elementIterator("Device");
		while(it.hasNext()){
			Element device = (Element)it.next();
			String id = device.attributeValue("id");
			if(id != null && id.equals(deviceId)){
				Iterator iit = device.elementIterator("Variable");
				while(iit.hasNext()){
					Element var = (Element)iit.next();
					String varid = var.attributeValue("id");
					if(varid != null && varid.equals(variableId)){
						save();
						return false;
					}
				}
				
				Element varEl = device.addElement("Variable");
				varEl.addAttribute("id", variableId);
				varEl.addElement("AlermStr");
				save();
				return true;
			}
		}
		
		Element devEl = root.addElement("Device");
		devEl.addAttribute("id", deviceId);
		Element varEl = devEl.addElement("Variable");
		varEl.addAttribute("id", variableId);
		varEl.addElement("AlermStr");
		save();
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean deleteFocusVariable(String deviceId, String variableId) throws FileNotFoundException, IOException{
		Element root = doc.getRootElement();
		Iterator it = root.elementIterator("Device");
		while(it.hasNext()){
			Element device = (Element)it.next();
			String id = device.attributeValue("id");
			if(id != null && id.equals(deviceId)){
				Iterator iit = device.elementIterator("Variable");
				while(iit.hasNext()){
					Element var = (Element)iit.next();
					String varid = var.attributeValue("id");
					if(varid != null && varid.equals(variableId)){
						device.remove(var);
						save();
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean deleteDevice(String deviceId) throws FileNotFoundException, IOException{
		Element root = doc.getRootElement();
		Iterator it = root.elementIterator("Device");
		while(it.hasNext()){
			Element device = (Element)it.next();
			String id = device.attributeValue("id");
			if(id != null && id.equals(deviceId)){
				root.remove(device);
				save();
				return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public List<String> getFocusDeviceIds(){
		List<String> result = new ArrayList<String>();
		
		Element root = doc.getRootElement();
		Iterator it = root.elementIterator("Device");
		while(it.hasNext()){
			Element device = (Element)it.next();
			String id = device.attributeValue("id");
			if(id != null){
				result.add(id);
			}
		}
		
		return result;
		
	}
	
	@SuppressWarnings("rawtypes")
	public List<String> getFocusDeviceVariableIds(String deviceId){
		List<String> result = new ArrayList<String>();
		
		Element root = doc.getRootElement();
		Iterator it = root.elementIterator("Device");
		while(it.hasNext()){
			Element device = (Element)it.next();
			String id = device.attributeValue("id");
			if(id != null && id.equals(deviceId)){
				Iterator iit = device.elementIterator("Variable");
				while(iit.hasNext()){
					Element variable = (Element)iit.next();
					String varid = variable.attributeValue("id");
					if(varid != null){
						result.add(varid);
					}
				}
				break;
			}
		}
		
		return result;
	}
	
	
	
	@SuppressWarnings("rawtypes")
	public FocusVariable getVariable(String deviceId, String variableId){
		Element root = doc.getRootElement();
		Iterator it = root.elementIterator("Device");
		while(it.hasNext()){
			Element device = (Element)it.next();
			String id = device.attributeValue("id");
			if(id != null && id.equals(deviceId)){
				Iterator iit = device.elementIterator("Variable");
				while(iit.hasNext()){
					Element variable = (Element)iit.next();
					String varid = variable.attributeValue("id");
					if(varid != null && varid.equals(variableId)){
						FocusVariable var = new FocusVariable();
						var.setId(varid);
						var.setAlermString(variable.elementText("AlermStr"));
						return var;
					}
				}
				break;
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public FocusDevice getDevice(String deviceId){
		Element root = doc.getRootElement();
		Iterator it = root.elementIterator("Device");
		while(it.hasNext()){
			Element device = (Element)it.next();
			String id = device.attributeValue("id");
			if(id != null && id.equals(deviceId)){
				FocusDevice dev = new FocusDevice();
				dev.setId(deviceId);
						
				Iterator iit = device.elementIterator("Variable");
				while(iit.hasNext()){
					Element variable = (Element)iit.next();
					String varid = variable.attributeValue("id");
					if(varid != null){
						FocusVariable var = new FocusVariable();
						var.setId(varid);
						var.setAlermString(variable.elementText("AlermStr"));
						dev.addVariable(var);
					}
				}
				return dev;
			}
		}
		
		return null;
	}
}
