/**
 * 
 */
package com.tenline.pinecone.service;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tenline.pinecone.model.Device;
import com.tenline.pinecone.model.Protocol;
import com.tenline.pinecone.service.restful.ProtocolRestfulService;

/**
 * @author Bill
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ProtocolServiceTest extends AbstractServiceTest {
	
	private Device device;

	private Protocol protocol;
	
	private List protocols;
	
	private ProtocolRestfulService protocolService;
	
	@Before
	public void testSetup() {
		protocolService = new ProtocolRestfulService(persistenceManagerFactory);
		protocolService.setJdoTemplate(jdoTemplate);
		protocol = new Protocol();
		protocol.setId("asa");
		protocol.setName("modbus");
		device = new Device();
		device.setId("asa");
		protocol.setDevice(device);
		protocols = new ArrayList();
		protocols.add(protocol);
	}
	
	@After
	public void testShutdown() {	
		protocolService = null;
		protocols.remove(protocol);
		device = null;
		protocol = null;
		protocols = null;
	}

	@Test
	public void testCreate() { 
		when(jdoTemplate.getObjectById(Device.class, device.getId())).thenReturn(device);
		when(jdoTemplate.makePersistent(protocol)).thenReturn(protocol);
		Protocol result = protocolService.create(protocol);
		verify(jdoTemplate).getObjectById(Device.class, device.getId());
		verify(jdoTemplate).makePersistent(protocol);
		assertEquals("modbus", result.getName());
	}
	
	@Test
	public void testDelete() {
		when(jdoTemplate.getObjectById(Protocol.class, protocol.getId())).thenReturn(protocol);
		Response result = protocolService.delete(protocol.getId());
		verify(jdoTemplate).deletePersistent(protocol);
		assertEquals(200, result.getStatus());
	}
	
	@Test
	public void testUpdate() {
		when(jdoTemplate.getObjectById(Protocol.class, protocol.getId())).thenReturn(protocol);
		when(jdoTemplate.makePersistent(protocol)).thenReturn(protocol);
		Protocol result = protocolService.update(protocol);
		verify(jdoTemplate).getObjectById(Protocol.class, protocol.getId());
		verify(jdoTemplate).makePersistent(protocol);
		assertEquals("modbus", result.getName());
	}
	
	@Test
	public void testShow() {
		String filter = "name=='modbus'";
		String queryString = "select from " + Protocol.class.getName() + " where " + filter;
		when(jdoTemplate.find(queryString)).thenReturn(protocols);
		Collection<Protocol> result = protocolService.show(filter);
		verify(jdoTemplate).find(queryString);
		assertEquals(1, result.size());
	}

}
