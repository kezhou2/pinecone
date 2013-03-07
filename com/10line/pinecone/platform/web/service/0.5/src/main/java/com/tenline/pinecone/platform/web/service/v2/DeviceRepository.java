/**
 * 
 */
package com.tenline.pinecone.platform.web.service.v2;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Repository;

import com.tenline.pinecone.platform.model.Device;

/**
 * @author Bill
 *
 */
@Repository
@Path("/rest/v2/device")
public class DeviceRepository {

	@PersistenceContext  
	private EntityManager entityManager;  
	
	@GET
	@Path("/search/codes")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Device> getDeviceByCode(@QueryParam(value = "code") String code) {
		return entityManager.createQuery("from Device where code='" + code + "'", Device.class).getResultList();
	}

}
