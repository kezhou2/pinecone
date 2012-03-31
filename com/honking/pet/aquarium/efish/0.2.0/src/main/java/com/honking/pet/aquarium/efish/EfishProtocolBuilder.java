/**
 * 
 */
package com.honking.pet.aquarium.efish;

import java.util.ArrayList;
import java.util.LinkedList;

import org.osgi.framework.Bundle;

import com.tenline.pinecone.platform.model.Category;
import com.tenline.pinecone.platform.model.Device;
import com.tenline.pinecone.platform.model.Driver;
import com.tenline.pinecone.platform.model.Item;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.monitor.AbstractProtocolBuilder;

/**
 * @author Bill
 *
 */
public class EfishProtocolBuilder extends AbstractProtocolBuilder {
	
	/**
	 * 
	 * @param bundle
	 */
	public EfishProtocolBuilder(Bundle bundle) {
		super(bundle);
		// TODO Auto-generated constructor stub
		if (metaData == null) {
			metaData = new Device();
			
			Category category = new Category();
			category.setType(Category.COM);
			category.setName("honking");
			category.setDomain(Category.DOMAIN_PET);
			category.setSubdomain(Category.SUB_DOMAIN_AQUARIUM);
			
			Driver driver = new Driver();
			driver.setAlias("efish");
			driver.setName(bundle.getHeaders().get("Bundle-Name").toString());
			driver.setVersion(bundle.getVersion().toString());
			driver.setCategory(category);
			
			metaData.setDriver(driver);
			metaData.setVariables(new ArrayList<Variable>());
			
			Variable variable = new Variable();
			variable.setName(bundle.getHeaders().get("Water-Temperature").toString()); // Localization
			variable.setType("read_write_discrete");
			variable.setItems(new ArrayList<Item>());
			
			for (int i=20; i<=30; i++) {
				Item item = new Item();
				item.setText(String.valueOf(i) + "°C");
				item.setValue(String.valueOf(i).getBytes());
				variable.getItems().add(item);
			}
			
			metaData.getVariables().add(variable);	
			
			variable = new Variable();
			variable.setName(bundle.getHeaders().get("Oxygen-Generation").toString());
			variable.setType("write_discrete");
			variable.setItems(new ArrayList<Item>());
			
			for (int i=1; i<=3; i++) {
				for (int j=5; j<=10; j++) {
					Item item = new Item();
					item.setText(bundle.getHeaders().get("Work").toString() + i + bundle.getHeaders().get("Minute").toString() + 
							", " + bundle.getHeaders().get("Stop").toString() + j + bundle.getHeaders().get("Minute").toString());
					item.setValue((i + "-" + j).getBytes());
					variable.getItems().add(item);
				}
			}
			
			metaData.getVariables().add(variable);		
		}
	}
	
	@Override
	public void initializeReadQueue(LinkedList<Device> queue) {
		// TODO Auto-generated method stub
		Device device = new Device();
		device.setVariables(new ArrayList<Variable>());
		Variable variable = new Variable();
		variable.setName(bundle.getHeaders().get("Water-Temperature").toString());
		device.getVariables().add(variable);
		queue.addLast(device);
	}

}
