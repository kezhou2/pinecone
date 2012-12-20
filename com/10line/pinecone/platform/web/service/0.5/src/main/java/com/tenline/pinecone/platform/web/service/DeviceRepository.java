/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

import com.tenline.pinecone.platform.model.Device;

/**
 * @author Bill
 *
 */
public interface DeviceRepository extends JpaRepository<Device, Long> {
	
	@RestResource(path = "codes", rel = "codes")
	public List<Device> findByCode(@Param("code") String code);
	
	@RestResource(path = "names", rel = "names")
	public List<Device> findByName(@Param("name") String name);
	
}
