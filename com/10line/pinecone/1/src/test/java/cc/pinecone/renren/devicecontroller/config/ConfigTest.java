//package cc.pinecone.renren.devicecontroller.config;
//
//import static org.junit.Assert.*;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.List;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import cc.pinecone.renren.devicecontroller.model.FocusDevice;
//import cc.pinecone.renren.devicecontroller.model.FocusVariable;
//
//public class ConfigTest {
//
//	private Config conf;
//	@Before
//	public void setUp() throws Exception {
//		conf = new Config("21.xml");
//	}
//
//	@Test
//	public void testAddFocusVariable() {
//		try {
//			Assert.assertTrue(conf.addFocusVariable("1", "1"));
//			Assert.assertTrue(conf.addFocusVariable("1", "2"));
//			Assert.assertTrue(conf.addFocusVariable("1", "3"));
//			Assert.assertTrue(conf.addFocusVariable("2", "4"));
//			Assert.assertTrue(conf.addFocusVariable("2", "5"));
//			Assert.assertTrue(conf.addFocusVariable("3", "6"));
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testGetVariable(){
//		FocusVariable var = conf.getVariable("1", "1");
//		
//		Assert.assertNotNull(var);
//		Assert.assertEquals(var.getId(), "1");
//	}
//	
//	@Test
//	public void testGetDevice(){
//		FocusDevice dev = conf.getDevice("2");
//		
//		Assert.assertNotNull(dev);
//		Assert.assertEquals(dev.getId(), "2");
//		Assert.assertNotNull(dev.getVariable("4"));
//	}
//	
//	@Test
//	public void testGetFocusDeviceIds() {
//		List<String> result = conf.getFocusDeviceIds();
//		Assert.assertEquals(result.size(), 3);
//	}
//
//	@Test
//	public void testGetFocusDeviceVariableIds() {
//		List<String> result = conf.getFocusDeviceVariableIds("1");
//		Assert.assertEquals(result.size(), 3);
//		result = conf.getFocusDeviceVariableIds("2");
//		Assert.assertEquals(result.size(), 2);
//		result = conf.getFocusDeviceVariableIds("3");
//		Assert.assertEquals(result.size(), 1);
//	}
//
//	@Test
//	public void testDeleteFocusVariable() {
//		try {
//			Assert.assertTrue(conf.deleteFocusVariable("1", "1"));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		List<String> result = conf.getFocusDeviceVariableIds("1");
//		Assert.assertEquals(result.size(), 2);
//	}
//
//	@Test
//	public void testDeleteDevice() {
//		try {
//			Assert.assertTrue(conf.deleteDevice("3"));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		List<String> result = conf.getFocusDeviceIds();
//		Assert.assertEquals(result.size(), 2);
//	}
//
//
//
//}
