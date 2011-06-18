/**
 * 
 */
package com.tenline.pinecone.platform.sdk;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.jar.JarFile;

import javax.ws.rs.core.MediaType;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.util.GenericType;

import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Variable;

/**
 * @author Bill
 *
 */
public class VariableAPI extends AbstractAPI {

	/**
	 * @param host
	 * @param port
	 * @param listener
	 */
	public VariableAPI(String host, String port,
			APIListener listener) {
		super(host, port, listener);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param variable
	 * @throws Exception
	 */
	public void create(Variable variable) throws Exception {
		request = new ClientRequest(url + "/api/variable/create");
		request.body(MediaType.APPLICATION_JSON, variable).accept(MediaType.APPLICATION_JSON);
		response = request.post();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(Variable.class));
		else listener.onError("Create Variable Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(String id) throws Exception {
		request = new ClientRequest(url + "/api/variable/delete/{id}");
		request.pathParameter("id", id);
		response = request.delete();
		if (response.getStatus() == 200) listener.onMessage("Variable Deleted!");
		else listener.onError("Delete Variable Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param variable
	 * @throws Exception
	 */
	public void update(Variable variable) throws Exception {
		request = new ClientRequest(url + "/api/variable/update");
		request.body(MediaType.APPLICATION_JSON, variable).accept(MediaType.APPLICATION_JSON);
		response = request.put();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(Variable.class));
		else listener.onError("Update Variable Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param filter
	 * @throws Exception
	 */
	public void show(String filter) throws Exception {
		request = new ClientRequest(url + "/api/variable/show/{filter}");
		request.pathParameter("filter", filter).accept(MediaType.APPLICATION_JSON);
		response = request.get();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(new GenericType<Collection<Variable>>(){}));
		else listener.onError("Show Variable Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param filter
	 * @throws Exception
	 */
	public void showByDevice(String filter) throws Exception {
		request = new ClientRequest(url + "/api/variable/show/{filter}/@Device");
		request.pathParameter("filter", filter).accept(MediaType.APPLICATION_JSON);
		response = request.get();
		if (response.getStatus() == 200) listener.onMessage(response.getEntity(new GenericType<Collection<Variable>>(){}));
		else listener.onError("Show Variable By Device Error Code: Http (" + response.getStatus() + ")");
		response.releaseConnection();
	}
	
	/**
	 * 
	 * @param symbolicName
	 * @param version
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void get(String symbolicName, String version) {
		String jarName = symbolicName.substring(symbolicName.lastIndexOf(".") + 1) + "-" + version + ".jar";
		url += "/svn/repository/" + symbolicName.replace(".", "/") + "/" + version + "/" + jarName;
		JarFile jar = null;
		try {
			jar = ((JarURLConnection) new URL("jar:" + url + "!/").openConnection()).getJarFile();
		    Document doc = new SAXReader().read(jar.getInputStream(jar.getEntry("META-INF/pinecone-metadata.xml")));
		    List<Node> variableNodes = doc.selectNodes("/metadata/variable");
		    Collection<Variable> variables = new ArrayList<Variable>();
		    for (int i=0; i<variableNodes.size(); i++) {
		    	Node variableNode = variableNodes.get(i);
		    	Variable variable = new Variable();
		    	variable.setName(variableNode.selectSingleNode("./name").getText());
		    	variable.setType(variableNode.selectSingleNode("./type").getText());
		    	List<Node> itemNodes = variableNode.selectNodes("./item");
		    	Collection<Item> items = new ArrayList<Item>();
		    	for (int j=0; j<itemNodes.size(); j++) {
		    		Node itemNode = itemNodes.get(j);
		    		Item item = new Item();
		    		item.setText(itemNode.selectSingleNode("./text").getText());
		    		item.setValue(itemNode.selectSingleNode("./value").getText());
		    		items.add(item);
		    	}
		    	variable.setItems(items);
		    	variables.add(variable);
		    }
		    listener.onMessage(variables);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			listener.onError("Get Variable Error: " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			listener.onError("Get Variable Error: " + e.getMessage());
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			listener.onError("Get Variable Error: " + e.getMessage());
		} finally {
			try {
				jar.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
