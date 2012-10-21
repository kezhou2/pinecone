/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tenline.pinecone.platform.model.Device;

/**
 * @author Bill
 *
 */
public interface DeviceRepository extends JpaRepository<Device, Long> {}
